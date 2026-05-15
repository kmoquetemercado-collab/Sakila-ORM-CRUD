# DOCUMENTACIÓN COMPLETA DEL PROYECTO
# Sakila ORM/CRUD Manager

---

## Tabla de Contenidos
1. [Información del Proyecto](#información-del-proyecto)
2. [Descripción General](#descripción-general)
3. [Tecnologías Utilizadas](#tecnologías-utilizadas)
4. [Estructura del Proyecto](#estructura-del-proyecto)
5. [Instalación](#instalación)
6. [Configuración](#configuración)
7. [Uso de la Aplicación](#uso-de-la-aplicación)
8. [Operaciones CRUD](#operaciones-crud)
9. [Ejemplos de Uso](#ejemplos-de-uso)
10. [Validaciones](#validaciones)
11. [Reportes y Estadísticas](#reportes-y-estadísticas)
12. [Arquitectura y Patrones de Diseño](#arquitectura-y-patrones-de-diseño)
13. [Características Avanzadas](#características-avanzadas)
14. [Solución de Problemas](#solución-de-problemas)
15. [Contribuidores](#contribuidores)

---

## Información del Proyecto

| Campo | Valor |
|-------|-------|
| **Nombre** | Sakila ORM/CRUD Manager |
| **Versión** | 1.0.0 |
| **Autor** | Kimberly Moquete Mercado |
| **Matrícula** | 100573552 |
| **Institución** | Universidad Autónoma de Santo Domingo (UASD) |
| **Asignatura** | Java Programming (JP INF514) |
| **Tipo de Proyecto** | Proyecto Final (Valor: 20 puntos) |
| **Fecha de Creación** | Mayo 2026 |
| **Fecha de Entrega** | 15 de mayo de 2026 |
| **Repositorio** | https://github.com/kmoquetemercado-collab/Sakila-ORM-CRUD |
| **Licencia** | Copyright © 2026 - Todos los derechos reservados |

---

## Descripción General

### ¿Qué es Sakila ORM/CRUD Manager?

**Sakila ORM/CRUD Manager** es una aplicación de gestión completa desarrollada en Java que implementa un sistema robusto de operaciones **CRUD (Create, Read, Update, Delete)** sobre la base de datos **Sakila** de MySQL. 

#### Propósito Principal
El proyecto demuestra la implementación de patrones de diseño avanzados en Java, incluyendo:
- Patrón **ORM (Object-Relational Mapping)**
- Patrón **DAO (Data Access Object)**
- Patrón **MVC (Model-View-Controller)**
- Abstracción mediante interfaces y clases abstractas
- Gestión de bases de datos con JDBC

#### Base de Datos
La aplicación trabaja con la base de datos **Sakila**, un conjunto de datos de ejemplo oficial de MySQL que simula un sistema de alquiler de películas con entidades como:
- Actores
- Películas
- Inventario
- Alquileres
- Pagos
- Clientes
- Tiendas
- Ubicaciones (Países, Ciudades, Direcciones)

### Características Principales

✅ **ORM/CRUD Completo**
- Interface estándar `IDataPost<T>` con operaciones POST, GET, PUT, DELETE
- Implementación consistente en todas las entidades

✅ **Arquitectura Flexible**
- Clase abstracta `DataContext<T>` como padre híbrido
- Métodos finales que no pueden ser sobrescritos
- Patrón de herencia multinivel

✅ **Entidades Jerárquicas**
- Todas las entidades heredan de la clase abstracta `Entity`
- Composición para relaciones de foreign key
- Propiedades comunes en la clase base

✅ **Gestión Completa de Datos**
- Managers/Controladores para cada tabla
- Búsqueda avanzada por múltiples campos
- Soft delete (marcar como inactivo en lugar de eliminar)

✅ **Conexión Optimizada**
- JDBC MySQL con pool de conexiones
- Manejo automático de recursos
- Cierre seguro de conexiones

✅ **Interfaz de Consola**
- Menú navegable e intuitivo
- Validación de entradas
- Mensajes de retroalimentación

✅ **Reportes y Estadísticas**
- Exportación a CSV
- Exportación a JSON
- Análisis por tienda, ciudad, país, cliente
- Métricas financieras

✅ **Validaciones Robustas**
- Expresiones regulares para múltiples tipos de datos
- Validación de emails, teléfonos, SSN, fechas
- Prevención de datos inválidos

---

## Tecnologías Utilizadas

### Lenguaje y Plataforma
- **Java 11+** - Lenguaje de programación
- **JDK 11** - Java Development Kit

### Herramientas de Desarrollo
- **Apache Maven 3.6+** - Gestor de dependencias y construcción
- **Git** - Control de versiones
- **GitHub** - Repositorio remoto

### Base de Datos
- **MySQL 5.7+** - Sistema gestor de base de datos relacional
- **Sakila Database** - Conjunto de datos de ejemplo

### Librerías y Dependencias

| Librería | Versión | Propósito |
|----------|---------|----------|
| **MySQL JDBC Driver** | 8.0.33 | Conexión a MySQL |
| **GSON** | 2.10.1 | Serialización/Deserialización JSON |
| **Apache Commons CSV** | 1.10.0 | Exportación a CSV |
| **SLF4J API** | 2.0.5 | Logging de aplicación |
| **SLF4J Simple** | 2.0.5 | Implementación de logging |
| **JUnit** | 4.13.2 | Framework de pruebas unitarias |

### Plugins Maven
- **Maven Compiler Plugin 3.11.0** - Compilación de código fuente
- **Maven JAR Plugin 3.3.0** - Generación de archivo JAR
- **Maven Assembly Plugin 3.6.0** - Creación de JAR con todas las dependencias

---

## Estructura del Proyecto

### Árbol de Directorios

```
Sakila-ORM-CRUD/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── sakila/
│                   ├── Main.java                      # Punto de entrada
│                   ├── data/
│                   │   ├── IDataPost.java             # Interface CRUD genérica
│                   │   ├── DataContext.java           # Clase abstracta base
│                   │   └── DatabaseConnection.java    # Gestor de conexión JDBC
│                   ├── models/
│                   │   ├── Entity.java                # Clase base abstracta
│                   │   ├── Actor.java
│                   │   ├── Film.java
│                   │   ├── Language.java
│                   │   ├── Customer.java
│                   │   ├── Rental.java
│                   │   ├── Payment.java
│                   │   ├── Inventory.java
│                   │   ├── Store.java
│                   │   ├── Staff.java
│                   │   ├── Address.java
│                   │   ├── City.java
│                   │   └── Country.java
│                   ├── controllers/
│                   │   ├── ActorManager.java
│                   │   ├── FilmManager.java
│                   │   ├── LanguageManager.java
│                   │   ├── CustomerManager.java
│                   │   ├── PaymentManager.java
│                   │   ├── RentalManager.java
│                   │   ├── InventoryManager.java
│                   │   ├── StoreManager.java
│                   │   ├── StaffManager.java
│                   │   ├── AddressManager.java
│                   │   ├── CityManager.java
│                   │   └── CountryManager.java
│                   ├── views/
│                   │   └── ConsoleUI.java             # Interfaz de consola
│                   ├── reports/
│                   │   └── ReportGenerator.java       # Generador de reportes
│                   └── utils/
│                       └── RegexValidator.java        # Validaciones
├── docs/
│   └── DOCUMENTACION_COMPLETA.md                      # Este documento
├── pom.xml                                             # Configuración Maven
├── README.md                                           # Descripción rápida
├── INSTALL.md                                          # Guía de instalación
└── .gitignore                                          # Archivos ignorados por Git

```

### Descripción de Paquetes

#### `com.sakila.data`
Contiene la capa de acceso a datos:
- **IDataPost.java**: Interface genérica que define las operaciones CRUD
- **DataContext.java**: Clase abstracta base que implementa funcionalidad común
- **DatabaseConnection.java**: Gestor de conexiones JDBC a MySQL

#### `com.sakila.models`
Define las entidades del dominio:
- **Entity.java**: Clase abstracta base para todas las entidades
- Clases específicas: Actor, Film, Customer, Rental, Payment, etc.

#### `com.sakila.controllers`
Implementa los gestores de datos (Data Access Objects):
- **ActorManager.java**, **FilmManager.java**, etc.
- Cada manager extiende `DataContext<T>` e implementa `IDataPost<T>`

#### `com.sakila.views`
Interfaz de usuario:
- **ConsoleUI.java**: Menú interactivo en consola

#### `com.sakila.reports`
Generación de reportes y estadísticas:
- **ReportGenerator.java**: Exportación a CSV y JSON

#### `com.sakila.utils`
Utilidades de la aplicación:
- **RegexValidator.java**: Validaciones con expresiones regulares

---

## Instalación

### Requisitos Previos

Antes de instalar el proyecto, asegúrate de tener instalado:

1. **Java Development Kit (JDK) 11 o superior**
   ```bash
   java -version
   # Debería mostrar: java version "11" o superior
   ```

2. **Apache Maven 3.6 o superior**
   ```bash
   mvn -version
   # Debería mostrar: Maven 3.6.0 o superior
   ```

3. **MySQL Server 5.7 o superior**
   ```bash
   mysql --version
   # Debería mostrar: mysql Ver X.X.XX
   ```

### Paso 1: Clonar el Repositorio

```bash
# Clonar el repositorio
git clone https://github.com/kmoquetemercado-collab/Sakila-ORM-CRUD.git

# Entrar al directorio
cd Sakila-ORM-CRUD
```

### Paso 2: Instalar la Base de Datos Sakila

#### Opción A: Descargar desde MySQL oficial

1. Descargar los scripts desde:
   ```
   https://dev.mysql.com/doc/sakila/en/sakila-installation.html
   ```

2. Ejecutar los scripts en MySQL:
   ```bash
   # Schema (estructura)
   mysql -u root -p < sakila-schema.sql
   
   # Data (datos de ejemplo)
   mysql -u root -p < sakila-data.sql
   ```

#### Opción B: Crear la base de datos manualmente

```bash
# Abrir MySQL
mysql -u root -p

# En la consola de MySQL
CREATE DATABASE sakila;
USE sakila;

# Luego importar los datos usando los scripts descargados
```

### Paso 3: Compilar el Proyecto

```bash
# Limpiar compilaciones anteriores
mvn clean

# Compilar el proyecto
mvn compile

# O en un comando
mvn clean compile
```

### Paso 4: Generar el JAR Ejecutable

```bash
# Compilar y empaquetar
mvn clean package

# Esto generará dos JAR:
# - target/sakila-orm-crud.jar (JAR simple)
# - target/sakila-orm-crud-jar-with-dependencies.jar (JAR con todas las librerías)
```

---

## Configuración

### Configurar Credenciales de Base de Datos

El archivo principal de configuración se encuentra en:
```
src/main/java/com/sakila/data/DatabaseConnection.java
```

Editar las siguientes líneas:

```java
private static final String DB_HOST = "localhost";      // Host de MySQL
private static final int DB_PORT = 3306;               // Puerto MySQL
private static final String DB_NAME = "sakila";        // Nombre BD
private static final String DB_USER = "root";          // Usuario MySQL
private static final String DB_PASSWORD = "";          // Contraseña
```

### Ejemplo de Configuración

```java
// Para un servidor local con contraseña
private static final String DB_HOST = "localhost";
private static final int DB_PORT = 3306;
private static final String DB_NAME = "sakila";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "myPassword123";

// Para un servidor remoto
private static final String DB_HOST = "192.168.1.100";
private static final int DB_PORT = 3306;
private static final String DB_NAME = "sakila";
private static final String DB_USER = "remoteUser";
private static final String DB_PASSWORD = "remotePass";
```

---

## Uso de la Aplicación

### Ejecutar la Aplicación

#### Opción 1: Con Java directamente

```bash
java -cp target/sakila-orm-crud-jar-with-dependencies.jar com.sakila.Main
```

#### Opción 2: Con Maven

```bash
mvn exec:java -Dexec.mainClass="com.sakila.Main"
```

#### Opción 3: Usando el JAR generado

```bash
cd target
java -jar sakila-orm-crud-jar-with-dependencies.jar
```

### Interfaz de Menú Principal

```
==================================================================
        SAKILA DATABASE MANAGEMENT SYSTEM
==================================================================

MAIN MENU
------------------------------------------------------------------
1. Manage Actors             - Crear, leer, actualizar actores
2. Manage Films              - Gestionar películas
3. Manage Inventory          - Administrar inventario
4. Manage Rentals            - Gestionar alquileres
5. Manage Payments           - Administrar pagos
6. Manage Customers          - Gestionar clientes
7. Manage Stores             - Administrar tiendas
8. Manage Staff              - Gestionar personal
9. Manage Addresses          - Administrar direcciones
10. Manage Cities            - Gestionar ciudades
11. Manage Countries         - Gestionar países
12. Manage Languages         - Gestionar idiomas
13. View Reports & Statistics - Reportes y análisis
0. Exit                      - Salir de la aplicación

==================================================================
Enter your choice: 
```

### Flujo de Operaciones CRUD

Para cualquier entidad, el flujo es similar:

```
Seleccionar Entidad (ej: 1 = Actors)
    ↓
Menú de Operaciones:
1. Create (POST)
2. Read (GET)
3. Update (PUT)
4. Delete (DELETE)
0. Back to Main Menu
    ↓
Seleccionar Operación
    ↓
Completar Campos Requeridos
    ↓
Validación de Datos
    ↓
Ejecutar en Base de Datos
    ↓
Mostrar Resultado
```

---

## Operaciones CRUD

### 1. CREATE (POST) - Crear Registros

#### Sintaxis
```java
Entity nuevaEntidad = new Entity(...parámetros...);
manager.post(nuevaEntidad);
```

#### Ejemplo: Crear un Actor

```
Menú: Manage Actors → 1 (Create)

Enter actor's first name: Johnny
Enter actor's last name: Depp
Enter last update date (YYYY-MM-DD): 2026-05-15

✓ Actor created successfully with ID: 201
```

#### Código Java
```java
Actor actor = new Actor("Johnny", "Depp");
actor.setLastUpdate(LocalDateTime.now());
actorManager.post(actor);
```

### 2. READ (GET) - Leer Registros

#### Obtener por ID
```
Menú: Manage Actors → 2 (Read) → 1 (Get by ID)

Enter actor ID: 1

Results:
ID: 1 | First Name: Penelope | Last Name: Guiness | Last Update: 2006-02-15
```

#### Código Java
```java
Actor actor = actorManager.getById(1);
System.out.println(actor);
```

#### Obtener Todos los Registros
```
Menú: Manage Actors → 2 (Read) → 2 (Get All)

Displaying all 200 actors...
ID: 1 | First Name: Penelope | Last Name: Guiness
ID: 2 | First Name: Nick | Last Name: Wahlberg
ID: 3 | First Name: Ed | Last Name: Chase
...
```

#### Código Java
```java
ArrayList<Actor> actores = actorManager.getAll();
for (Actor a : actores) {
    System.out.println(a);
}
```

#### Buscar por Campo
```
Menú: Manage Actors → 2 (Read) → 3 (Search by Field)

Enter field name: last_name
Enter search value: Wahlberg

Results: 1 match found
ID: 2 | First Name: Nick | Last Name: Wahlberg
```

#### Código Java
```java
ArrayList<Actor> results = actorManager.getByField("last_name", "Wahlberg");
```

### 3. UPDATE (PUT) - Actualizar Registros

#### Sintaxis
```java
Entity entidad = manager.getById(id);
entidad.setPropiedad(nuevoValor);
manager.put(entidad);
```

#### Ejemplo: Actualizar Actor
```
Menú: Manage Actors → 3 (Update)

Enter actor ID to update: 1
Current data: Penelope Guiness

Enter new first name (current: Penelope): Paula
Enter new last name (current: Guiness): Guiness

✓ Actor updated successfully
```

#### Código Java
```java
Actor actor = actorManager.getById(1);
actor.setFirstName("Paula");
actorManager.put(actor);
```

### 4. DELETE (Soft Delete) - Eliminar Registros

#### Nota: Soft Delete
Los registros no se eliminan completamente, solo se marcan como inactivos.

#### Sintaxis
```java
manager.delete(id);
```

#### Ejemplo: Eliminar Actor
```
Menú: Manage Actors → 4 (Delete)

Enter actor ID to delete: 201

✓ Actor deleted successfully (marked as inactive)
```

#### Código Java
```java
actorManager.delete(201);
```

---

## Ejemplos de Uso

### Ejemplo 1: Crear y Listar Actores

```java
// 1. Crear actores
Actor actor1 = new Actor("Robert", "Pattinson");
Actor actor2 = new Actor("Kristen", "Stewart");

ActorManager manager = new ActorManager();
manager.post(actor1);
manager.post(actor2);

// 2. Listar todos
ArrayList<Actor> actores = manager.getAll();
for (Actor a : actores) {
    System.out.println(a.getFirstName() + " " + a.getLastName());
}
```

### Ejemplo 2: Buscar Películas por Idioma

```java
FilmManager filmManager = new FilmManager();

// Buscar películas en inglés
ArrayList<Film> englishFilms = filmManager.getByField("language_id", "1");

System.out.println("Películas en inglés: " + englishFilms.size());
for (Film film : englishFilms) {
    System.out.println(film.getTitle());
}
```

### Ejemplo 3: Actualizar Información de Cliente

```java
CustomerManager customerManager = new CustomerManager();

// Obtener cliente
Customer customer = customerManager.getById(1);

// Actualizar
customer.setFirstName("Juan");
customer.setLastName("Pérez");
customer.setEmail("juan.perez@example.com");

// Guardar cambios
customerManager.put(customer);

System.out.println("Cliente actualizado exitosamente");
```

### Ejemplo 4: Generar Reportes

```java
ReportGenerator reporter = new ReportGenerator();

// Exportar actores a CSV
reporter.exportActorsToCSV("actores_report.csv");

// Exportar películas a JSON
reporter.exportFilmsToJSON("peliculas_report.json");

// Exportar estadísticas
reporter.exportStatisticsToJSON("estadisticas.json");
```

### Ejemplo 5: Validar Datos de Entrada

```java
RegexValidator validator = new RegexValidator();

// Validar email
if (validator.isValidEmail("usuario@ejemplo.com")) {
    System.out.println("Email válido");
}

// Validar teléfono
if (validator.isValidPhone("+1-234-567-8900")) {
    System.out.println("Teléfono válido");
}

// Validar fecha
if (validator.isValidDate("2026-05-15")) {
    System.out.println("Fecha válida");
}

// Validar SSN
if (validator.isValidSSN("123-45-6789")) {
    System.out.println("SSN válido");
}
```

---

## Validaciones

### Expresiones Regulares Implementadas

| Tipo | Patrón | Ejemplo | Válido |
|------|--------|---------|--------|
| **Email** | RFC 5322 | usuario@ejemplo.com | ✓ |
| **Teléfono** | Múltiples formatos | +1-234-567-8900 | ✓ |
| **SSN** | XXX-XX-XXXX | 123-45-6789 | ✓ |
| **Fecha** | YYYY-MM-DD | 2026-05-15 | ✓ |
| **Código Postal** | Números y formato | 12345 | ✓ |
| **Número Positivo** | [0-9]+ | 12345 | ✓ |
| **Número Decimal** | X.XX | 123.45 | ✓ |

### Método de Validación

```java
RegexValidator validator = new RegexValidator();

// Ejemplo
String email = "usuario@ejemplo.com";
if (validator.isValidEmail(email)) {
    System.out.println("Email válido");
} else {
    System.out.println("Email inválido");
}
```

### Validaciones por Entidad

- **Actor**: Nombres (alfabético)
- **Film**: Título, Descripción, Duración
- **Customer**: Email, Teléfono (opcional)
- **Payment**: Monto (número decimal positivo)
- **Address**: Código Postal, Dirección
- **Rental**: Fecha de alquiler (formato YYYY-MM-DD)

---

## Reportes y Estadísticas

### Tipos de Reportes Disponibles

#### 1. Reportes por Entidad
```
- Listado completo de actores
- Listado completo de películas
- Listado completo de clientes
- Listado completo de pagos
- Listado completo de alquileres
- Listado completo de inventario
```

#### 2. Reportes de Estadísticas
```
- Total de registros por tabla
- Películas por idioma
- Películas por clasificación (G, PG, R, etc.)
- Clientes activos vs inactivos
- Pagos totales por periodo
```

#### 3. Reportes de Análisis
```
- Ingresos por tienda
- Ingresos por ciudad
- Ingresos por país
- Películas más alquiladas
- Clientes con más alquileres
- Películas con mayor inventario
```

### Formatos de Exportación

#### CSV (Comma-Separated Values)
```csv
ID,First_Name,Last_Name,Last_Update
1,Penelope,Guiness,2006-02-15
2,Nick,Wahlberg,2006-02-15
3,Ed,Chase,2006-02-15
```

#### JSON (JavaScript Object Notation)
```json
{
  "actors": [
    {
      "id": 1,
      "firstName": "Penelope",
      "lastName": "Guiness",
      "lastUpdate": "2006-02-15"
    },
    {
      "id": 2,
      "firstName": "Nick",
      "lastName": "Wahlberg",
      "lastUpdate": "2006-02-15"
    }
  ],
  "totalRecords": 200
}
```

### Cómo Generar Reportes

#### Desde el Menú de Consola
```
Menú Principal → 13 (View Reports & Statistics)
    → Seleccionar tipo de reporte
    → Seleccionar formato (CSV/JSON)
    → Ingresar nombre de archivo
```

#### Desde Código Java
```java
ReportGenerator report = new ReportGenerator();

// Exportar a CSV
report.exportActorsToCSV("actores_report.csv");
report.exportFilmsToCSV("peliculas_report.csv");
report.exportCustomersToCSV("clientes_report.csv");

// Exportar a JSON
report.exportActorsToJSON("actores_report.json");
report.exportFilmsToJSON("peliculas_report.json");
report.exportStatisticsToJSON("estadisticas.json");
```

### Ubicación de Reportes

Los reportes se guardan en:
```
proyecto-root/
└── reports/
    ├── actores_report.csv
    ├── peliculas_report.json
    └── estadisticas.json
```

---

## Arquitectura y Patrones de Diseño

### Patrón MVC (Model-View-Controller)

```
┌─────────────────────────────────────────────┐
│              ARQUITECTURA MVC                │
├─────────────────────────────────────────────┤
│                                             │
│  MODELS (com.sakila.models)                │
│  ├── Entity (clase abstracta base)         │
│  ├── Actor, Film, Customer, etc.           │
│  └── [Objetos de dominio]                  │
│                                             │
│  VIEWS (com.sakila.views)                  │
│  ├── ConsoleUI                             │
│  └── [Presentación al usuario]             │
│                                             │
│  CONTROLLERS (com.sakila.controllers)      │
│  ├── ActorManager                          │
│  ├── FilmManager                           │
│  └── [Lógica de negocio]                   │
│                                             │
│  DATA (com.sakila.data)                    │
│  ├── DatabaseConnection                   │
│  ├── DataContext                           │
│  └── [Acceso a datos]                      │
│                                             │
└─────────────────────────────────────────────┘
```

### Patrón ORM (Object-Relational Mapping)

```
┌──────────────────────────────────────────┐
│     TABLA EN BASE DE DATOS (SQL)         │
│                                          │
│  ID | first_name | last_name | ...      │
│  1  | Penelope   | Guiness   | ...      │
│  2  | Nick       | Wahlberg  | ...      │
└──────────────────────────────────────────┘
              ↕ (Mapeo ORM)
┌──────────────────────────────────────────┐
│      OBJETO EN JAVA (POO)                │
│                                          │
│  Actor {                                 │
│    id: 1                                 │
│    firstName: "Penelope"                 │
│    lastName: "Guiness"                   │
│  }                                       │
└──────────────────────────────────────────┘
```

### Patrón DAO (Data Access Object)

```
┌─────────────────┐
│   Controller    │  ActorManager
├─────────────────┤
│                 │  - post()
│  - Lógica       │  - getById()
│  - Validación   │  - getAll()
│                 │  - getByField()
│                 │  - put()
│                 │  - delete()
├─────────────────┤
│   DataContext   │  Abstracción común
├─────────────────┤
│  Data Access    │
│   - JDBC        │
│   - MySQL       │
└─────────────────┘
```

### Jerarquía de Clases

```java
// Interfaz CRUD genérica
interface IDataPost<T> {
    void post(T entity);
    T getById(int id);
    ArrayList<T> getAll();
    ArrayList<T> getByField(String field, String value);
    void put(T entity);
    void delete(int id);
}

// Clase abstracta base
abstract class DataContext<T> implements IDataPost<T> {
    // Implementación común de CRUD
    // Métodos finales que no pueden ser sobrescritos
}

// Clase modelo base
abstract class Entity {
    protected int id;
    protected LocalDateTime lastUpdate;
    // Propiedades comunes
}

// Implementación concreta
class ActorManager extends DataContext<Actor> {
    // Métodos específicos para Actor
}

class Actor extends Entity {
    private String firstName;
    private String lastName;
    // Propiedades de Actor
}
```

### Relaciones entre Entidades

#### Relación 1:N (Uno a Muchos)
```
Store (1) ──→ (N) Rental
         ↓
        Una tienda tiene muchos alquileres
```

#### Relación N:N (Muchos a Muchos)
```
Actor (N) ──→ (N) Film
              ↓
     Un actor aparece en muchas películas
     Una película tiene muchos actores
```

#### Composición (Foreign Key)
```java
public class Film extends Entity {
    private Language language;  // FK como objeto
    
    public Film(String title, Language lang) {
        this.title = title;
        this.language = lang;
    }
}
```

---

## Características Avanzadas

### 1. Soft Delete

En lugar de eliminar registros físicamente, se marcan como inactivos:

```java
// En la base de datos (columna active)
UPDATE actor SET active = 0 WHERE id = 1;

// En Java
actor.setActive(false);
actorManager.put(actor);
```

**Ventajas:**
- Preserva integridad referencial
- Permite recuperar datos accidentalmente eliminados
- Mantiene histórico de cambios

### 2. Búsqueda Avanzada

```java
// Búsqueda por campo específico
ArrayList<Film> results = filmManager.getByField("rating", "PG");

// Búsqueda múltiple (mediante filtros encadenados)
ArrayList<Customer> customers = customerManager.getByField("country_id", "1");
```

### 3. Validación de Datos

```java
RegexValidator validator = new RegexValidator();

// Múltiples tipos de validación
boolean emailValid = validator.isValidEmail(email);
boolean phoneValid = validator.isValidPhone(phone);
boolean dateValid = validator.isValidDate(date);
boolean ssnValid = validator.isValidSSN(ssn);
```

### 4. Exportación de Datos

```java
ReportGenerator report = new ReportGenerator();

// CSV - compatible con Excel
report.exportActorsToCSV("actores.csv");

// JSON - compatible con aplicaciones web
report.exportFilmsToJSON("peliculas.json");

// Estadísticas - análisis de datos
report.exportStatisticsToJSON("stats.json");
```

### 5. Manejo de Excepciones

```java
try {
    Actor actor = actorManager.getById(999);
    if (actor == null) {
        System.out.println("Actor no encontrado");
    }
} catch (SQLException e) {
    System.out.println("Error en base de datos: " + e.getMessage());
} catch (Exception e) {
    System.out.println("Error inesperado: " + e.getMessage());
}
```

### 6. Logging

```java
// Usando SLF4J
Logger logger = LoggerFactory.getLogger(ActorManager.class);

logger.info("Buscando actor con ID: 1");
logger.debug("Parámetros de búsqueda: {}", params);
logger.warn("Registro no encontrado");
logger.error("Error conectando a BD", exception);
```

---

## Solución de Problemas

### Error 1: "Failed to connect to database"

**Causa:** Problemas de conexión a MySQL

**Solución:**
```bash
# 1. Verificar que MySQL está corriendo
sudo systemctl status mysql

# 2. Verificar credenciales en DatabaseConnection.java
# 3. Verificar que la base de datos existe
mysql -u root -p
show databases;
use sakila;
show tables;

# 4. Recompilar
mvn clean compile
```

### Error 2: "MySQL JDBC Driver not found"

**Causa:** Falta descargar las dependencias

**Solución:**
```bash
# Descargar todas las dependencias
mvn dependency:resolve

# O recompilar
mvn clean install
```

### Error 3: "Table 'sakila' doesn't exist"

**Causa:** Base de datos Sakila no instalada

**Solución:**
```bash
# Descargar e instalar Sakila
# Desde: https://dev.mysql.com/doc/sakila/en/

mysql -u root -p < sakila-schema.sql
mysql -u root -p < sakila-data.sql

# Verificar instalación
mysql -u root -p
USE sakila;
SHOW TABLES;
```

### Error 4: "Access denied for user"

**Causa:** Usuario o contraseña incorrectos

**Solución:**
```bash
# Verificar usuario en MySQL
mysql -u root -p
SELECT User FROM mysql.user;

# Cambiar contraseña
ALTER USER 'root'@'localhost' IDENTIFIED BY 'nueva_contraseña';

# Actualizar en DatabaseConnection.java
private static final String DB_PASSWORD = "nueva_contraseña";
```

### Error 5: Aplicación no ejecuta - "Main class not found"

**Causa:** El JAR no se generó correctamente

**Solución:**
```bash
# Limpiar y recompilar
mvn clean
mvn package

# Verificar JAR se creó
ls target/*.jar

# Ejecutar con ruta completa
java -jar target/sakila-orm-crud-jar-with-dependencies.jar
```

### Error 6: "Port 3306 already in use"

**Causa:** MySQL ya está ejecutándose

**Solución:**
```bash
# Verificar procesos
lsof -i :3306

# O usar un puerto diferente
# Cambiar en DatabaseConnection.java
private static final int DB_PORT = 3307;
```

---

## Contribuidores

### Autor Principal

| Aspecto | Información |
|--------|------------|
| **Nombre** | Kimberly Moquete Mercado |
| **Matrícula** | 100573552 |
| **Usuario GitHub** | kmoquetemercado-collab |
| **Institución** | Universidad Autónoma de Santo Domingo (UASD) |
| **Asignatura** | Java Programming (JP INF514) |
| **Año Académico** | 2026 |
| **Rol** | Desarrollador Principal |

### Contribuciones

| Área | Descripción |
|------|------------|
| **Desarrollo** | Implementación completa del ORM/CRUD |
| **Base de Datos** | Integración con Sakila Database |
| **Testing** | Pruebas manuales de funcionalidad |
| **Documentación** | Redacción de guías y comentarios de código |
| **Reportes** | Generador de CSV y JSON |

### Reconocimientos

- **MySQL**: Por proporcionar la base de datos Sakila
- **Apache Maven**: Por herramientas de construcción
- **GitHub**: Por plataforma de versionamiento
- **UASD**: Por facilidades y educación

### Próximas Mejoras Planeadas

- [ ] Interfaz gráfica con Swing o JavaFX
- [ ] API REST con Spring Boot
- [ ] Autenticación de usuarios
- [ ] Paginación de resultados
- [ ] Filtros avanzados
- [ ] Caché de datos
- [ ] Transacciones múltiples
- [ ] Backup automático de BD
- [ ] Historial de cambios
- [ ] Reportes más complejos (gráficos)

---

## Licencia

```
Copyright © 2026 Sakila ORM/CRUD Manager
All rights reserved.

Proyecto Final - Universidad Autónoma de Santo Domingo (UASD)
Java Programming (JP INF514)

Este proyecto es propiedad intelectual de Kimberly Moquete Mercado
Matrícula: 100573552

Se permite el uso para propósitos educativos únicamente.
```

---

## Referencias y Recursos

### Documentación Oficial
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Oracle Java Documentation](https://docs.oracle.com/en/java/)
- [Apache Maven Official](https://maven.apache.org/)
- [Sakila Database](https://dev.mysql.com/doc/sakila/en/)

### Tutoriales Utilizados
- JDBC Database Connectivity
- ORM (Object-Relational Mapping) Patterns
- MVC Architecture
- Design Patterns in Java
- Regular Expressions in Java

### Herramientas Utilizadas
- IntelliJ IDEA / Visual Studio Code
- MySQL Workbench
- Git / GitHub
- Maven
- Postman (para testing de API)

---

## Información de Entrega

| Ítem | Detalle |
|------|---------|
| **Tipo de Proyecto** | Proyecto Final (Coursework) |
| **Valor Académico** | 20 puntos |
| **Requisitos** | Obligatorio para aprobar |
| **Fecha de Creación** | Mayo 2026 |
| **Fecha Máxima de Entrega** | 15 de mayo de 2026 |
| **Repositorio** | https://github.com/kmoquetemercado-collab/Sakila-ORM-CRUD |
| **Formato** | GitHub + Documentación + PDF |

---

**Documento generado: 15 de mayo de 2026**

**Versión: 1.0.0 (Final)**

**Estado: Completado y listo para entrega**

---

*Fin de la Documentación Completa*
