import React, { useState } from 'react';
import OrderForm from './components/OrderFrom1';
import OrderHistory from './components/OrderHistory1';
import './App.css'; // Importar estilos globales

function App() {
  const [refresh, setRefresh] = useState(0);

  const handleOrderSuccess = () => {
    setRefresh(refresh + 1); // cambia el trigger para actualizar historial
  };

  return (
  
    <div>
    <nav className="navbar">
      <div className="navbar-brand">VirtualCoffe</div>
      <ul className="navbar-links">
        <li><a href="http://localhost:4200/">Menú</a></li>
      </ul>
    </nav>
      <OrderForm onOrderSuccess={handleOrderSuccess} />
      <hr />
      <OrderHistory refreshTrigger={refresh} />
    </div>
  );
}

export default App;
