# ✅ Instrucciones de Ejecución de Pruebas

Este archivo describe cómo ejecutar las pruebas del proyecto `orders_api`, el cual incluye dos tipos de pruebas:

- ✅ **Pruebas Unitarias y de bajo nivel (con JUnit 5 + Mockito)**
- 🥒 **Pruebas de Integración BDD (con Cucumber + Spring Boot)**

=========================================================================================================

## ✅ Opción 1: Pruebas Unitarias / BDD de bajo nivel con Mockito

Estas pruebas validan el comportamiento de componentes individuales como servicios, controladores y clientes REST.

### 📁 Archivos Relevantes

- `controllers/OrderControllerTest.java`
- `services/OrderServiceTest.java`
- `services/DrinkServiceClientTest.java`
- `OrdersApiApplicationTests.java`

### ▶️ Cómo Ejecutar

Puede ejecutar estas pruebas desde tu IDE (IntelliJ, Eclipse, etc.) o desde la línea de comandos.

#### 🔧 Desde la línea de comandos

<details>
<summary>Con Maven</summary>

🧪 Ejecutar una clase de prueba específica
Haz clic en el botón ▶️ para ejecutarlo como una prueba JUnit

O ejecute el set de pruebas en sutotalidad dando click en run de la clase `OrdersApiApplicationTests`

========================================================================================================

##  Opción 2: Pruebas BDD con Cucumber (Integración)
Estas pruebas validan funcionalidades completas desde una perspectiva de negocio, utilizando escenarios escritos en lenguaje natural.

📁 Archivos Relevantes
resources/features/order_management.feature

stepDefinitions/OrderStepDefinitions.java

runners/TestRunner.java

🧩 Configuración Adicional
config/CucumberSpringConfig.java

config/StepDefinitionsConfig.java

config/TestConfig.java

config/TestWebConfig.java

▶️ Cómo Ejecutar
🔧 Desde la línea de comandos
run com.virtualcoffee.orders_api.runners.TestRunner


Abrir TestRunner.java

Hacer clic en el botón ▶️ para ejecutarlo como una prueba JUnit
