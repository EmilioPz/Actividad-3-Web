import { useEffect, useRef, useState } from "react";

export function useFetch(url, opts) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const [errorText, setErrorText] = useState(null);
  const abortRef = useRef(null);

  const fetchData = async () => {
    if (abortRef.current) abortRef.current.abort();
    const ctrl = new AbortController();
    abortRef.current = ctrl;

    setLoading(true);
    setError(false);
    setErrorText(null);

    try {
      const res = await fetch(url, {
        ...opts,
        headers: { Accept: "application/json", ...(opts?.headers || {}) },
        signal: ctrl.signal,
      });

      const rawText = await res.text();

      if (!res.ok) {
        setError(true);
        setErrorText(`HTTP ${res.status}`);
        setData(null);
        setLoading(false);
        return;
      }

      let json = null;
      try {
        json = rawText ? JSON.parse(rawText) : [];
      } catch (e) {
        console.warn("[useFetch] Error al parsear JSON, usando []");
        json = [];
      }

      setData(json);
      setError(false);
      setErrorText(null);
    } catch (e) {
      if (e.name === "AbortError") return;
      console.error("[useFetch] Error:", e);
      setError(true);
      setErrorText(e.message);
      setData(null);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    return () => abortRef.current?.abort();
      // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [url]);

  return { data, loading, error, errorText, refetch: fetchData };
}