package com.hautilargi.footman;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootTest
@ActiveProfiles("local")
class EndpointSmokeTest {

    @Autowired
    RequestMappingHandlerMapping handlerMapping;

    @Test
    void exposesExpectedEndpoints() {
        Set<String> actual = new TreeSet<>();
        for (RequestMappingInfo info : handlerMapping.getHandlerMethods().keySet()) {
            if (info.getPathPatternsCondition() == null) {
                continue;
            }
            String paths = String.join(",", info.getPathPatternsCondition().getPatternValues());
            if (paths.startsWith("/api") || paths.startsWith("/auth") || paths.startsWith("api")) {
                actual.add(info.getMethodsCondition().getMethods() + " " + paths);
            }
        }
        Set<String> expected = new TreeSet<>(Set.of(
                "[GET] /api/config/sleep", "[GET] /api/config/status", "[GET] /api/config/wake",
                "[GET] /api/debug/squads", "[GET] /api/debug/testmatch",
                "[GET] /api/leagues", "[GET] /api/leagues/table", "[GET] /api/leagues/{id}",
                "[GET] /api/matches", "[GET] /api/matches/{id}",
                "[GET] /api/me/league", "[GET] /api/me/roster", "[GET] /api/me/team",
                "[GET] /api/players", "[GET] /api/players/{id}",
                "[GET] /api/teams", "[GET] /api/teams/{id}", "[GET] /api/teams/{id}/roster",
                "[GET] /auth/me", "[POST] /auth/login", "[POST] /auth/logout", "[POST] /auth/register"));
        assertEquals(expected, actual);
    }
}
