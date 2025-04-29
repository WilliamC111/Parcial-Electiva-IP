import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './OrderForm.css'; // <-- Importar estilos

function OrderForm({ onOrderSuccess }) {
  const [menu, setMenu] = useState([]);
  const [selectedName, setSelectedName] = useState('');
  const [selectedSize, setSelectedSize] = useState('');
  const [quantity, setQuantity] = useState(1);
  const [selectedItems, setSelectedItems] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    axios.get('http://localhost:8000/menu')
      .then(res => setMenu(res.data))
      .catch(err => console.error('Error al obtener el menú:', err));
  }, []);

  const selectedDrink = menu.find(drink => drink.name === selectedName);
  const availableSizes = selectedDrink ? Object.keys(selectedDrink.availableSizes) : [];
  const selectedPrice = selectedDrink?.availableSizes?.[selectedSize] || '';

  const handleAddItem = (e) => {
    e.preventDefault();

    if (!selectedName || !selectedSize || quantity < 1) {
      setError('Completa todos los campos para agregar un item.');
      return;
    }

    const newItem = {
      drinkName: selectedName,
      size: selectedSize,
      quantity: quantity
    };

    setSelectedItems([...selectedItems, newItem]);
    setSelectedName('');
    setSelectedSize('');
    setQuantity(1);
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (selectedItems.length === 0) {
      setError('Debes agregar al menos una bebida a la orden.');
      return;
    }

    const payload = {
      items: selectedItems
    };

    try {
      await axios.post('http://localhost:8081/orders', payload);
      setSelectedItems([]);
      setError('');
      onOrderSuccess();
    } catch (err) {
      setError('Error al hacer el pedido. Verifica los datos.');
    }
  };

  return (
    <div className="order-form-container">
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
          onChange={(e) => {
            const val = e.target.value;
            setQuantity(val === '' ? '' : parseInt(val));
          }}
          placeholder="Cantidad"
        />

        <button onClick={handleAddItem}>
          Agregar bebida
        </button>

        {/* Lista de bebidas agregadas */}
        <div>
          <h3>Bebidas en la orden:</h3>
          {selectedItems.length === 0 ? (
            <p>No has agregado bebidas aún.</p>
          ) : (
            <ul>
              {selectedItems.map((item, idx) => (
                <li key={idx}>
                  {item.quantity}x {item.drinkName} - {item.size}
                </li>
              ))}
            </ul>
          )}
        </div>

        <button type="submit">
          Enviar Pedido
        </button>
      </form>

      {error && <p className="error-message">{error}</p>}
    </div>
  );
}

export default OrderForm;
