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

# --- Validaciones con datos inválidos ---

@pytest.mark.parametrize("bebida_invalida", [
    {"name": "", "availableSizes": {"Small": 1.0}, "imageUrl": "http://ejemplo.com"},
    {"name": "ConPrecioNegativo", "availableSizes": {"Small": -1.0}, "imageUrl": "http://ejemplo.com"},
    {"name": "SinTamanos", "availableSizes": {}, "imageUrl": "http://ejemplo.com"},
    {"name": "SinURL", "availableSizes": {"Small": 2.0}, "imageUrl": "no_es_url"},
    {"name": "NoDict", "availableSizes": ["Small", "Large"], "imageUrl": "http://ejemplo.com"}
])
def test_post_bebida_datos_invalidos(bebida_invalida):
    response = client.post("/menu", json=bebida_invalida)
    assert response.status_code == 422

# --- Consultar bebida existente ---

def test_get_bebida_por_nombre():
    response = client.get("/menu/TDDLatte")
    assert response.status_code == 200
    data = response.json()
    assert data["name"] == "TDDLatte"
    assert "availableSizes" in data

# --- Consultar bebida inexistente ---

def test_get_bebida_inexistente():
    response = client.get("/menu/BebidaQueNoExiste")
    assert response.status_code == 404
    assert response.json()["detail"] == "Bebida no encontrada."

# --- Obtener información detallada ---

def test_get_info_bebida_existente():
    response = client.get("/menu/info/TDDLatte")
    assert response.status_code == 200
    data = response.json()
    assert data["nombre"] == "TDDLatte"
    assert "tamaños" in data
    assert isinstance(data["tamaños"], dict)
    assert "imagen" in data

def test_get_info_bebida_inexistente():
    response = client.get("/menu/info/BebidaQueNoExiste")
    assert response.status_code == 404
    assert response.json()["detail"] == "Bebida no encontrada."

# --- Stress test: varias bebidas únicas ---

def test_agregar_varias_bebidas_unicas():
    for i in range(5):
        bebida = {
            "name": f"AutoTest_{i}",
            "availableSizes": {"Size": i + 1.0},
            "imageUrl": f"http://ejemplo.com/img_{i}.jpg"
        }
        response = client.post("/menu", json=bebida)
        assert response.status_code in (200, 400)  # 400 si ya existe
