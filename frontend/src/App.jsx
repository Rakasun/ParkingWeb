import { useState } from 'react';

function App() {
  const [view, setView] = useState('login'); // 'login' o 'register'
  const [form, setForm] = useState({ username: '', password: '', nombre: '' });
  const [error, setError] = useState(null);

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
      if (!res.ok) throw new Error('Credenciales incorrectas frontend');
      // Aquí podrías guardar el token o redirigir
      alert('¡Login exitoso!');
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
      if (!res.ok) throw new Error('Error al registrar usuario');
      alert('¡Usuario registrado!');
      setView('login');
    } catch (err) {
      setError(err.message);
    }
  };

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