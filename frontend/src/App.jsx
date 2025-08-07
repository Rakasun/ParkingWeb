import { useState } from 'react';
import './App.css';

function App() {
  const [view, setView] = useState('login');
  const [form, setForm] = useState({ username: '', password: '', nombre: '' });
  const [error, setError] = useState(null);
  const [usuario, setUsuario] = useState(null);
  const [vehiculos, setVehiculos] = useState([]);
  const [nuevoVehiculo, setNuevoVehiculo] = useState({ matricula: '' });
  const [vehiculoSeleccionado, setVehiculoSeleccionado] = useState(null);
  const [estancias, setEstancias] = useState([]);
  const [horasEstancia, setHorasEstancia] = useState(1);

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleVehiculoChange = e => {
    setNuevoVehiculo({ ...nuevoVehiculo, [e.target.name]: e.target.value });
  };

  const handleLogin = async e => {
    e.preventDefault();
    setError(null);
    try {
      const res = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: form.username, password: form.password })
      });
      const data = await res.json();
      if (!res.ok) throw new Error('Credenciales incorrectas');
      if (data.error) throw new Error('Data error');
      if (data.username == undefined) throw new Error('undefined');
      setUsuario(data);
      setView('vehiculos');
      cargarVehiculos(data.id);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleRegister = async e => {
    e.preventDefault();
    setError(null);
    try {
      const res = await fetch('http://localhost:8080/api/usuarios', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form)
      });
      if (res.status == 409) {
        throw new Error('Username ya existe');
      }
      if (!res.ok) throw new Error('Error al registrar usuario');
      await res.json();
      alert('¡Usuario registrado!');
      setView('login');
    } catch (err) {
      setError(err.message);
    }
  };

  const cargarVehiculos = async (usuarioId) => {
    try {
      const res = await fetch(`http://localhost:8080/api/usuarios/${usuarioId}/vehiculos`);
      const data = await res.json();
      setVehiculos(data);
    } catch (err) {
      setError('Error al cargar vehículos');
    }
  };

  const handleAddVehiculo = async e => {
    e.preventDefault();
    setError(null);
    try {
      const res = await fetch('http://localhost:8080/api/vehiculos', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          matricula: nuevoVehiculo.matricula,
          usuario: { id: usuario.id }
        })
      });
      if (res.status == 409) throw new Error('Matrícula ya existe');
      if (!res.ok) throw new Error('Error al agregar vehículo');
      setNuevoVehiculo({ matricula: '' });
      cargarVehiculos(usuario.id);
    } catch (err) {
      setError(err.message);
    }
  };

  const cargarEstancias = async (vehiculoId) => {
    try {
      const res = await fetch(`http://localhost:8080/api/vehiculos/${vehiculoId}/estancias`);
      const data = await res.json();
      setEstancias(data);
    } catch (err) {
      setError('Error al cargar estancias');
    }
  };

  const handleAddEstancia = async vehiculoId => {
    setError(null);
    try {
      const res = await fetch('http://localhost:8080/api/estancias', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          vehiculo: { id: vehiculoId },
          horaEntrada: new Date().toISOString(),
          horasPagadas: 1
        })
      });
      if (!res.ok) throw new Error('Error al añadir estancia');
      cargarEstancias(vehiculoId);
      cargarVehiculos(usuario.id);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDeleteVehiculo = async vehiculoId => {
    setError(null);
    try {
      const res = await fetch(`http://localhost:8080/api/vehiculos/${vehiculoId}`, {
        method: 'DELETE'
      });
      if (!res.ok) throw new Error('Error al eliminar vehículo');
      cargarVehiculos(usuario.id);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDeleteEstancia = async estanciaId => {
    setError(null);
    try {
      const res = await fetch(`http://localhost:8080/api/estancias/${estanciaId}`, {
        method: 'DELETE'
      });
      if (!res.ok) throw new Error('Error al eliminar estancia');
      cargarEstancias(vehiculoSeleccionado.id);
    } catch (err) {
      setError(err.message);
    }
  };

  if (vehiculoSeleccionado) {
    return (
      <div>
        <h2>Estancias de {vehiculoSeleccionado.matricula}</h2>
        {estancias.length === 0 ? (
          <p>No hay estancias para este vehículo.</p>
        ) : (
          <table border="1" cellPadding="5">
            <thead>
              <tr>
                <th>ID</th>
                <th>Entrada</th>
                <th>Salida</th>
                <th>Horas pagadas</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {estancias.map(e => (
                <tr key={e.id}>
                  <td>{e.id}</td>
                  <td>{e.horaEntrada}</td>
                  <td>{e.horaSalida || '-'}</td>
                  <td>{e.horasPagadas}</td>
                  <td>
                    <button onClick={() => handleDeleteEstancia(e.id)}>
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
        <form
          onSubmit={e => {
            e.preventDefault();
            handleAddEstancia(vehiculoSeleccionado.id, horasEstancia);
          }}
        >
          <label>
            Horas a estacionar:
            <input
              type="number"
              min="1"
              value={horasEstancia}
              onChange={e => setHorasEstancia(Number(e.target.value))}
              required
            />
          </label>
          <button type="submit">Añadir estancia</button>
        </form>
        <button onClick={() => setVehiculoSeleccionado(null)}>Volver</button>
      </div>
    );
  }

  if (view === 'vehiculos' && usuario) {
    return (
      <div>
        <h1>Vehículos de {usuario.username}</h1>
        <form onSubmit={handleAddVehiculo}>
          <input
            type="text"
            name="matricula"
            placeholder="Matrícula"
            value={nuevoVehiculo.matricula}
            onChange={handleVehiculoChange}
            required
          />
          <button type="submit">Agregar vehículo</button>
        </form>
        {vehiculos.length === 0 ? (
          <p>No tienes vehículos registrados.</p>
        ) : (
          <table border="1" cellPadding="5">
            <thead>
              <tr>
                <th>Matrícula</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {vehiculos.map(v => (
                <tr key={v.id}>
                  <td>{v.matricula}</td>
                  <td>
                    <button onClick={() => {
                      setVehiculoSeleccionado(v);
                      cargarEstancias(v.id);
                    }}>
                      Ver estancias / Añadir estancia
                    </button>
                    {(!v.estancias || v.estancias.length === 0) && (
                      <button onClick={() => handleDeleteVehiculo(v.id)}>
                        Eliminar vehículo
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
        <button onClick={() => { setUsuario(null); setView('login'); }}>Cerrar sesión</button>
      </div>
    );
  }

  return (
    <div>
      <h1>{view === 'login' ? 'Iniciar Sesión' : 'Registrar Usuario'}</h1>
      {error && <p style={{color: 'red'}}>Error: {error}</p>}
      <form onSubmit={view === 'login' ? handleLogin : handleRegister}>
        <input
          type="text"
          name="username"
          placeholder="Usuario"
          value={form.username}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Contraseña"
          value={form.password}
          onChange={handleChange}
          required
        />
        {view === 'register' && (
          <input
            type="text"
            name="nombre"
            placeholder="Nombre"
            value={form.nombre}
            onChange={handleChange}
            required
          />
        )}
        <button type="submit">{view === 'login' ? 'Entrar' : 'Registrar'}</button>
      </form>
      <button onClick={() => {
        setError(null);
        setView(view === 'login' ? 'register' : 'login')
      }}>
        {view === 'login' ? '¿No tienes cuenta? Regístrate' : '¿Ya tienes cuenta? Inicia sesión'}
      </button>
    </div>
  );
}

export default App;