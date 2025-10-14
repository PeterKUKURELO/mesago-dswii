## 🧾 **README.md — Proyecto MesaGO**

````markdown
# 🍽️ MesaGO — Sistema de Gestión de Pedidos y Reservas (Microservicios)

### Proyecto académico — CIBERTEC (Desarrollo de Servicios Web II)
**Integrantes:**
- Peter Kukurelo Limaylla
- Camila Campos
- Añadir nombres ++

---

## 🧠 Descripción General

**MesaGO** es un sistema web basado en **arquitectura de microservicios** que permite la **gestión integral de pedidos, reservas, inventario y facturación en un restaurante**, con roles diferenciados de acceso (Administrador, Mesero y Chef).

El proyecto implementa un ecosistema distribuido utilizando **Spring Boot**, **Spring Cloud Gateway** y **MySQL**, y será consumido por un **frontend web en Angular**.

---

## 🧩 Arquitectura del Sistema

### 🏗️ Estructura de microservicios:

| Microservicio | Puerto | Descripción |
|----------------|---------|-------------|
| `gateway-service` | `8080` | API Gateway (enrutamiento y seguridad JWT) |
| `ms-auth` | `8081` | Autenticación JWT, usuarios y roles |
| `ms-pedidos` | `8082` | Gestión de pedidos y mesas |
| `ms-inventario` | `8083` | Control de insumos, proveedores e incidencias |
| `ms-reservas` | `8084` | Administración de clientes y reservas |
| `ms-facturacion` | `8085` | Generación de facturas y reportes |

Cada microservicio se comunica a través del **Gateway**, utilizando **HTTP REST + JSON**.  
Todos los módulos utilizan **bases de datos MySQL independientes**.

---

## 🧭 Diagrama de Arquitectura

```plaintext
┌──────────────────────────┐
│  FRONTEND (Angular)      │
│  Roles: Admin, Chef, Mesero │
└───────────────┬──────────┘
                │
        HTTP REST / JSON
                │
┌───────────────▼────────────────┐
│ GATEWAY SERVICE (Spring Cloud) │
│  - Enrutamiento global         │
│  - Validación JWT              │
└───────────────┬────────────────┘
    ┌───────────┼───────────────────────────────┐
    │           │               │               │
┌───▼───┐   ┌───▼───┐       ┌───▼───┐       ┌───▼───┐
│ms-auth│   │ms-pedidos│    │ms-inventario│ │ms-reservas│
└───┬───┘   └───┬──────┘    └────┬──────┘  └────┬──────┘
    │           │                 │             │
    └───────────┴────────────────────────────────┘
                 │
             ┌───▼───┐
             │ms-fact│
             └───┬───┘
                 ▼
              MySQL DB
````

---

## ⚙️ Tecnologías Utilizadas

### 🔸 Backend:

* **Java 17**
* **Spring Boot 3.4.10**
* **Spring Cloud 2023.0.3**
* **Spring Web / Data JPA / Security**
* **Spring Cloud Gateway**
* **JWT (JSON Web Token)**
* **MySQL 8**
* **Lombok**

### 🔸 Frontend:

* **Angular 17**
* **Bootstrap / Material UI**

### 🔸 DevOps:

* **Git / GitHub**
* **Render / Railway (para despliegue en la nube)**

---

## 🗂️ Estructura del Repositorio

```
MesaGO/
├── gateway-service/
│   └── application.yml (rutas y seguridad)
├── ms-auth/
│   └── application.properties (conexion MySQL y JWT)
├── ms-pedidos/
├── ms-inventario/
├── ms-reservas/
├── ms-facturacion/
└── README.md
```

---

## 🧰 Instalación y Ejecución

### 🔹 1. Clonar el repositorio:

```bash
git clone https://github.com/PeterKUKURELO/mesago-dswii.git
cd mesago-dswii
```

### 🔹 2. Crear las bases de datos en MySQL:

```sql
CREATE DATABASE mesago_auth;
CREATE DATABASE mesago_pedidos;
CREATE DATABASE mesago_inventario;
CREATE DATABASE mesago_reservas;
CREATE DATABASE mesago_facturacion;
```

### 🔹 3. Configurar `application.properties` o `application.yml` en cada microservicio:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mesago_auth
spring.datasource.username=root
spring.datasource.password=123456
```

### 🔹 4. Ejecutar cada servicio:

```bash
mvn spring-boot:run
```

> Asegúrate de usar puertos distintos por microservicio.

### 🔹 5. Probar en navegador o Postman:

```
http://localhost:8080/api/auth/login
http://localhost:8080/api/pedidos
```

---

## 🔐 Seguridad (JWT)

El servicio `ms-auth` emite un **token JWT** al iniciar sesión.
El token debe enviarse en el header de cada solicitud:

```
Authorization: Bearer <token>
```

El **Gateway Service** valida el token antes de enrutar la solicitud hacia los demás microservicios.

---

## 🧑‍💻 Roles y Permisos

| Rol               | Permisos                              |
| ----------------- | ------------------------------------- |
| **Administrador** | Acceso completo al sistema            |
| **Mesero**        | Gestión de pedidos y mesas            |
| **Chef**          | Visualiza y marca pedidos completados |

---

## 🧩 Equipo de Desarrollo

| Nombre             | Rol                         | Microservicio                |
|--------------------| --------------------------- | ---------------------------- |
| **Peter Kukurelo** | Coordinador / Líder Técnico | `gateway-service`, `ms-auth` |
| **Integrante 2**   | Backend Developer           | `ms-pedidos`                 |
| **Integrante 3**   | Backend Developer           | `ms-inventario`              |
| **Integrante 4**   | Backend Developer           | `ms-reservas`                |
| **Integrante 5**   | Backend Developer           | `ms-facturacion`             |

---

## ☁️ Despliegue en la nube

Se realizará el despliegue en **Render.com** o **Railway.app**, con un esquema de URLs como:

| Servicio    | URL                                                                                |
| ----------- | ---------------------------------------------------------------------------------- |
| Gateway     | [https://mesago-api.onrender.com](https://mesago-api.onrender.com)                 |
| Auth        | [https://mesago-auth.onrender.com](https://mesago-auth.onrender.com)               |
| Pedidos     | [https://mesago-pedidos.onrender.com](https://mesago-pedidos.onrender.com)         |
| Inventario  | [https://mesago-inventario.onrender.com](https://mesago-inventario.onrender.com)   |
| Reservas    | [https://mesago-reservas.onrender.com](https://mesago-reservas.onrender.com)       |
| Facturación | [https://mesago-facturacion.onrender.com](https://mesago-facturacion.onrender.com) |

---

## 🧾 Licencia

Proyecto académico sin fines comerciales.
Desarrollado para el curso **Desarrollo de Servicios Web II – CIBERTEC (2025)**.

---

## ✨ Créditos

**MesaGO** © 2025 — Equipo de estudiantes Cibertec
Desarrollado con ❤️ en Java, Spring Boot y Angular.


