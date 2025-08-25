// utils/api.js
const BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080';

async function toJson(res) {
  const txt = await res.text();
  try { return JSON.parse(txt); } catch { return txt; }
}

export async function apiGetArray(path) {
  const res = await fetch(`${BASE}${path}`);
  if (!res.ok) throw new Error(await res.text());
  const data = await toJson(res);
  if (Array.isArray(data)) return data;
  if (data && Array.isArray(data.content)) return data.content;
  return [];
}

export async function apiJson(path, method, body) {
  const res = await fetch(`${BASE}${path}`, {
    method,
    headers: { 'Content-Type': 'application/json' },
    body: body ? JSON.stringify(body) : undefined,
  });
  if (!res.ok) throw new Error(await res.text());
  return toJson(res);
}
