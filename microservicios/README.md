# Sistema de Microservicios

Este proyecto implementa un sistema de microservicios para la gestión de productos, órdenes y autenticación, utilizando Spring Boot 3.3.5 y Java 17.

## Arquitectura

El sistema está compuesto por los siguientes microservicios:

### 1. ms-auth (Puerto 8081)

Microservicio de autenticación y autorización que maneja:

- Registro y autenticación de usuarios
- Gestión de roles (SUPERADMIN, ADMIN, USUARIO)
- Generación y validación de tokens JWT

### 2. ms-productos (Puerto 8082)

Microservicio de gestión de productos que permite:

- CRUD de productos
- Gestión de stock
- Validación de productos existentes

### 3. ms-ordenes (Puerto 8083)

Microservicio de gestión de órdenes que:

- Permite a usuarios crear órdenes
- Permite a administradores ver todas las órdenes
- Se comunica con ms-productos para validar productos

### 4. ms-eurekaserver (Puerto 8761)

Servidor de descubrimiento de servicios que:

- Centraliza el registro de microservicios
- Facilita la comunicación entre servicios
- Proporciona un dashboard para monitoreo

## Patrones de Diseño Implementados

### Builder Pattern (ms-productos)

Implementado en la clase `ProductoBuilder` para la construcción de productos:

```java
Producto producto = new ProductoBuilder()
    .nuevo()
    .conNombre("Producto 1")
    .conPrecio(new BigDecimal("99.99"))
    .conStock(100)
    .conCategoria("Electrónicos")
    .build();
```

Beneficios:

- Construcción fluida de objetos
- Validaciones centralizadas
- Código más limpio y mantenible

## Endpoints

### ms-auth

```
POST /auth/register
- Registro de usuarios
- Body: { "nombre": "string", "email": "string", "password": "string", "rol": "string" }

POST /auth/login
- Autenticación de usuarios
- Body: { "email": "string", "password": "string" }

GET /auth/validate
- Validación de token JWT
- Header: Authorization: Bearer <token>

GET /test/superadmin
GET /test/admin
GET /test/user
- Endpoints protegidos por rol
```

### ms-productos

```
POST /api/productos
- Crear producto (ADMIN)
- Body: { "nombre": "string", "precio": number, "stock": number, "categoria": "string" }

GET /api/productos
- Listar productos (AUTH)

PUT /api/productos/{id}
- Actualizar producto (ADMIN)
- Body: { "nombre": "string", "precio": number, "stock": number, "categoria": "string" }

DELETE /api/productos/{id}
- Eliminar producto (ADMIN)

PATCH /api/productos/{id}/stock
- Actualizar stock (ADMIN)
- Query: cantidad=number
```

### ms-ordenes

```
POST /api/ordenes
- Crear orden (USER)
- Body: { "usuarioId": number, "productosIds": [number] }

GET /api/ordenes
- Listar órdenes (ADMIN)
```

## Requisitos

- Java 17
- Maven 3.8+
- Spring Boot 3.3.5
- H2 Database (embebida)
- Eureka Server
- Config Server
- HashiCorp Vault

## Instalación

1. Clonar el repositorio:

```bash
git clone <url-del-repositorio>
```

2. Compilar los proyectos:

```bash
mvn clean install
```

3. Iniciar los servicios en orden:

```bash
# 1. Eureka Server
cd ms-eurekaserver
mvn spring-boot:run

# 2. Config Server
cd ../ms-configserver
mvn spring-boot:run

# 3. Auth Service
cd ../ms-auth
mvn spring-boot:run

# 4. Productos Service
cd ../ms-productos
mvn spring-boot:run

# 5. Ordenes Service
cd ../ms-ordenes
mvn spring-boot:run
```

## Configuración

### Variables de Entorno

```properties
# JWT Secret
JWT_SECRET=your-secret-key

# Database
SPRING_DATASOURCE_URL=jdbc:h2:mem:db
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=

# Eureka
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://localhost:8761/eureka/
```

### Puertos

- Eureka Server: 8761
- Config Server: 8888
- Auth Service: 8081
- Productos Service: 8082
- Ordenes Service: 8083

## Pruebas

Ejecutar las pruebas unitarias:

```bash
mvn test
```

Verificar la cobertura de código:

```bash
mvn verify
```

## Seguridad

- Autenticación mediante JWT
- Autorización basada en roles
- Validación de tokens entre microservicios
- Protección de endpoints sensibles

## Monitoreo

- Eureka Dashboard: http://localhost:8761
- H2 Console: http://localhost:8081/h2-console (ms-auth)
- H2 Console: http://localhost:8082/h2-console (ms-productos)
- H2 Console: http://localhost:8083/h2-console (ms-ordenes)

## Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request
