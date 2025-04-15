from .models import Bebida
from .schemas import BebidaSchema
from .exceptions import BebidaYaExiste, BebidaNoEncontrada
import json

def obtener_menu():
    return Bebida.listar()


def agregar_bebida(bebida: BebidaSchema):
    if Bebida.buscar(bebida.name):
        raise BebidaYaExiste()
    Bebida.agregar(json.loads(bebida.model_dump_json()))
    return bebida


def buscar_bebida(nombre: str):
    bebida = Bebida.buscar(nombre)
    if not bebida:
        raise BebidaNoEncontrada()
    return bebida
