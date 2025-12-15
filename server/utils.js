export function randomToken() {
  return [...Array(40)]
    .map(() => Math.floor(Math.random() * 16).toString(16))
    .join("");
}
