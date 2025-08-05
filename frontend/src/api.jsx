export async function login(username, password) {
    const res = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });
      if (!res.ok) throw new Error('Credenciales incorrectas frontend');
      return res.json();
}

const handleRegister = async e => {
  e.preventDefault();
  setError(null);
  try {
    await api.register(form); // Use api.register
    alert('¡Usuario registrado!');
    setView('login');
  } catch (err) {
    setError(err.message);
  }
};