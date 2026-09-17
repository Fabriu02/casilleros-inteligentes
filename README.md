# UGD Smart Lockers - Backend & Plataforma Web 🎓🔒

Este repositorio contiene la plataforma web y el servidor central (Backend) para el proyecto IoT de Casilleros Inteligentes (UGD Smart Lockers). 

> **⚠️ Nota de Despliegue:** Actualmente el proyecto se encuentra en etapa de prototipado. Todo el sistema (Servidor Java y Base de Datos MySQL) está diseñado para ejecutarse de forma **local** (`localhost`). La comunicación con el hardware (Arduino + ESP-01) se simula de manera local en la red mediante nuestra API REST a la espera de la integración física.

## 🛠️ Stack Tecnológico
* **Lenguaje:** Java
* **Framework Backend:** Spring Boot (Spring Web, Spring Data JPA)
* **Base de Datos:** MySQL 5.7+
* **Frontend:** HTML5, CSS3, Thymeleaf (Server-Side Rendering)
* **Arquitectura:** MVC (Modelo-Vista-Controlador) y API REST para Hardware.

## 📋 Reglas de Negocio Implementadas (Fase 1)
1. **Roles de Usuario:** Sistema de autenticación manual con roles (`ALUMNO` y `ADMIN`).
2. **Control de Reservas:** Un estudiante no puede tener más de 1 casillero reservado simultáneamente.
3. **Registro de Tiempo:** El sistema registra y formatea automáticamente la fecha/hora de inicio de la reserva.
4. **Modo Administrador:** El administrador tiene visibilidad total sobre los ocupantes y posee privilegios de "Forzar Liberación" sobre cualquier casillero.
5. **Autoseed de Base de Datos:** Generación automática de casilleros y usuarios al detectar una base de datos vacía.

---

## 🚀 Guía de Instalación y Ejecución Local

Para los evaluadores o desarrolladores que deseen clonar y probar el sistema localmente, sigan estos pasos:

### 1. Prerrequisitos
* Java JDK (17 o superior)
* IntelliJ IDEA (o IDE de preferencia)
* MySQL Server (Workbench o XAMPP)

### 2. Configuración de Base de Datos
Abra su gestor de MySQL local y cree una base de datos vacía ejecutando la siguiente consulta:
```sql
CREATE DATABASE smartlockers_db;
```

### 3. Variables de Entorno (Seguridad)
Por normativas de ciberseguridad, las credenciales de la base de datos no están subidas a este repositorio. Antes de ejecutar el proyecto en su IDE, debe configurar las siguientes Variables de Entorno en su perfil de ejecución (*Edit Configurations -> Environment variables*):
```env
DB_USER=root
DB_PASSWORD=su_contraseña_local
```

### 4. Ejecución
Ejecute la clase `SmartlockersApplication.java`. Hibernate (JPA) se conectará a MySQL, estructurará las tablas, e insertará los registros base automáticamente.

---

## 🧪 Pruebas del Sistema

Una vez que la consola indique que Tomcat ha iniciado, acceda a la plataforma desde su navegador en:  
🔗 **http://localhost:8080**

**Credenciales generadas automáticamente para pruebas:**
| Rol | Correo Electrónico | Contraseña |
| :--- | :--- | :--- |
| **Administrador** | `admin@ugd.edu.ar` | `admin123` |
| **Alumno** | `alumno@ugd.edu.ar` | `1234` |

---

## 📡 API de Hardware (En desarrollo)
El sistema incluye un endpoint REST diseñado para recibir las peticiones del microcontrolador (ESP-01):
* **GET** `/api/hardware/verificar?casilleroId={id}&pin={pin}`
* Retorna un `JSON` indicando si el hardware debe accionar el servomotor para abrir la puerta.
