import { useState, useEffect } from 'react';

function App() {
  // Estado para usuarios
  const [usuarios, setUsuarios] = useState([
    // Datos simulados
    { id: 1, username: 'usuario1', nombre: 'Juan' },
    { id: 2, username: 'usuario2', nombre: 'Ana' }
  ]);
  // const [error, setError] = useState(null);

  // useEffect(() => {
  //   fetch('http://localhost:8080/api/usuarios')
  //     .then(res => res.json())
  //     .then(data => setUsuarios(data))
  //     .catch(err => setError(err.message));
  // }, []);

  return (
    <div>
      <h1>Lista de Usuarios</h1>
      {/* {error && <p style={{color: 'red'}}>Error: {error}</p>} */}
      <ul>
        {usuarios.map(u => (
          <li key={u.id}>{u.username} - {u.nombre}</li>
        ))}
      </ul>
    </div>
  );
}

export default App;