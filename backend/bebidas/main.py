from fastapi import FastAPI
from bebidas.routes import router as bebidas_router

app = FastAPI(title="API de Bebidas - VirtualCoffee")

app.include_router(bebidas_router, prefix="/menu", tags=["Menu"])
