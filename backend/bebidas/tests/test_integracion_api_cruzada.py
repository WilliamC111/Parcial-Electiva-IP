import requests
import pytest
from pathlib import Path
import json
import uuid
import time

# === Configuración de rutas ===
API_PYTHON_BEBIDAS = "http://localhost:8000/menu"
API_JAVA_ORDERS = "http://localhost:8081/orders"

ROOT_DIR = Path(__file__).resolve().parents[2]
BEBIDAS_PATH = ROOT_DIR / "Persistence" / "bebidas.json"
ORDERS_PATH = ROOT_DIR / "orders-api" / "src" / "main" / "resources" / "Orders.json"

def pedido_fue_guardado(pedido_id):
    if not ORDERS_PATH.exists():
        print("[ERROR] No se encontró el archivo orders.json")
        return False

    with open(ORDERS_PATH, "r", encoding="utf-8") as f:
        pedidos = json.load(f)
        return any(p.get("id") == pedido_id for p in pedidos)

def test_registrar_bebida_y_realizar_pedido():
    bebida_name = f"CruzaLatte_{uuid.uuid4().hex[:6]}"  # nombre único

    bebida = {
        "name": bebida_name,
        "availableSizes": {
            "Small": 2.5,
            "Medium": 3.0
        },
        "imageUrl": "http://ejemplo.com/latte.jpg"
    }

    res_post = requests.post(API_PYTHON_BEBIDAS, json=bebida)
    print("\n[DEBUG] Status registro bebida:", res_post.status_code)
    print("[DEBUG] Respuesta registro bebida:", res_post.text)
    assert res_post.status_code in (200, 201), f"Se esperaba 200 o 201 pero se obtuvo {res_post.status_code}: {res_post.text}"

    pedido = {
        "items": [
            {
                "drinkName": bebida_name,
                "size": "Medium",
                "quantity": 2
            }
        ]
    }

    res_order = requests.post(API_JAVA_ORDERS, json=pedido)
    print("[DEBUG] Status orden:", res_order.status_code)
    print("[DEBUG] Respuesta orden:", res_order.text)
    assert res_order.status_code == 201

    data = res_order.json()
    assert data["items"][0]["drinkName"] == bebida_name
    assert data["items"][0]["quantity"] == 2
    assert data["total"] == 6.0

    pedido_id = data.get("id")
    assert pedido_id is not None, "El ID del pedido no fue retornado por la API"

    time.sleep(1)

    assert pedido_fue_guardado(pedido_id), f"El pedido con ID {pedido_id} no fue encontrado en orders.json"
    print(f"Pedido {pedido_id} fue guardado correctamente.")
