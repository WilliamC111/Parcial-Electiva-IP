from fastapi import HTTPException, status

class BebidaYaExiste(HTTPException):
    def __init__(self):
        super().__init__(status_code=status.HTTP_400_BAD_REQUEST, detail="La bebida ya existe.")

class BebidaNoEncontrada(HTTPException):
    def __init__(self):
        super().__init__(status_code=status.HTTP_404_NOT_FOUND, detail="Bebida no encontrada.")
