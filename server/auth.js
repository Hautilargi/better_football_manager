import jwt from "jsonwebtoken";
import bcrypt from "bcryptjs";

const ACCESS_SECRET = "ACCESS_SECRET_KEY";
const REFRESH_SECRET = "REFRESH_SECRET_KEY";

export function generateTokens(user) {
  const access = jwt.sign({ id: user.id, username: user.username }, ACCESS_SECRET, { expiresIn: "10m" });
  const refresh = jwt.sign({ id: user.id }, REFRESH_SECRET, { expiresIn: "7d" });

  return { access, refresh };
}

export function verifyAccessToken(token) {
  return jwt.verify(token, ACCESS_SECRET);
}

export function verifyRefreshToken(token) {
  return jwt.verify(token, REFRESH_SECRET);
}

export async function hashPassword(pw) {
  return await bcrypt.hash(pw, 10);
}

export async function checkPassword(pw, hash) {
  return await bcrypt.compare(pw, hash);
}
