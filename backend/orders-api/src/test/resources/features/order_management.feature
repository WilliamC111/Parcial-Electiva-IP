# language: es
Característica: Gestión de pedidos de bebidas
  Como cliente
  Quiero poder realizar pedidos de bebidas
  Para recibir mis bebidas favoritas

  Escenario: Pedido exitoso de una bebida disponible
    Dado que la bebida "Cappuccino" existe en el menú con los tamaños Small, Medium y Large
    Cuando envío una solicitud POST a "/orders" con:
      """
      {
        "items": [
          {
            "drinkName": "Cappuccino",
            "size": "Medium",
            "quantity": 2
          }
        ]
      }
      """
    Entonces la respuesta debe tener status 201
    Y la respuesta debe contener el total calculado correctamente
    Y el pedido debe guardarse en el sistema

  Escenario: Intento de pedido con bebida no disponible
    Dado que la bebida "Té Verde" no existe en el menú
    Cuando envío una solicitud POST a "/orders" con:
      """
      {
        "items": [
          {
            "drinkName": "Té Verde",
            "size": "Large",
            "quantity": 1
          }
        ]
      }
      """
    Entonces la respuesta debe tener status 404
    Y la respuesta debe contener el mensaje "Drink not found: Té Verde"