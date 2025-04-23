from fastapi import APIRouter
from .schemas import BebidaSchema
from .service import obtener_menu, agregar_bebida, buscar_bebida

router = APIRouter()

@router.get("/", summary="Obtener menú de bebidas")
def listar_menu():
    return obtener_menu()

@router.post("/", summary="Agregar nueva bebida")
def crear_bebida(bebida: BebidaSchema):
    return agregar_bebida(bebida)

@router.get("/search/{nombre}", summary="Buscar bebida por nombre")
def buscar(nombre: str):
    return buscar_bebida(nombre)

@router.get("/info/{nombre}", summary="Obtener información detallada de una bebida")
def obtener_info_bebida(nombre: str):
    bebida = buscar_bebida(nombre)
    return {
        "nombre": bebida["name"],
        "tamañosDisponibles": list(bebida["availableSizes"].keys()),
        "precioPromedio": round(sum(bebida["availableSizes"].values()) / len(bebida["availableSizes"]), 2),
        "imagen": bebida["imageUrl"]
    }
