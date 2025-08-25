import React, { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
import ModalReceta from '../components/ModalReceta';
import styles from './styles/BuscarRecetas.module.css';
import IngredienteInput from '../components/IngredienteInput';
import RecetaCard from '../components/RecetaCard';

const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080';

export default function BuscarRecetas() {
  const [ingredienteInput, setIngredienteInput] = useState('');
  const [ingredientesUser, setIngredientesUser] = useState([]);
  const [recetas, setRecetas] = useState([]);
  const [resultados, setResultados] = useState([]);
  const [recetaSeleccionada, setRecetaSeleccionada] = useState(null);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState('');

  const normalizeList = (data) => {
    if (Array.isArray(data)) return data;
    if (data && Array.isArray(data.content)) return data.content;
    return [];
  };

  useEffect(() => {
    const cargarRecetas = async () => {
      try {
        setCargando(true);
        setError('');
        const res = await fetch(`${API_BASE}/api/recetas`);
        const txt = await res.text();
        const data = txt ? JSON.parse(txt) : [];
        setRecetas(normalizeList(data));
      } catch (e) {
        console.error('Error al cargar recetas:', e);
        setError('No se pudieron cargar las recetas.');
      } finally {
        setCargando(false);
      }
    };
    cargarRecetas();
  }, []);

  const agregarIngrediente = () => {
    const ing = ingredienteInput.trim().toLowerCase();
    if (ing && !ingredientesUser.includes(ing)) {
      setIngredientesUser((prev) => [...prev, ing]);
      setIngredienteInput('');
    }
  };

  const quitarIngrediente = (ing) => {
    setIngredientesUser((prev) => prev.filter((i) => i !== ing));
  };

  const colorPorcentaje = (p) => {
    if (p > 75) return '#4caf50';
    if (p >= 40) return '#ff9800';
    return '#f44336';
  };

  const getIngredientesDeReceta = (receta) => {
    const raw = receta?.ingredientes;

    if (Array.isArray(raw)) {
      return raw
        .map((i) =>
          typeof i === 'string'
            ? i
            :
              (i?.nombre ?? '')
        )
        .map((s) => s.trim().toLowerCase())
        .filter(Boolean);
    }

    if (typeof raw === 'string') {
      return raw
        .split(',')
        .map((s) => s.trim().toLowerCase())
        .filter(Boolean);
    }

    if (receta?.ingredientesNombres && Array.isArray(receta.ingredientesNombres)) {
      return receta.ingredientesNombres.map((s) => String(s).trim().toLowerCase()).filter(Boolean);
    }

    return [];
  };

  useEffect(() => {
    if (!ingredientesUser.length) {
      setResultados([]);
      return;
    }

    const res = recetas
      .map((receta) => {
        const ingReceta = Array.from(new Set(getIngredientesDeReceta(receta))); // sin duplicados
        if (!ingReceta.length) return null;

        const encontrados = ingredientesUser.filter((i) => ingReceta.includes(i));
        const faltantes = ingReceta.filter((i) => !ingredientesUser.includes(i));
        const porcentaje = (encontrados.length / ingReceta.length) * 100;

        return {
          ...receta,
          porcentaje: Math.round(porcentaje),
          encontrados,
          faltantes,
        };
      })
      .filter((r) => r && r.porcentaje > 0)
      .sort((a, b) => b.porcentaje - a.porcentaje);

    setResultados(res);
  }, [ingredientesUser, recetas]);

  return (
    <div className={styles.container}>
      <Sidebar />
      <main className={styles.main}>
        <h2 style={{ marginBottom: 20 }}>Buscar recetas por ingredientes</h2>

        {!!ingredientesUser.length && (
          <div className={styles.chips}>
            {ingredientesUser.map((ing) => (
              <button
                key={ing}
                className={styles.chip}
                title="Quitar"
                onClick={() => quitarIngrediente(ing)}
              >
                {ing} ✕
              </button>
            ))}
          </div>
        )}

        <div className={styles.inputGroup}>
          <IngredienteInput
            value={ingredienteInput}
            onChange={setIngredienteInput}
            onAdd={agregarIngrediente}
          />
        </div>

        {cargando && <p>Cargando recetas…</p>}
        {error && <p className={styles.error}>{error}</p>}

        <div style={{ marginTop: 30 }}>
          {!cargando && !error && resultados.length === 0 && (
            <p>No hay recetas que coincidan.</p>
          )}

          {resultados.map((receta) => (
            <RecetaCard
              key={receta.id}
              receta={receta}
              onView={(r) => setRecetaSeleccionada(r)}
              colorPorcentaje={colorPorcentaje}
            />
          ))}
        </div>
      </main>

      <ModalReceta receta={recetaSeleccionada} onClose={() => setRecetaSeleccionada(null)} />
    </div>
  );
}