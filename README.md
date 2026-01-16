### 📱 Aplicación Android con cliente REST y backend en Spring Boot

Este proyecto consiste en el desarrollo de una **aplicación móvil Android** que actúa como **cliente de una REST API** creada con **Spring Boot**, siguiendo una arquitectura cliente-servidor clara y escalable.

El **backend**, desarrollado en **Spring Boot**, expone una API REST encargada de gestionar la lógica de negocio y el acceso a los datos. Incluye endpoints para operaciones CRUD, validaciones, manejo de errores y respuestas estructuradas en formato **JSON**, siguiendo buenas prácticas REST. La comunicación se realiza mediante el protocolo HTTP y se apoya en tecnologías como JPA/Hibernate para la persistencia de datos.

El **cliente Android**, desarrollado en **Android Studio**, consume esta API utilizando la librería **Retrofit**, lo que permite una comunicación eficiente, tipada y desacoplada con el backend. Retrofit se encarga de las peticiones HTTP, la serialización/deserialización de datos y la gestión de respuestas asíncronas, garantizando una experiencia de usuario fluida.

La aplicación permite al usuario:

* Consultar datos obtenidos desde el servidor en tiempo real
* Enviar información al backend mediante formularios
* Visualizar respuestas dinámicas según el estado de la API
* Manejar errores de red y respuestas inválidas de forma controlada

El proyecto pone en práctica conceptos clave como:

* Arquitectura REST
* Consumo de APIs en Android
* Separación de responsabilidades entre frontend móvil y backend
* Uso de DTOs, servicios y controladores
* Comunicación asíncrona y manejo de estados

Este desarrollo demuestra la integración completa entre un **backend Java moderno** y un **cliente móvil Android**, simulando un entorno real de producción y reforzando conocimientos tanto de desarrollo backend como mobile.
