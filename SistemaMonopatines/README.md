# 🛴 Sistema de Alquiler de Monopatines - API REST

Proyecto backend para la gestión integral de un sistema de alquiler de monopatines eléctricos, desarrollado con Spring Boot. Este sistema está compuesto por varios microservicios que se comunican entre sí usando Feign Client, y está diseñado para gestionar usuarios, viajes, monopatines, paradas y tarifas.

Este proyecto se desarrolló en el marco de la asignatura **Arquitectura Web** de la **Tecnicatura Universitaria en Desarrollo de Aplicaciones Informáticas (TUDAI), UNICEN**.

---

## 🎯 Objetivos del proyecto

- Construir una arquitectura basada en microservicios para un sistema de alquiler de monopatines.
- Gestionar usuarios, viajes, paradas, monopatines y tarifas de forma distribuida.
- Implementar comunicación entre microservicios con Feign Client.
- Aplicar validaciones y manejo centralizado de errores para garantizar robustez.
- Calcular costos de viajes dinámicamente según tipo de usuario y condiciones del viaje.
- Exponer APIs REST claras y seguras con autenticación JWT.

---

## 🛠️ Tecnologías utilizadas

- Java 21
- Spring Boot (Web, Data JPA, Validation, Cloud OpenFeign)
- Hibernate / JPA
- Jakarta Validation (Bean Validation)
- Lombok
- MySQL
- Docker (para base de datos y microservicios auxiliares)
- Maven
- Postman / Curl para pruebas
- Spring Cloud Gateway (API Gateway)
- Springdoc OpenAPI (Swagger UI para documentación)
- JWT (seguridad y autenticación)

---

## 🏛️ Arquitectura del sistema

El sistema está compuesto por varios microservicios independientes:

- **Usuario:** gestión de usuarios y sus cuentas (premium, kilómetros acumulados).
- **Viaje:** gestión de viajes realizados con monopatines y calculo de su costo.
- **Monopatin:** gestión de monopatines disponibles.
- **Parada:** gestión de paradas dentro del sistema.
- **Gateway:** API Gateway que unifica el acceso externo y enruta peticiones a microservicios.
- **Mock:** servicios simulados para pruebas.
- **Integración IA:** integración con IA para funcionalidades avanzadas.

---

## 🏗️ Patrones de diseño aplicados

Cada microservicio está organizado en capas clásicas:

- **Controller:** manejo de solicitudes HTTP y exposición de endpoints REST.
- **Service:** lógica de negocio y validaciones.
- **Repository:** acceso a datos con Spring Data JPA.
- **DTOs:** objetos de transferencia de datos entre capas y microservicios.
- **Feign Clients:** para consumir APIs de otros microservicios de forma declarativa.
- **GlobalExceptionHandler:** manejo centralizado de errores y respuestas.
- **Mapper:** Mapper: encargados de convertir entre entidades y DTOs para desacoplar capas y evitar exponer directamente la persistencia.

---

## 📦 Requisitos previos

- JDK 21
- Docker (para levantar bases de datos y microservicios mock)
- Maven
- Configuración de red para ejecución local (puertos y URLs)
- Postman o herramientas similares para probar APIs

---

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
   http://localhost:8080/usuarios

## ⚙️ Configuración del proyecto
Para que la aplicación funcione correctamente, se debe tener configurado el archivo `application.yml` con los datos de conexión a la base de datos.

---

## 🔍 Servicios implementados:
- Como administrador quiero poder generar un reporte de uso de monopatines por kilómetros
para establecer si un monopatín requiere de mantenimiento. Este reporte debe poder
configurarse para incluir (o no) los tiempos de pausa.
- Como administrador quiero poder anular cuentas de usuarios, para inhabilitar el uso
momentáneo de la aplicación.
- Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.
- Como administrador quiero consultar el total facturado en un rango de meses de cierto año.
- Como administrador quiero ver los usuarios que más utilizan los monopatines, filtrado por
período y por tipo de usuario.
- Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
habilite los nuevos precios.
- Como usuario quiero buscar un listado de los monopatines cercanos a mi zona, para poder
encontrar un monopatín cerca de mi ubicación
- Como usuario quiero saber cuánto he usado los monopatines en un período, y opcionalmente si
  otros usuarios relacionados a mi cuenta los han usado

---

## 👨‍💻 Autores del proyecto
- Acosta Franco
- Cabrera Maria
- Eliana Gramuglia