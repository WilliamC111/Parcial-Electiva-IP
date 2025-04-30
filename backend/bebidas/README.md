===============================================================
               VIRTUALCOFFEE - API DE BEBIDAS
                  BACKEND PYTHON - FASTAPI
===============================================================

DESCRIPCIÓN GENERAL
--------------------
Este componente forma parte del sistema VirtualCoffee y se encarga de la
gestión de bebidas disponibles en el menú. Está desarrollado con FastAPI
y almacena los datos de forma persistente en un archivo JSON.

Su objetivo es ofrecer endpoints para agregar, consultar y obtener
información estructurada de las bebidas.

---------------------------------------------------------------
ESTRUCTURA DEL PROYECTO
---------------------------------------------------------------

VirtualCoffee/
│
├── Backend/
│   └── bebidas/
│       ├── main.py            → Archivo principal que levanta FastAPI
│       ├── models.py          → Lógica de acceso y persistencia de datos
│       ├── routes.py          → Definición de rutas (endpoints)
│       ├── service.py         → Lógica de negocio
│       ├── schemas.py         → Esquemas y validaciones con Pydantic
│       ├── exceptions.py      → Excepciones personalizadas
│       └── tests/
│           ├── test_routes.py                  → Pruebas unitarias y funcionales
│           ├── test_integracion_api_cruzada.py → Prueba cruzada Python ↔ Java
│           └── run_static_analysis.py          → Script de análisis estático y cobertura
│
├── Persistence/
│   └── bebidas.json           → Archivo donde se almacenan las bebidas

---------------------------------------------------------------
DEPENDENCIAS REQUERIDAS
---------------------------------------------------------------

- Python 3.11 o superior
- FastAPI
- Uvicorn
- pytest
- pylint
- mypy
- pytest-cov
- requests

Para instalar dependencias, usar el siguiente comando desde la
carpeta 'bebidas':

    pip install -r requirements.txt

---------------------------------------------------------------
FORMA DE EJECUCIÓN
---------------------------------------------------------------

1. Asegúrese de ubicarse en la carpeta raíz del backend:

    VirtualCoffee/Backend/

2. Luego, ejecute el servidor con el siguiente comando:

    uvicorn bebidas.main:app --reload

3. Una vez iniciado, puede acceder a la documentación interactiva:

    Documentación Swagger:   http://localhost:8000/docs  
    Documentación Redoc:     http://localhost:8000/redoc

---------------------------------------------------------------
ENDPOINTS DISPONIBLES
---------------------------------------------------------------

1. Obtener todas las bebidas registradas  
   Método: GET  
   Ruta:   /menu

2. Registrar una nueva bebida  
   Método: POST  
   Ruta:   /menu

   Parámetros esperados (en formato JSON):
     - name: Nombre de la bebida
     - availableSizes: Diccionario con los tamaños y precios
     - imageUrl: URL de la imagen de la bebida

3. Consultar bebida por nombre  
   Método: GET  
   Ruta:   /menu/{nombre}

4. Obtener información detallada de una bebida  
   Método: GET  
   Ruta:   /menu/info/{nombre}

   Retorna:
     - nombre
     - tamaños (con precios)
     - imagen

---------------------------------------------------------------
POSIBLES ERRORES Y CÓDIGOS
---------------------------------------------------------------

- 400: La bebida ya existe
- 404: Bebida no encontrada
- 422: Error de validación de datos (campos inválidos)

---------------------------------------------------------------
PERSISTENCIA
---------------------------------------------------------------

Los datos se almacenan en el archivo:

    VirtualCoffee/Persistence/bebidas.json

Este archivo se crea automáticamente si no existe. El sistema lo
utiliza para leer y escribir bebidas de forma persistente.

---------------------------------------------------------------
OBSERVACIONES
---------------------------------------------------------------

- Toda la lógica de negocio se encuentra desacoplada por capas.
- Las validaciones se realizan con Pydantic.
- El acceso a datos no está quemado; se persiste en disco.
- La estructura está preparada para escalar a microservicios.

---------------------------------------------------------------
PRUEBAS DEL SISTEMA
---------------------------------------------------------------

El proyecto incluye pruebas unitarias, funcionales e integradas para asegurar
el correcto funcionamiento de los endpoints y la interacción entre APIs.

===============================================================
1. PRUEBAS UNITARIAS Y FUNCIONALES CON PYTEST
===============================================================

Ubicación: 
    VirtualCoffee/Backend/bebidas/tests/test_routes.py

Estas pruebas verifican los siguientes casos:

- Obtener lista completa del menú
- Agregar bebida válida
- Validar duplicidad de bebidasS
- Insertar múltiples bebidas únicas

Ejecución:
    Desde la carpeta `bebidas/`, ejecutar:

    python -m pytest tests/test_routes.py

===============================================================
2. PRUEBA DE INTEGRACIÓN ENTRE APIS (PYTHON ↔ JAVA)
===============================================================

Ubicación:
    VirtualCoffee/Backend/bebidas/tests/test_integracion_api_cruzada.py

Simula el siguiente flujo completo:
- Registrar una bebida en la API Python
- Realizar un pedido usando esa bebida en la API Java (`orders-api`)
- Validar que el pedido fue persistido correctamente en `orders.json`

Requisitos:
- Backend Python corriendo en http://localhost:8000
- Backend Java (`orders-api`) corriendo en http://localhost:8081
- Archivo `orders.json` ubicado en:
  `orders-api/src/main/resources/orders.json`

Ejecución:
    python -m pytest tests/test_integracion_api_cruzada.py

===============================================================
3. ANÁLISIS ESTÁTICO Y COBERTURA
===============================================================

Ubicación:
    VirtualCoffee/Backend/bebidas/tests/run_static_analysis.py

Este script ejecuta:
- pylint (con reglas de docstring desactivadas: C0114, C0115, C0116)
- mypy (validación de tipos)
- pytest-cov (cobertura de pruebas)

Genera reportes `.txt` en la carpeta:
    VirtualCoffee/Backend/reportes/

Y un resumen en:
    VirtualCoffee/Backend/ANALISIS_ESTATICO.md

Ejecución:
    python tests/run_static_analysis.py
