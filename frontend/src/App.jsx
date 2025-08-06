import { useState } from 'react';
import './App.css';

function App() {
  const [view, setView] = useState('login'); // 'login' o 'register'
  const [form, setForm] = useState({ username: '', password: '', nombre: '' });
  const [error, setError] = useState(null);
  const [usuario, setUsuario] = useState(null);
  const [vehiculos, setVehiculos] = useState([]);

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
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
      if (!res.ok) throw new Error('Credenciales incorrectas frontend');
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

  if (view === 'vehiculos' && usuario) {
    return (
      <div>
        <h1>Vehículos de {usuario.username}</h1>
        {vehiculos.length === 0 ? (
          <p>No tienes vehículos registrados.</p>
        ) : (
          <ul>
            {vehiculos.map(v => (
              <li key={v.id}>{v.matricula}</li>
            ))}
          </ul>
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
      <button onClick={() => setView(view === 'login' ? 'register' : 'login')}>
        {view === 'login' ? '¿No tienes cuenta? Regístrate' : '¿Ya tienes cuenta? Inicia sesión'}
      </button>
    </div>
  );
}

export default App;