import sqlite3 from "sqlite3";
import { open } from "sqlite";

export async function initDB() {
  const db = await open({
    filename: "./database.db",
    driver: sqlite3.Database
  });

  await db.exec(`
    CREATE TABLE IF NOT EXISTS users (
      id INTEGER PRIMARY KEY,
      username TEXT UNIQUE,
      password TEXT,
      reset_token TEXT,
      reset_expire INTEGER
    )
  `);

  await db.exec(`
    CREATE TABLE IF NOT EXISTS messages (
      id INTEGER PRIMARY KEY,
      text TEXT
    )
  `);

  // Beispielnachricht
  const row = await db.get("SELECT * FROM messages WHERE id = 1");
  if (!row) {
    await db.run("INSERT INTO messages(id, text) VALUES (1, 'Hallo Welt! Geschützt und sicher!')");
  }

  return db;
}
