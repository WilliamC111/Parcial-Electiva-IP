from pydantic import BaseModel, Field, HttpUrl
from typing import Dict

class BebidaSchema(BaseModel):
    name: str = Field(..., min_length=1)
    availableSizes: Dict[str, float] = Field(..., min_items=1)
    imageUrl: HttpUrl

    class Config:
        schema_extra = {
            "example": {
                "name": "Cappuccino",
                "availableSizes": {
                    "Small": 3.50,
                    "Medium": 4.00,
                    "Large": 4.50
                },
                "imageUrl": "http://ejemplo.com/cappuccino.jpg"
            }
        }
