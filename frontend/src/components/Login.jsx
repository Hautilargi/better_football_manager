import React from "react";

const styles = {
  container: {
    minHeight: "100vh",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#f2f4f7",
  },
  card: {
    width: "320px",
    padding: "24px",
    borderRadius: "8px",
    backgroundColor: "#fff",
    boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
  },
  title: {
    marginBottom: "16px",
    textAlign: "center",
  },
  field: {
    marginBottom: "12px",
  },
  label: {
    display: "block",
    marginBottom: "4px",
    fontSize: "14px",
  },
  input: {
    width: "100%",
    padding: "8px",
    boxSizing: "border-box",
  },
  button: {
    width: "100%",
    padding: "10px",
    marginTop: "12px",
    cursor: "not-allowed",
  },
  hint: {
    marginTop: "12px",
    fontSize: "12px",
    textAlign: "center",
    color: "#777",
  },
};

export default function Login() {
  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <h2 style={styles.title}>Login</h2>

        <div style={styles.field}>
          <label style={styles.label}>E-Mail</label>
          <input
            type="email"
            placeholder="name@example.com"
            style={styles.input}
            disabled
          />
        </div>

        <div style={styles.field}>
          <label style={styles.label}>Passwort</label>
          <input
            type="password"
            placeholder="••••••••"
            style={styles.input}
            disabled
          />
        </div>

        <button style={styles.button} disabled>
          Anmelden
        </button>

        <p style={styles.hint}>
          * Platzhalter – keine Login-Funktion implementiert
        </p>
      </div>
    </div>
  );
}
