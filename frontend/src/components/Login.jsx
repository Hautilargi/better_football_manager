import { useState } from "react";
import { api } from "../api/axios";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [regEmail, setRegEmail] = useState("");
  const [regTeam, setRegTeam] = useState("");

  const login = async () => {
    try {
      const res = await api.post("/auth/login", { username, password });
      if (res.status >= 200 && res.status < 300) {
        // session cookie is set by server (withCredentials:true)
        alert("Login erfolgreich");
      } else {
        alert("Login fehlgeschlagen");
      }
    } catch (err) {
      console.error(err);
      alert("Login fehlgeschlagen: " + (err.response?.status || err.message));
    }
  };

  const logout = async () => {
    try {
      await api.post("/auth/logout");
      alert("Abgemeldet");
    } catch (err) {
      console.error(err);
      alert("Logout fehlgeschlagen");
    }
  };

  const register = async () => {
    try {
      const body = { username, password, email: regEmail, teamname: regTeam };
      const res = await api.post("/auth/register", body);
      if (res.status >= 200 && res.status < 300) {
        alert("Registrierung durchgeführt (oder Endpoint erfolgreich aufgerufen)");
      } else {
        alert("Registrierung fehlgeschlagen");
      }
    } catch (err) {
      console.error(err);
      alert("Registrierung fehlgeschlagen: " + (err.response?.status || err.message));
    }
  };

  return (
    <div style={{ maxWidth: 360 }}>
      <div>
        <label>Benutzer:</label>
        <input value={username} onChange={e => setUsername(e.target.value)} />
      </div>
      <div>
        <label>Passwort:</label>
        <input type="password" value={password} onChange={e => setPassword(e.target.value)} />
      </div>
      <div style={{ marginTop: 8 }}>
        <button onClick={login}>Login</button>
        <button onClick={logout} style={{ marginLeft: 8 }}>Logout</button>
      </div>

      <hr />
      <h4>Registrieren</h4>
      <div>
        <label>E-Mail (optional):</label>
        <input value={regEmail} onChange={e => setRegEmail(e.target.value)} />
      </div>
       <div>
       <label>Team Name (optional):</label>
        <input value={regTeam} onChange={e => setRegTeam(e.target.value)} />
      </div>
      <div style={{ marginTop: 8 }}>
        <button onClick={register}>Registrieren</button>
      </div>
    </div>
  );
}

export default Login;