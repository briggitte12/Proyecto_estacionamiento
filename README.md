# 🚗 Sistema Web API Parking Developer (REST)

Este repositorio contiene el módulo **REST API** del sistema **Parking Developer**, encargado de gestionar las operaciones principales del estacionamiento mediante servicios web.  
La API permite interactuar con la base de datos para registrar, actualizar, consultar y eliminar información sobre vehículos, conductores, cobros, ingresos, tarifas y usuarios.

---

## 📌 Descripción

El módulo REST fue desarrollado en **Java** usando **JAX-RS** e implementado sobre **WildFly**, conectado a una base de datos **MariaDB**.  
El sistema fue desplegado en **Oracle Cloud**, utilizando máquinas virtuales con sistema operativo **Linux**, lo que permite acceder a los endpoints desde cualquier cliente HTTP.  
Algunos endpoints (como los relacionados con usuarios) solo se pueden ejecutar desde la máquina virtual por temas de seguridad, y no están expuestos directamente en la interfaz web, a excepción de los métodos `GET`, que retornan información en formato JSON.

---

## 🛠️ Tecnologías utilizadas

- **Java JDK 17**
- **JAX-RS** (para la implementación de servicios REST)
- **WildFly** (Servidor de aplicaciones)
- **MariaDB** (Base de datos)
- **Maven** (Gestión de dependencias)
- **Oracle Cloud** (Despliegue)

---

## 📂 Estructura del proyecto

/scratches # Endpoints REST (VehiculoResource, CobroResource, etc.)
/src
├── main
│ ├── java/pe/edu/pe/parking
│ │ ├── business # Acceso a datos y persistencia
│ │ ├── controller # Servlets para el flujo de datos/control (ObtenerVehiculoServlet, RegistrarCobroServlet, etc.)
│ │ ├── model # Separa claramente la lógica del negocio (Cobro, Conductor, etc.)
│ │ ├── rest # Endpoints REST (VehiculoResource, CobroResource, etc.)
│ │ ├── service # Lógica de negocio asociada a la API
│ │ └── util # Herramientas
│ │
│ ├── webapp # Carpeta principal de la vista (JSF/JSP)
│ │ ├── resources # Recursos estáticos (CSS, JS, imágenes)
│ │ ├── templates # Plantillas comunes (header, footer, layout.xhtml)
│ │ ├── pages # Páginas JSF/JSP (Dash.xhtml, login.xhtml, etc.)
│ │ └── WEB-INF # Configuración interna (web.xml, faces-config.xml)
│ │
│ └── resources
│ ├── META-INF # Configuración de persistencia (persistence.xml)
│ └── log4j.properties # Configuración de logging

---

## 🔗 Endpoints principales

### **Cobro**
- `GET /v1/cobros` → Lista todos los cobros.
- `GET /v1/cobros/ganancia` → Obtiene el total de ganancias.
- `POST /v1/cobros` → Registra un nuevo cobro.
- `PUT /v1/cobros` → Actualiza un cobro existente.
- `DELETE /v1/cobros/{id}` → Elimina un cobro por ID.

### **Conductor**
- `GET /v1/conductores` → Lista todos los conductores.
- `GET /v1/conductores/{dni}` → Obtiene un conductor por su DNI.
- `POST /v1/conductores` → Registra un nuevo conductor.
- `PUT /v1/conductores` → Actualiza un conductor.
- `DELETE /v1/conductores` → Elimina un conductor por DNI.

### **Ingreso de vehículos**
- `GET /v1/ingresos` → Lista todos los ingresos de vehículos.
- `POST /v1/ingresos` → Registra el ingreso de un vehículo.
- `PUT /v1/ingresos/salida` → Registra la salida de un vehículo por ID de registro.
- `PUT /v1/ingresos` → Actualiza la fecha de salida de un vehículo.
- `DELETE /v1/ingresos/{id}` → Elimina un ingreso de vehículo.

### **Tarifa**
- `GET /v1/tarifas` → Lista todas las tarifas.
- `GET /v1/tarifas/{id}` → Obtiene el precio de la tarifa por ID.
- `POST /v1/tarifas` → Registra una nueva tarifa.
- `PUT /v1/tarifas` → Actualiza el precio de una tarifa.
- `DELETE /v1/tarifas/{tipoVehiculo}` → Elimina una tarifa por tipo de vehículo.

### **Usuario** *(acceso restringido)*
- `GET /v2/usuarios/{nombre}` → Obtiene un usuario por nombre.
- `GET /v2/usuarios/actividad` → Verifica si un usuario está autorizado.
- `POST /v2/usuarios/json` → Registra un usuario desde un archivo JSON.
- `POST /v2/usuarios/subido` → Sube un archivo previamente cargado en el servidor.
- `GET /v2/usuarios/descargado` → Descarga un archivo desde el servidor.

### **Vehículo**
- `GET /v1/vehiculos` → Lista todos los vehículos.
- `GET /v1/vehiculos/{placa}` → Obtiene un vehículo por número de placa.
- `POST /v1/vehiculos` → Registra un nuevo vehículo.
- `PUT /v1/vehiculos` → Actualiza la información de un vehículo.
- `DELETE /v1/vehiculos/eliminado/{placa}` → Elimina un vehículo.

---

## 📐 Principios aplicados

- **RESTful**: Uso de HTTP y sus métodos (GET, POST, PUT, DELETE) de forma semántica.  
- **Separación de capas**: Controladores REST, servicios y DAOs.  
- **DAO Pattern**: Aislamiento del acceso a datos para mantener bajo acoplamiento.  
- **Validaciones**: Tanto a nivel de API como en la base de datos para garantizar integridad.

---

## ⚡ Ejemplo de consumo

**Solicitud:**
```
http
POST /api/registros/ingreso
Content-Type: application/json

{
  "placa": "ABC-123",
  "id_tarifa": 1,
  "id_usuario": 5,
  "id_estacionamiento": 2
}
```  

**Respuesta:**
```  

{
  "mensaje": "Ingreso registrado correctamente",
  "id_registro": 101
}
```  

---

## 🚀 Despliegue
1. Empaquetar con Maven:
```
mvn clean package

```  
2. Desplegar el .war en WildFly (puerto 8080).

3. Configurar conexión a MariaDB en el persistence.xml.

4. Acceder a los endpoints en:
```  
http://<tu-servidor>/api
```
**Nota:** El sistema estuvo previamente desplegado en una máquina virtual Linux en Oracle Cloud. Algunos endpoints eran accesibles públicamente (GET) y otros solo desde el servidor por razones de seguridad.

---

## ⚙️ Despliegue en Oracle Cloud

- **Infraestructura:** Máquinas virtuales con Linux.
- **Servidor de Aplicaciones:** WildFly (puerto 8080).
- **Base de Datos:** MariaDB (puerto 3306).
- **Acceso a la API:** Se realiza vía HTTP/HTTPS.  
  - Métodos `GET`: accesibles públicamente, devuelven JSON.  
  - Métodos `POST`, `PUT`, `DELETE`: requieren ejecución desde la máquina virtual por políticas de seguridad.

---

## ✍️ Autora
**Briggitte Martinez Vidaurre**

---

## 📄 Licencia

Proyecto desarrollado con fines académicos para el curso **Desarrollo Web Integrado**.

