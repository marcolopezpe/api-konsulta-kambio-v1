# API Konsulta Kambio v1

Este proyecto es un servicio desarrollado con **Quarkus** que expone un endpoint para la consulta de tipo de cambio.  
Utiliza **PostgreSQL** como base de datos y **Dev Services** de Quarkus para levantar automáticamente un contenedor de PostgreSQL en el entorno de desarrollo, evitando la necesidad de instalar y configurar una instancia externa.

---

## 📌 Características principales

- **Quarkus 3.25.2** con Java 21.
- Uso de **Dev Services** para PostgreSQL.
- Persistencia con **Hibernate ORM con Panache**.
- Validaciones con **Hibernate Validator**.
- Cliente REST para consumo de API externa de tipo de cambio.
- Configuración centralizada en **application.yml**.
- Generación de esquema de base de datos mediante `hibernate-orm.database.generation=update`.

---

## ⚙️ Configuración del proyecto

La configuración principal se encuentra en `src/main/resources/application.yml`:

```yaml
quarkus:
  http:
    port: 8282
  rest-client:
    eapi-tipo-cambio:
      url: https://free.e-api.net.pe
      scope: Singleton
  datasource:
    username: postgres
    password: 123456789
    jdbc:
      url: jdbc:postgresql://localhost:5432/db_konsulta_kambio
    devservices:
      enabled: true
      db-name: db_konsulta_kambio
      port: 5432
      image-name: postgres:16-alpine
  hibernate-orm:
    database:
      generation: update
    log:
      sql: true
      format-sql: true
      bind-parameters: true
```

## 🐘 Base de datos con Dev Services
- No es necesario instalar PostgreSQL manualmente en desarrollo.
- Quarkus levantará automáticamente un contenedor con la imagen postgres:16-alpine.
- El nombre de la base de datos será db_konsulta_kambio.
- Las credenciales por defecto son postgres / 123456789.
- El puerto expuesto localmente es el 5432.
- El esquema y tablas se generan automáticamente en base a las entidades gracias a:

```yaml
hibernate-orm:
  database:
    generation: update
```

## 🌐 API Externa
El proyecto consume la API pública de tipo de cambio disponible en:
https://free.e-api.net.pe

Se accede mediante un cliente REST configurado en:

```yaml
rest-client:
  eapi-tipo-cambio:
    url: https://free.e-api.net.pe
    scope: Singleton
```
    
## 📡 Endpoint principal
El servicio expone el siguiente endpoint:
- URL: http://localhost:8282/api/v1/tipo-cambio
- Método: GET
- Parámetros:
    - dni (obligatorio, tipo string)

### Ejemplo de petición:

```bash
curl "http://localhost:8282/api/v1/tipo-cambio?dni=12345678"
```

### Respuesta exitosa (200):

```json
{
  "compra": 3.70,
  "venta": 3.75,
  "fecha": "2025-08-13"
}
```

### Error de validación (400):

```json
{
  "timestamp": "2025-08-12T22:21:58.365798-05:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Error de validación de parámetros",
  "path": "/api/v1/tipo-cambio",
  "violations": [
    {
      "field": "dni",
      "message": "El DNI es obligatorio y no puede estar vacio"
    }
  ]
}
```

### Error de validación (429):

```json
{
  "timestamp": "2025-08-13T20:38:25.623298-05:00",
  "status": 429,
  "error": "Too Many Requests",
  "message": "Máximo de consultas diarias alcanzado (10/10) para DNI 46491904 en 2025-08-13",
  "path": "/api/v1/tipo-cambio",
  "details": {
    "dni": "46491904",
    "fecha": "2025-08-13",
    "used": 10,
    "limit": 10
  }
}
```

### 🚀 Ejecución en desarrollo
Para levantar el proyecto en modo desarrollo con recarga en caliente:

```bash
./mvnw quarkus:dev
```

Esto iniciará:

- La aplicación en http://localhost:8282.
- Un contenedor temporal de PostgreSQL vía Dev Services.

## 📄 Licencia
Este proyecto se distribuye bajo la licencia MIT.
