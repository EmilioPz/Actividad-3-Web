import React, { useState, useEffect } from 'react';
import { Table, Form, Pagination, Spinner } from 'react-bootstrap';
import styles from './styles/TablaRecetas.module.css';
import BotoneraReceta from './BotoneraReceta';
import { toast } from 'react-toastify';
import { apiGetArray } from '../../api';

const API_BASE = '/api';

export default function TablaRecetas({ onRefresh }) {
  const [recetas, setRecetas] = useState([]);
  const [editId, setEditId] = useState(null);
  const [busqueda, setBusqueda] = useState('');
  const [pagina, setPagina] = useState(1);
  const [loading, setLoading] = useState(true);

  const recetasPorPagina = 8;

  useEffect(() => {
    cargarRecetas();
  }, []);

  const cargarRecetas = async () => {
    try {
      setLoading(true);
      const data = await apiGetArray(`${API_BASE}/recetas`);
      setRecetas(Array.isArray(data) ? data : []);
    } catch (e) {
      toast.error('❌ Error al cargar recetas');
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  const recetasFiltradas = (recetas || []).filter((r) =>
    (r.nombre || '').toLowerCase().includes(busqueda.toLowerCase())
  );

  const totalPaginas = Math.ceil(recetasFiltradas.length / recetasPorPagina);
  const recetasPagina = recetasFiltradas.slice(
    (pagina - 1) * recetasPorPagina,
    pagina * recetasPorPagina
  );

  return (
    <div>
      {loading ? (
        <div className="text-center my-4">
          <Spinner animation="border" /> Cargando recetas...
        </div>
      ) : (
        <>
          <Form.Control
            type="text"
            placeholder="🔎 Buscar receta..."
            value={busqueda}
            onChange={(e) => {
              setBusqueda(e.target.value);
              setPagina(1);
            }}
            className="mb-3"
          />

          <Table striped bordered hover responsive className={styles.table}>
            <thead>
              <tr>
                <th>Imagen</th>
                <th>Nombre</th>
                <th>Tiempo</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {recetasPagina.map((r) => (
                <tr key={r.id}>
                <td>
                    {(r?.imagen && r.imagen.trim()) ? (
                      <img
                        src={r.imagen}
                        alt={r.nombre || 'Receta'}
                        className={styles.image}
                        onError={(e) => { e.currentTarget.style.display = 'none'; }}
                      />
                    ) : (
                      <div className={styles.placeholder}>Sin imagen</div>
                    )}
                  </td>
                  <td>{r.nombre}</td>
                  <td>{r.tiempo}</td>
                  <td>
                    <BotoneraReceta
                      isEditing={editId === r.id}
                      recetaId={r.id}
                      receta={r}
                      onCancel={() => setEditId(null)}
                    />
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>

          {totalPaginas > 1 && (
            <Pagination className="justify-content-center">
              {[...Array(totalPaginas).keys()].map((num) => (
                <Pagination.Item
                  key={num + 1}
                  active={num + 1 === pagina}
                  onClick={() => setPagina(num + 1)}
                >
                  {num + 1}
                </Pagination.Item>
              ))}
            </Pagination>
          )}
        </>
      )}
    </div>
  );
}