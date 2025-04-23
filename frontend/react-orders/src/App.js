import React, { useState } from 'react';
import OrderForm from './components/OrderFrom1';
import OrderHistory from './components/OrderHistory1';

function App() {
  const [refresh, setRefresh] = useState(0);

  const handleOrderSuccess = () => {
    setRefresh(refresh + 1); // cambia el trigger para actualizar historial
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Virtual Coffee - Pedidos</h1>
      <OrderForm onOrderSuccess={handleOrderSuccess} />
      <hr />
      <OrderHistory refreshTrigger={refresh} />
    </div>
  );
}

export default App;
