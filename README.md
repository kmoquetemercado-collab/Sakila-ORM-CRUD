# Sakila ORM/CRUD Manager

## Descripción del Proyecto

Sistema de gestión de base de datos **Sakila** utilizando patrones **ORM/CRUD** en Java. Implementa una arquitectura escalable con JDBC para la gestión de datos de una base de datos de alquiler de películas.

### Características Principales

- ✅ **ORM/CRUD Completo**: Interfaz estándar IDataPost con operaciones POST, GET, PUT, DELETE
- ✅ **DataContext Abstracto**: Padre híbrido con métodos finales no sobrescribibles
- ✅ **Entidades Jerárquicas**: Todas heredan de la clase Entity abstracta
- ✅ **Managers/Controladores**: Gestión de ArrayList para cada tabla del modelo
- ✅ **JDBC MYSQL**: Conexión optimizada a la base de datos Sakila
- ✅ **Interfaz Console**: Navegación por menú en consola
- ✅ **Reportes**: Exportación a CSV y JSON con estadísticas
- ✅ **Validación**: Expresiones regulares para fechas, SSN, teléfono, etc.

## Estructura del Proyecto

```
com.sakila/
├── data/
│   ├── IDataPost.java          # Interface CRUD estándar
│   ├── DataContext.java        # Clase abstracta padre
│   └── DatabaseConnection.java # Gestión JDBC
├── models/
│   ├── Entity.java             # Clase base para entidades
│   ├── Actor.java
│   ├── Film.java
│   ├── Inventory.java
│   ├── Rental.java
│   ├── Payment.java
│   └── ... (otras entidades)
├── controllers/
│   ├── ActorManager.java
│   ├── FilmManager.java
│   ├── RentalManager.java
│   └── ... (otros managers)
├── views/
│   └── ConsoleUI.java          # Interface de consola
├── reports/
│   ├── ReportGenerator.java
│   └── ExportManager.java
└── utils/
    └── RegexValidator.java     # Validaciones
```

## Requisitos

- Java 11+
- MySQL 5.7+ con Sakila Database
- Maven 3.6+

## Instalación

### 1. Descargar e instalar Sakila Database

```bash
# Descargar desde: https://dev.mysql.com/doc/sakila/en/sakila-installation.html
mysql -u root -p < sakila-schema.sql
mysql -u root -p < sakila-data.sql
```

### 2. Configurar conexión a BD

Editar `src/main/java/com/sakila/data/DatabaseConnection.java`:

```java
private static final String DB_HOST = "localhost";
private static final int DB_PORT = 3306;
private static final String DB_NAME = "sakila";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "";
```

### 3. Compilar y ejecutar

```bash
# Compilar
mvn clean compile

# Ejecutar
mvn exec:java -Dexec.mainClass="com.sakila.Main"

# Generar JAR
mvn clean package
java -jar target/sakila-orm-crud-jar-with-dependencies.jar
```

## Operaciones CRUD

### POST (Crear)
```java
Actor actor = new Actor("John", "Doe");
actorManager.post(actor);
```

### GET (Leer)
```java
Actor actor = actorManager.getById(1);
ArrayList<Actor> all = actorManager.getAll();
ArrayList<Actor> results = actorManager.getByField("lastName", "Doe");
```

### PUT (Actualizar)
```java
actor.setFirstName("Jane");
actorManager.put(actor);
```

### DELETE (Soft Delete)
```java
actorManager.delete(1); // Marca como inactivo
```

## Validaciones Soportadas

- ✅ Emails
- ✅ Números telefónicos
- ✅ Social Security Numbers (SSN)
- ✅ Fechas (YYYY-MM-DD)
- ✅ Códigos postales
- ✅ Números decimales

## Reportes Generados

- Listado completo de tablas (CSV/JSON)
- Estadísticas por tabla
- Películas en inventario
- Actores y películas
- Rentas por tienda/ciudad/país/cliente
- Cobranzas por tienda/ciudad/país/cliente
- Aging de cuentas por cobrar

## Notas Técnicas

- **Patrón de Diseño**: MVC + DAO
- **Gestión de Transacciones**: JDBC Connection Management
- **Soft Delete**: Registros marcados como inactivos en lugar de eliminados
- **Composición**: Agregación de objetos para relaciones foreign key
- **Genéricos**: Uso de <?> para métodos genéricos
- **Colecciones**: HashMap, ArrayList para búsquedas y estadísticas

## Documentación

Todos los archivos incluyen:
- Encabezado de derechos de autor
- Documentación JavaDoc completa en cada clase y método
- Comentarios explicativos en secciones complejas

## Autor

**kmoquetemercado-collab**

Proyecto Final - Java Programming (JP INF514)

Fecha: Mayo 2026

## Licencia

Copyright (c) 2026 Sakila ORM/CRUD Manager. All rights reserved.

---

**Valor**: 20 puntos (Obligatorio para aprobar)

**Fecha de Entrega**: 15 de mayo de 2026
