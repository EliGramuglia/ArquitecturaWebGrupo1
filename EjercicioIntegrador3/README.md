# 📚 Registro de Estudiantes - API REST

Proyecto de gestión de estudiantes y carreras universitarias desarrollado con Spring Boot.
El sistema permite registrar estudiantes, inscribirlos en carreras, consultar inscripciones, filtrar estudiantes por carrera y ciudad, y generar reportes de inscriptos y egresados.

Este proyecto fue realizado en el marco de la asignatura "Arquitectura Web" en la Tecnicatura Universitaria en Desarrollo de Aplicaciones Informáticas (TUDAI), UNICEN.

## 🎯 Objetivos del proyecto

- Diseñar y desarrollar una API REST que gestione estudiantes, carreras e inscripciones.
- Implementar consultas personalizadas mediante JPQL y Spring Data JPA.
- Exponer los endpoints de manera clara y estructurada utilizando controladores REST.
- Aplicar buenas prácticas de arquitectura, separación de capas y uso de patrones de diseño.

## 🛠️ Tecnologías utilizadas
- **JAVA 21**
- **SPRING BOOT**
  - **Spring Web** (para construir la API REST)
  - **Spring Data JPA** (para la persistencia)
  - **Spring Boot Dev Tools** (recarga automática y mejoras en desarrollo)
  - **Spring Boot Configuration Processor** (autocompletado y validación de propiedades)
  - **MySQL Driver** (conexión con la base de datos MySQL)
  - **Lombok** (generación automática de getters, setters y constructores)
- **JPA / Hibernate** (API para implementar mapeo objeto-relacional)
- **JPQL** (para hacer consultas de persistencia en Java (JPA)
- **MySQL** (base de datos relacional)
- **Docker** (para levantar la base de datos)
- **IntelliJ IDEA Ultimate** (IDE de desarrollo)
- **Maven** como gestor de dependencias
- **Postman / Insomnia** (para testeo de endpoints)

## 🏛️️ Arquitectura del sistema
El sistema sigue una **arquitectura en capas**, compuesta por:

- **Controller** → gestiona las solicitudes HTTP y las respuestas.
- **Service** → contiene la lógica de negocio y las validaciones.
- **Repository** → maneja el acceso a los datos mediante *Spring Data JPA*.
- **DTOs (Data Transfer Objects)** → permiten transferir datos entre capas sin exponer directamente las entidades.
- **GlobalExceptionHandler** → manejo centralizado de errores y excepciones.


## 🏗️ Patrones de diseño aplicados
En el desarrollo del sistema se utilizaron los siguientes patrones de diseño arquitectónico:
- **Repository**: para aislar la persistencia de una entidad.
- **DTO (Data Transfer Object)**: transferencia de datos entre capas de la aplicación, desacoplando la entidad de la lógica de negocio (ocultando la información de mis entities). → seguridad, separación de intereses, performance.

## 📦 Requisitos previos
- Tener instalado [Docker](https://www.docker.com/).
- Tener instalado [Java JDK 21](https://adoptium.net/).
- Agregar las dependencias de [Maven](https://mvnrepository.com/)

## 🚀 Cómo levantar el proyecto
1. Clonar el repositorio:
   ```bash
   git clone <https://github.com/EliGramuglia/ArquitecturaWebGrupo1.git>
   cd EjercicioIntegrador3

2.  Levantar la base de datos con Docker 🐳:
    ```bash
    docker-compose up -d

- Si utilizas el IDE Intellij IDEA podes levantarlo aun mas facilmente haciendo click en la seccion de services en el archivo de docker-compose.yml
- Esto inicia un contenedor con MySQL en el puerto 8080.

3. Abrir el proyecto y ejecutar la aplicación desde IntelliJ o con:
    ```bash
   mvn spring-boot:run

4. Acceder a la API desde el navegador o Postman en:
    ```bash
   http://localhost:8080/estudiantes

## ⚙️ Configuración del proyecto
Para que la aplicación funcione correctamente, se debe tener configurado el archivo `application.yml` con los datos de conexión a la base de datos.

### Propiedades principales a configurar:
- **URL de conexión**: `jdbc:mysql://localhost:3306/Entregable3?createDatabaseIfNotExist=true`
- **Usuario**: `root`
- **Contraseña**: *(vacía por defecto, cambiar según tu entorno)*

## 🔍 Funciones implementadas:
- Dar de alta un estudiante
- Matricular un estudiante en una carrera
- Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple
- Recuperar un estudiante, en base a su número de libreta universitaria
- Recuperar todos los estudiantes, en base a su género
- Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
- Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia
- Generar un reporte de las carreras, que para cada carrera incluya información de los inscriptos y egresados por año.  
  Se deben ordenar las carreras alfabéticamente, y presentar los años de manera cronológica

## 🖥️ Endpoints de la API
La API REST expone los siguientes endpoints principales:

### Estudiantes
- `GET /estudiantes` → Listar todos los estudiantes
- `GET /estudiantes/lu/{lu}` → Buscar estudiante por número de libreta
- `GET /estudiantes/genero/{genero}` → Listar estudiantes por género
- `GET /estudiantes/{carrera}/{ciudad}` → Buscar estudiantes por carrera y ciudad
- `POST /estudiantes` → Crear un nuevo estudiante
- `GET /estudiantes/{id}` → Buscar estudiante por su PK
- `DELETE /estudiantes/{id}` → Eliminar un estudiante
- `UPDATE /estudiantes/{id}` → Editar un estudiante

### Carreras
- `GET /carreras` → Listar todas las carreras
- `GET /carreras/{id}` → Buscar una carrera por su PK
- `POST /carreras` → Crear una nueva carrera
- `DELETE /carreras/{id}` → Eliminar una carrera
- `UPDATE /carreras/{id}` → Editar una carrera
- `GET /carreras/carreras-inscriptos` → Listar carreras ordenadas por cantidad de inscriptos
- `GET /carreras/reporte-carreras` → Generar reporte de inscriptos y egresados por año

### Inscripciones
- `GET /inscripciones` → Listar todas las inscripciones
- `GET /inscripciones/{dni}/{idCarrera}` → Buscar una inscripción por su PK
- `POST /inscripciones` → Matricular un estudiante en una carrera
- `PUT /inscripciones/{dni}/{idCarrera}` → Actualizar fecha de graduación
- `DELETE /inscripciones/{dni}/{idCarrera}` → Eliminar inscripción

> 💡 Para más detalles sobre los request/response, se puede importar el archivo [Postman Collection](./Entregable3.postman_collection.json) en Postman o Insomnia.


## 👨‍💻 Autores del proyecto
- Acosta Franco
- Cabrera Maria
- Eliana Gramuglia