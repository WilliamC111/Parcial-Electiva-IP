import json
from pathlib import Path
from typing import List, Dict

DATA_PATH = Path(__file__).resolve().parents[2] / "Persistence" / "bebidas.json"

class Bebida:

    @classmethod
    def _cargar_datos(cls) -> List[Dict]:
        try:
            if not DATA_PATH.exists():
                return []
            with open(DATA_PATH, "r", encoding="utf-8") as f:
                return json.load(f)
        except json.JSONDecodeError:
            cls._guardar_datos([])
            return []

    @classmethod
    def _guardar_datos(cls, data: List[Dict]):
        DATA_PATH.parent.mkdir(parents=True, exist_ok=True)  
        with open(DATA_PATH, "w", encoding="utf-8") as f:
            json.dump(data, f, ensure_ascii=False, indent=4)

    @classmethod
    def listar(cls) -> List[Dict]:
        return cls._cargar_datos()

    @classmethod
    def agregar(cls, bebida: Dict):
        bebidas = cls._cargar_datos()
        bebidas.append(bebida)
        cls._guardar_datos(bebidas)

    @classmethod
    def buscar(cls, nombre: str) -> Dict | None:
        bebidas = cls._cargar_datos()
        for b in bebidas:
            if b["name"].lower() == nombre.lower():
                return b
        return None
