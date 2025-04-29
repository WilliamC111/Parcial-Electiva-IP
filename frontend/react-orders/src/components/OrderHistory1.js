import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './OrderHistory.css'; // <-- Importar los estilos aquí

function OrderHistory({ refreshTrigger }) {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8081/orders')
      .then(response => setOrders(response.data))
      .catch(error => console.error('Error al obtener pedidos:', error));
  }, [refreshTrigger]);

  return (
    <div className="order-history-container">
      <h2>Historial de pedidos</h2>
      {orders.length === 0 ? (
        <p>No hay pedidos aún.</p>
      ) : (
        <ul>
          {orders.map((order, idx) => (
            <li key={idx} className="order-card">
              <strong>Pedido #{idx + 1}</strong> - Fecha: {new Date(order.orderDate).toLocaleString()}
              <ul className="order-items">
                {order.items.map((item, itemIdx) => (
                  <li key={itemIdx}>
                    {item.quantity}x {item.drinkName} - {item.size}
                  </li>
                ))}
              </ul>
              <p className="order-total">
                Total: ${order.total.toFixed(2)} - Estado: {order.status}
              </p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default OrderHistory;
