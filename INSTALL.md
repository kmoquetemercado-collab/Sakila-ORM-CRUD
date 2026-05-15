# Sakila ORM/CRUD - Project Documentation

## Instalación y Configuración

### 1. Requisitos Previos

- **Java**: JDK 11 o superior
- **MySQL**: Versión 5.7 o superior
- **Maven**: Versión 3.6 o superior
- **Sakila Database**: Descargada e instalada

### 2. Descargar Sakila Database

```bash
# Descargar desde MySQL oficial
https://dev.mysql.com/doc/sakila/en/sakila-installation.html

# Ejecutar scripts de instalación
mysql -u root -p < sakila-schema.sql
mysql -u root -p < sakila-data.sql
```

### 3. Configurar Credenciales de Base de Datos

Editar `src/main/java/com/sakila/data/DatabaseConnection.java`:

```java
private static final String DB_HOST = "localhost";
private static final int DB_PORT = 3306;
private static final String DB_NAME = "sakila";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "tu_password";
```

### 4. Compilar el Proyecto

```bash
# Limpiar y compilar
mvn clean compile

# Crear JAR ejecutable
mvn clean package
```

### 5. Ejecutar la Aplicación

```bash
# Desde el directorio del proyecto
java -cp target/sakila-orm-crud-jar-with-dependencies.jar com.sakila.Main

# O ejecutar con Maven
mvn exec:java -Dexec.mainClass="com.sakila.Main"
```

## Uso de la Aplicación

### Menú Principal

```
==================================================================
MAIN MENU - Sakila Database Management
==================================================================
1. Manage Actors
2. Manage Films
3. Manage Inventory
4. Manage Rentals
5. Manage Payments
6. Manage Customers
7. Manage Stores
8. Manage Countries/Cities
9. View Reports & Statistics
0. Exit
==================================================================
```

### Operaciones CRUD

Cada entidad soporta:

- **CREATE (POST)**: Insertar nuevos registros
- **READ (GET)**: Visualizar registros (con búsqueda)
- **UPDATE (PUT)**: Modificar registros existentes
- **DELETE**: Marcar como inactivo (soft delete)

### Ejemplo: Gestionar Actores

```
1. Select option: 1 (Manage Actors)
2. Select operation: 1 (Create actor)
3. Enter actor first name: John
4. Enter actor last name: Doe
✓ Actor created successfully: John Doe
```

## Estructura del Código

### Packages

```
com.sakila/
├── Main.java                    (Punto de entrada)
├── data/
│   ├── IDataPost.java          (Interface CRUD)
│   ├── DataContext.java        (Clase abstracta padre)
│   └── DatabaseConnection.java (Gestor JDBC)
├── models/
│   ├── Entity.java             (Clase base)
│   ├── Actor.java
│   ├── Film.java
│   ├── Customer.java
│   ├── Rental.java
│   ├── Payment.java
│   ├── Inventory.java
│   ├── Store.java
│   ├── Staff.java
│   ├── Address.java
│   ├── City.java
│   ├── Country.java
│   └── Language.java
├── controllers/
│   ├── ActorManager.java
│   ├── FilmManager.java
│   ├── CustomerManager.java
│   ├── PaymentManager.java
│   ├── RentalManager.java
│   ├── InventoryManager.java
│   ├── StoreManager.java
│   ├── StaffManager.java
│   ├── AddressManager.java
│   ├── CityManager.java
│   ├── CountryManager.java
│   └── LanguageManager.java
├── views/
│   └── ConsoleUI.java          (Interface de usuario)
├── reports/
│   └── ReportGenerator.java    (Generador de reportes)
└── utils/
    └── RegexValidator.java     (Validaciones)
```

## Características Principales

### 1. ORM/CRUD Completo

- Interface estándar `IDataPost<T>`
- Clase abstracta `DataContext<T>` con métodos finales
- Implementaciones concretas para cada entidad

### 2. Gestión de Relaciones

- **Foreign Keys**: Composición de objetos
- **Primary Keys**: Autoincrement
- Ejemplo:

```java
public class Film extends Entity {
    private Language language;        // FK por composición
    private ArrayList<Integer> actorIds;  // Muchos-a-muchos
}
```

### 3. Validaciones

- Email: RFC 5322
- Teléfono: Múltiples formatos
- SSN: XXX-XX-XXXX o XXXXXXXXX
- Fecha: YYYY-MM-DD
- Códigos postales
- Números positivos y decimales

### 4. Reportes y Estadísticas

- Listado de entidades
- Estadísticas por tabla
- Exportación CSV
- Exportación JSON
- Métricas financieras (ingresos, pagos promedio)
- Análisis por tienda, ciudad, país

### 5. Soft Delete

Los registros se marcan como inactivos en lugar de eliminarse físicamente:

```java
customerManager.delete(customerId);  // Marca active = 0
```

## Ejemplos de Uso

### Crear un Actor

```java
Actor actor = new Actor("Tom", "Hanks");
actorManager.post(actor);
```

### Obtener Actor por ID

```java
Actor actor = actorManager.getById(1);
System.out.println(actor);
```

### Buscar Actores por Campo

```java
ArrayList<Actor> results = actorManager.getByField("last_name", "Hanks");
```

### Actualizar Actor

```java
Actor actor = actorManager.getById(1);
actor.setFirstName("Thomas");
actorManager.put(actor);
```

### Exportar a CSV

```java
ReportGenerator report = new ReportGenerator();
report.exportActorsToCSV("actors_report.csv");
```

### Exportar a JSON

```java
ReportGenerator report = new ReportGenerator();
report.exportFilmsToJSON("films_report.json");
```

## Parámetros de Compilación Maven

```bash
# Compilar
mvn clean compile

# Ejecutar pruebas (cuando estén disponibles)
mvn test

# Generar JAR con dependencias
mvn clean package

# Ejecutar la aplicación
mvn exec:java -Dexec.mainClass="com.sakila.Main"

# Generar documentación Javadoc
mvn javadoc:javadoc
```

## Documentación del Código

Todos los archivos incluyen:

- Encabezado de copyright
- Documentación Javadoc en clases
- Documentación Javadoc en métodos públicos
- Comentarios explicativos en lógica compleja

## Solución de Problemas

### Error de Conexión a BD

```
Failed to connect to database
```

**Solución**: Verificar:
1. MySQL está ejecutándose
2. Usuario y contraseña correctos
3. Base de datos "sakila" existe

### Error de Driver JDBC

```
MySQL JDBC Driver not found
```

**Solución**: Ejecutar `mvn clean install` para descargar dependencias

### No hay registros en búsqueda

**Causa**: La base de datos puede estar vacía o el filtro es muy específico

**Solución**: Usar `getAll()` para ver todos los registros disponibles

## Próximas Mejoras

- [ ] Interfaz gráfica (GUI) con Swing
- [ ] Validación más robusta de entradas
- [ ] Paginación de resultados
- [ ] Filtros avanzados
- [ ] Backup y restore de BD
- [ ] Transacciones múltiples
- [ ] API REST

## Autor

**kmoquetemercado-collab**

Proyecto Final - Java Programming (JP INF514)

Fecha: Mayo 2026

## Licencia

Copyright © 2026 Sakila ORM/CRUD Manager. All rights reserved.

---

**Valor**: 20 puntos (Obligatorio para aprobar el curso)

**Fecha Máxima de Entrega**: 15 de mayo de 2026
