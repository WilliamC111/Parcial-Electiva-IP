# Reflexión sobre la Evaluación Práctica - VirtualCoffee

## 📚 Lecciones Aprendidas

### Integración de Sistemas
- La comunicación entre APIs Java/Python requiere **contratos bien definidos** (DTOs compartidos)

- Las diferencias en manejo de errores entre FastAPI y Spring Boot fueron un desafío clave
- **Ejemplo**: Fue necesario  estandarizar formatos para la persistencia de datos
  - Spring Boot: JSON
  - FastAPI: JSON

- Los mocks realizados en la API de orders son funcionales en proyectos de corto alcance pero consideramos que un proyecto a gran escala se requiere de una implementación más robusta
- **Ejemplo**: Spring Boot: Mockito

- Las válidaciones del areá de Frontend más sencillas se obvian al asumir que el usuario  conoce a la perfección el sistema, lo cúal puede  ocasionar  fallos o respuestas erroneas en el aplicativo

- El diseño inicial y la abstracción que se realice del proyecto lo es todo tanto a la hora de codificar como en el diseño y desarrollo de las pruebas. 

## 📚 Mejores Prácticas

-- El manejo de archivos de configuracion con los @beans permite una mayor
flexibilidad en la configuración de los servicios

-- La separación de responsabilidades y componentes principalemente en el frontend hace la modularización de los servicios más fácil

-- La modularizacion y contrucción por capas del proyecto permite un testeo más sencillo

--La consideración de múltiples excepciones en el back permite dar una guía sencilla de los aspectos relevantes que deberian tenerse en ceunta en el Frontend

## 📚 Principales dificultades

- La sincronización de datos entre diferentes servicios es **un desafío clave** aún más cuando se intenta modularizar el sistema hasta donde sea posible


- Las pruebas de Api´s cruzdas representaron un reto para el grupo de trabajo por el
contraste de datos y el tipo de información que se pasaba en cada endpoint

- La redirección entre los dos frontends fue un reto en cuanto a integración ya que comunmente se habia estado realizando en una unica tecnologia en su totalidad

- El uso de herramientas como cucumber en un proyecto más robusto requería una mayor
comprensión de las herramientas y su integración con el entorno de desarrollo

