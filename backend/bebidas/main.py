from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from bebidas.routes import router as bebidas_router

app = FastAPI(title="API de Bebidas - VirtualCoffee")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:4200" , "http://localhost:3000"],  
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(bebidas_router, prefix="/menu", tags=["Menu"])
