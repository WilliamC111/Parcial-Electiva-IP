===============================================================
               VIRTUALCOFFEE - API DE ÓRDENES
                  BACKEND JAVA - SPRING BOOT
===============================================================

DESCRIPCIÓN GENERAL
--------------------
Este componente forma parte del sistema VirtualCoffee y se encarga de la
gestión de pedidos (órdenes) realizados por los clientes. Está desarrollado
con Spring Boot (Maven) y almacena los datos en un archivo JSON.

Su objetivo es ofrecer endpoints para crear, consultar y gestionar
órdenes de bebidas del menú.

---------------------------------------------------------------
ESTRUCTURA DEL PROYECTO (MAVEN)
---------------------------------------------------------------

orders-api/
│
├── src/
│   ├── main/
│   │   ├── java/com/virtualcoffee/orders_api/
│   │   │   ├── config/           → @Beans y Cors
│   │   │   ├── controllers/      → Controladores REST
│   │   │   ├── dtos/             → Objetos de transferencia de datos
│   │   │   ├── exceptions/       → Excepciones personalizadas
│   │   │   ├── models/           → Entidades del dominio
│   │   │   ├── repositories/     → Acceso a datos
│   │   │   ├── services/         → Lógica de negocio
│   │   │   └── OrdersApiApplication.java → Clase principal
│   │   │
│   │   └── resources/
│   │       ├── application.properties    → Configuración
│   │       └── orders.json        → Datos persistentes
│   │
│   └── test/                     → Pruebas unitarias e integración
│
├── pom.xml                       → Configuración Maven
└── README.md

---------------------------------------------------------------
DEPENDENCIAS PRINCIPALES
---------------------------------------------------------------

- Java 17+
- Spring Boot 3.x
- Lombok
- Jackson (JSON)
- JUnit 5
- Spring Web

Las dependencias se gestionan automáticamente a través de Maven.

---------------------------------------------------------------
CONFIGURACIÓN Y EJECUCIÓN
---------------------------------------------------------------

1. Requisitos previos:
   - JDK 17 instalado
   - Maven instalado
   - mvn wrapper:wrapper para la carpeta .mvn

2. Compilar y ejecutar:
    mvn clean
    mvn compile
    mvn package
    mvn spring-boot:run o ejecutar OrdersApiApplication.java

---------------------------------------------------------------
ENDPOINTS PRINCIPALES
---------------------------------------------------------------

1. Crear una nueva orden
   POST /orders
   Body (JSON):
   {
     "items": [
       {
         "drinkName": "Latte",
         "size": "Medium",
         "quantity": 1
       }
     ]
   }

2. Obtener todas las órdenes
   GET /orders


---------------------------------------------------------------
MODELO DE DATOS PRINCIPAL
---------------------------------------------------------------

Order {
  String id;
  List<OrderItem> items;
  LocalDateTime orderDate;
  String status; // PENDING, IN_PROGRESS, COMPLETED
  Double total;
}

OrderItem {
  String drinkName;
  String size;
  Integer quantity;
  Double price;
}

---------------------------------------------------------------
CÓDIGOS DE ERROR COMUNES
---------------------------------------------------------------

- 400: Solicitud mal formada
- 404: Orden no encontrada
- 422: Validación fallida (ej. bebida no disponible)
- 500: Error interno del servidor

---------------------------------------------------------------
PERSISTENCIA DE DATOS
---------------------------------------------------------------

Los datos se persisten en:
  src/main/resources/orders.json

El archivo se carga al iniciar la aplicación y se actualiza
automáticamente con cada modificación.

---------------------------------------------------------------
PRUEBAS
---------------------------------------------------------------

Ejecutar todas las pruebas:
  mvn test

Pruebas incluyen:
- Unitarias: Servicios,
- Integración: Controladores
- Pruebas JSON: Serialización/deserialización

---------------------------------------------------------------
OBSERVACIONES TÉCNICAS
---------------------------------------------------------------

- Arquitectura en capas (Controller-Service-Repository)
- Validación con Spring Validation
- Manejo centralizado de excepciones
- DTOs para transferencia de datos
- Inyección de dependencias
- Configuración para desarrollo/producción