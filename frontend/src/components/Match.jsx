import { useState, useEffect } from 'react'
import axios from 'axios';
import '../App.css'

function Match() {
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    let mounted = true;
    (async () => {
      try {
        const res = await axios.get(
          '/api/debug/testmatch?homeId=1&awayId=2',
          { responseType: 'arraybuffer' }
        );
        // ArrayBuffer -> Blob -> Text (UTF-8). Bei Binärdaten ggf. anders verarbeiten.
        const blob = new Blob([res.data]);
        const text = await blob.text();
        if (mounted) setData(text);
      } catch (e) {
        if (mounted) setError(e.message || String(e));
      }
    })();
    return () => { mounted = false; };
  }, []);

  if (error) return <div>Fehler: {error}</div>;
  if (data === null) return <div>Loading...</div>;

  return (
    <div>
      {data}
    </div>
  );
}

export default Match;