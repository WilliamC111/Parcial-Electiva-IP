import React, { useEffect, useState } from 'react';
import axios from 'axios';

function OrderHistory({ refreshTrigger }) {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8081/orders')
      .then(response => setOrders(response.data))
      .catch(error => console.error('Error al obtener pedidos:', error));
  }, [refreshTrigger]);

  return (
    <div>
      <h2>Historial de pedidos</h2>
      <ul>
        {orders.map((order, idx) => (
          <li key={idx}>
            {order.name} - {order.size}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default OrderHistory;
