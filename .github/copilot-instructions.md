# Copilot instructions

Football-manager browser game: Spring Boot 4 backend (Java 21, Maven, JPA + H2) in `backend/`, React 19 + Vite + MUI SPA in `frontend/`. Java package root: `com.hautilargi.footman`.

## Build, run, lint

Backend (from `backend/`):
- Run locally: `mvn spring-boot:run -Plocal` (in-memory H2, port 8080). `-Pdevelopment` uses `application-dev.properties` (H2 file DB `./footman.db`). No profile uses `application.properties`.
- Package: `mvn -B -DskipTests package`
- Test: `mvn test` (from `backend/`). `EndpointSmokeTest` loads the context with the `local` profile and asserts the full set of REST endpoints, so update it when adding or removing an endpoint. Run one test with `mvn test -Dtest=ClassName#method`. Use `mvn clean test` if you see odd "Unresolved compilation problems" from stale classes.

Frontend (from `frontend/`):
- `npm install && npm run dev` (Vite dev server), `npm run build`, `npm run lint` (ESLint). No test runner is configured.
- Dev API target is set by `VITE_API_BASE_URL` in `frontend/.env.development` (`http://localhost:8080`). `src/api/axios.jsx` is the shared axios instance and uses `withCredentials: true`.

Docker: the root `Dockerfile` builds both parts into one image. Vite `dist` is copied to `/app/frontend/build` and served by nginx on port 80, which proxies `/api/` and `/auth/` to Spring Boot on 8080 (`nginx/default.conf`, `start.sh`). CI (`.github/workflows`) builds with Node 24 and Java 21.

## Architecture

- **Auth**: session-cookie based, with no JWT. `AuthController` (`/auth/...`) puts `USER_ID` in the HTTP session. `AuthInterceptor`, registered in `WebConfig` for `/api/**` only, returns 401 when it is missing. New authenticated endpoints must live under `/api/`. CORS is set in `CorsConfig`. The session cookie settings are in the `application*.properties` files.
- **Packages are feature-sliced**: `clubs`, `leagues`, `matches`, `players`, `users`, `config`. Each has `controller/` (REST, thin), `service/` (business logic), `repository/`, `model/` (JPA entities), `dto/`, and `mapper/` (MapStruct) as needed. Layering rule: controllers call only services (never repositories) and use constructor injection, as do services. Team logic lives in `clubs/service/TeamService`.
- **History vs. live entities**: `AbstractPlayer`/`Player`/`HistoryPlayer` and `AbstractSquad`/`Squad`/`HistorySquad` mirror each other. History variants are snapshots for finished matches.
- **Game clock**: `core/Scheduler.dayChangeProcessor` runs on a cron and drives the whole simulation. `GlobalConfiguration` (via `ConfigurationService`) holds the current day, season and server status.
  - Server status `OK` means the clock runs. `SLEEP` pauses it.
  - Days 1–34 play a matchday (`LeagueService.playMatchDay`) and update suspensions.
  - Days 35–40 are the summer break.
  - After day 40: relegation, season increment, new league schedules per tier (18 teams per league), stats reset, and the day resets to 0.
  - It currently auto-pauses after season 3, day 35.
  - The cron is currently every 5 seconds (an hourly cron is commented out), so be careful when changing it.
- **Match simulation**: `core/processing` (`MatchProcessor` interface, `SimpleMatchProcessor`, `ComplexMatchProcessor`, `MatchState`, `MatchStepEvent`, `MatchResults`). `core/util` has `WeightedRandom`, `NameGenerator`, and enums for `Formations`, `Positions`, `PlayerStatus`.
- **DTOs and JSON**: entities are exposed through DTOs and mappers (`ClubMapper`, `RosterMapper`). `core/JsonViews` is used for view-scoped serialization.
- **Debug**: `debug/Debug` and `DebugHelperService` back the frontend `components/debug/*` pages (test match, all players and teams).
- **Frontend**: a router-based SPA (`react-router` 7). `App.jsx` holds the routes, and components are grouped by domain in `src/components/{clubs,league,matches,players,debug}`. Styling is MUI plus per-component CSS files.

## Conventions

- The Maven profile ids (`local`, `development`, `run`) map to Spring profiles `local`, `dev`, and none. Keep the profile and `application-*.properties` names in sync.
- Use the H2 `ddl-auto=update` schema. There are no migration scripts, so entity changes apply automatically.
- Code comments, log messages, and some Dockerfile comments mix German and English. Logging mostly uses `System.out.println`.
