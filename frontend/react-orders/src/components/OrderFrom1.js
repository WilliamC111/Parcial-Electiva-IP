import React, { useState, useEffect } from 'react';
import axios from 'axios';

function OrderForm({ onOrderSuccess }) {
  const [menu, setMenu] = useState([]);
  const [selectedName, setSelectedName] = useState('');
  const [selectedSize, setSelectedSize] = useState('');
  const [quantity, setQuantity] = useState(1); // nuevo estado
  const [error, setError] = useState('');

  useEffect(() => {
    axios.get('http://localhost:8000/menu')
      .then(res => setMenu(res.data))
      .catch(err => console.error('Error al obtener el menú:', err));
  }, []);

  const selectedDrink = menu.find(drink => drink.name === selectedName);
  const availableSizes = selectedDrink ? Object.keys(selectedDrink.availableSizes) : [];
  const selectedPrice = selectedDrink?.availableSizes?.[selectedSize] || '';

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!selectedName || !selectedSize || quantity < 1) {
      setError('Completa todos los campos y selecciona al menos una unidad.');
      return;
    }

    const payload = {
      items: [
        {
          drinkName: selectedName,
          size: selectedSize,
          quantity: quantity
        }
      ]
    };

    console.log("Enviando pedido:", payload);

    try {
      await axios.post('http://localhost:8081/orders', payload);
      setSelectedName('');
      setSelectedSize('');
      setQuantity(1);
      setError('');
      onOrderSuccess();
    } catch (err) {
      setError('Error al hacer el pedido. Verifica los campos.');
    }
  };

  return (
    <div>
      <h2>Hacer un pedido</h2>
      <form onSubmit={handleSubmit}>
        <select value={selectedName} onChange={(e) => {
          setSelectedName(e.target.value);
          setSelectedSize('');
        }}>
          <option value="">-- Selecciona bebida --</option>
          {menu.map((drink, idx) => (
            <option key={idx} value={drink.name}>{drink.name}</option>
          ))}
        </select>

        <select value={selectedSize} onChange={(e) => setSelectedSize(e.target.value)} disabled={!selectedName}>
          <option value="">-- Selecciona tamaño --</option>
          {availableSizes.map((size, idx) => (
            <option key={idx} value={size}>
              {size} - ${selectedDrink.availableSizes[size].toFixed(2)}
            </option>
          ))}
        </select>

        <input
          type="number"
          value={quantity}
          min="1"
          onChange={(e) => setQuantity(parseInt(e.target.value))}
          placeholder="Cantidad"
          style={{ marginTop: '0.5rem', width: '60px' }}
        />

        {selectedDrink?.imageUrl && (
          <div style={{ marginTop: '1rem' }}>
            <img
              src={selectedDrink.imageUrl}
              alt={selectedDrink.name}
              style={{ width: '150px', borderRadius: '8px' }}
            />
          </div>
        )}

        {selectedPrice && (
          <p><strong>Precio unitario:</strong> ${selectedPrice.toFixed(2)}</p>
        )}

        <button type="submit" disabled={!selectedName || !selectedSize}>
          Enviar pedido
        </button>
      </form>

      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}

export default OrderForm;
