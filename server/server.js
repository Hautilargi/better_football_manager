import express from "express";
import { initDB } from "./db.js";
import { 
  generateTokens, verifyAccessToken, verifyRefreshToken,
  hashPassword, checkPassword
} from "./auth.js";
import { randomToken } from "./utils.js";

const app = express();
app.use(express.json());

let db;
initDB().then(x => db = x);

// Auth-Middleware
function auth(req, res, next) {
  const header = req.headers.authorization;
  if (!header) return res.status(401).json({ error: "Kein Token" });

  try {
    const token = header.split(" ")[1];
    req.user = verifyAccessToken(token);
    next();
  } catch {
    res.status(401).json({ error: "Ungültiger Token" });
  }
}

/* ===========================
      Registrierung
=========================== */
app.post("/api/register", async (req, res) => {
  const { username, password } = req.body;

  const exists = await db.get("SELECT id FROM users WHERE username = ?", username);
  if (exists) return res.status(400).json({ error: "Name existiert" });

  const pwHash = await hashPassword(password);

  const result = await db.run(
    "INSERT INTO users(username, password) VALUES (?, ?)",
    username, pwHash
  );

  res.json({ success: true });
});

/* ===========================
      Login
=========================== */
app.post("/api/login", async (req, res) => {
  const { username, password } = req.body;

  const user = await db.get("SELECT * FROM users WHERE username = ?", username);
  if (!user) return res.status(400).json({ error: "Benutzer existiert nicht" });

  const ok = await checkPassword(password, user.password);
  if (!ok) return res.status(400).json({ error: "Passwort falsch" });

  const tokens = generateTokens(user);
  res.json(tokens);
});

/* ===========================
      Refresh Token
=========================== */
app.post("/api/refresh", async (req, res) => {
  const { refresh } = req.body;

  try {
    const data = verifyRefreshToken(refresh);
    const user = await db.get("SELECT * FROM users WHERE id = ?", data.id);
    const tokens = generateTokens(user);
    res.json(tokens);
  } catch {
    res.status(401).json({ error: "Ungültiger Refresh Token" });
  }
});

/* ===========================
      Passwort Reset Start
=========================== */
app.post("/api/reset/request", async (req, res) => {
  const { username } = req.body;

  const user = await db.get("SELECT * FROM users WHERE username = ?", username);
  if (!user) return res.json({ success: true }); // Kein Hinweis für Angreifer

  const token = randomToken();
  const expire = Date.now() + 1000 * 60 * 15; // 15 min gültig

  await db.run("UPDATE users SET reset_token=?, reset_expire=? WHERE id=?",
    token, expire, user.id);

  res.json({
    reset_link: `http://localhost:3000/reset.html#token=${token}`  
  });
});

/* ===========================
      Passwort setzen
=========================== */
app.post("/api/reset/confirm", async (req, res) => {
  const { token, password } = req.body;

  const user = await db.get("SELECT * FROM users WHERE reset_token = ?", token);
  if (!user) return res.status(400).json({ error: "Token ungültig" });

  if (Date.now() > user.reset_expire) {
    return res.status(400).json({ error: "Token abgelaufen" });
  }

  const pw = await hashPassword(password);

  await db.run("UPDATE users SET password=?, reset_token=NULL, reset_expire=NULL WHERE id=?",
    pw, user.id);

  res.json({ success: true });
});

/* ===========================
      Geschützte API
=========================== */
app.get("/api/message", auth, async (req, res) => {
  const row = await db.get("SELECT text FROM messages WHERE id = 1");
  res.json({ message: row.text, user: req.user.username });
});

/* ===========================
      Start
=========================== */
app.listen(3000, () => console.log("Server läuft auf http://localhost:3000"));
