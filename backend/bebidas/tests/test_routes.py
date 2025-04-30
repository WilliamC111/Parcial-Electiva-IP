import sys
import os
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "..", "..")))

import pytest
from fastapi.testclient import TestClient
from bebidas.main import app

client = TestClient(app)

# Bebida base para pruebas
bebida_valida = {
    "name": "TDDLatte",
    "availableSizes": {
        "Small": 2.5,
        "Medium": 3.0
    },
    "imageUrl": "http://ejemplo.com/latte.jpg"
}

# --- Listar menú ---

def test_get_menu_devuelve_lista():
    response = client.get("/menu")
    assert response.status_code == 200
    assert isinstance(response.json(), list)

# --- Agregar bebida válida ---

def test_post_bebida_valida():
    response = client.post("/menu", json=bebida_valida)
    assert response.status_code == 200
    data = response.json()
    assert data["name"] == "TDDLatte"
    assert "availableSizes" in data
    assert data["availableSizes"]["Small"] == 2.5

# --- Agregar bebida duplicada ---

def test_post_bebida_duplicada():
    response = client.post("/menu", json=bebida_valida)
    assert response.status_code == 400
    assert response.json()["detail"] == "La bebida ya existe."

# --- Stress test: varias bebidas únicas ---

def test_agregar_varias_bebidas_unicas():
    for i in range(5):
        bebida = {
            "name": f"AutoTest_{i}",
            "availableSizes": {"Size": i + 1.0},
            "imageUrl": f"http://ejemplo.com/img_{i}.jpg"
        }
        response = client.post("/menu", json=bebida)
        assert response.status_code in (200, 400) 
