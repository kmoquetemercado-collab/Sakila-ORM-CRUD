# Arquitectura del Proyecto Sakila ORM/CRUD

## Diagrama UML de Clases

```
┌─────────────────────────────────────────────────────────────┐
│                      IDataPost<T>                           │
│  (Interface estándar CRUD)                                  │
│  ────────────────────────────────────────────────────────── │
│  + post(T): boolean                                         │
│  + getAll(): ArrayList<T>                                   │
│  + getById(int): T                                          │
│  + getByField(String, Object): ArrayList<T>                │
│  + put(T): boolean                                          │
│  + delete(int): boolean                                     │
│  + count(): int                                             │
└─────────────────────────────────────────────────────────────┘
                            ▲
                            │ implements
                            │
┌─────────────────────────────────────────────────────────────┐
│                   DataContext<T>                            │
│  (Clase abstracta padre)                                    │
│  ────────────────────────────────────────────────────────── │
│  # connection: Connection                                   │
│  # tableName: String                                        │
│  # entityClass: Class<T>                                    │
│  ────────────────────────────────────────────────────────── │
│  + post(T): final boolean          [NO OVERRIDE]            │
│  + getAll(): final ArrayList<T>    [NO OVERRIDE]            │
│  + getById(int): final T           [NO OVERRIDE]            │
│  + getByField(...): final ArrayList<T> [NO OVERRIDE]        │
│  + put(T): final boolean           [NO OVERRIDE]            │
│  + delete(int): final boolean      [NO OVERRIDE]            │
│  + count(): final int              [NO OVERRIDE]            │
│  ────────────────────────────────────────────────────────── │
│  # abstract insertRecord(T): boolean      [OVERRIDE]        │
│  # abstract retrieveAllRecords(): ArrayList<T> [OVERRIDE]   │
│  # abstract retrieveById(int): T   [OVERRIDE]               │
│  # abstract retrieveByField(...): ArrayList<T> [OVERRIDE]   │
│  # abstract updateRecord(T): boolean [OVERRIDE]             │
│  # abstract softDeleteRecord(int): boolean [OVERRIDE]       │
│  # abstract countActiveRecords(): int [OVERRIDE]            │
└─────────────────────────────────────────────────────────────┘
                            ▲
                            │ extends
                            │
          ┌─────────────────┼─────────────────┐
          │                 │                 │
          ▼                 ▼                 ▼
    ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
    │ ActorManager │ │ FilmManager  │ │RentalManager │ ...
    │ (extends     │ │ (extends     │ │ (extends     │
    │ DataContext) │ │ DataContext) │ │ DataContext) │
    └──────────────┘ └──────────────┘ └──────────────┘


┌─────────────────────────────────────────────────────────────┐
│                     Entity                                  │
│  (Clase abstracta base para modelos)                        │
│  ────────────────────────────────────────────────────────── │
│  # id: int                                                  │
│  # active: int (1=activo, 0=inactivo)                       │
│  # createdAt: LocalDateTime                                 │
│  # updatedAt: LocalDateTime                                 │
│  ────────────────────────────────────────────────────────── │
│  + getId(): int                                             │
│  + setId(int): void                                         │
│  + getActive(): int                                         │
│  + setActive(int): void                                     │
│  + isActive(): boolean                                      │
│  + activate(): void                                         │
│  + deactivate(): void                                       │
│  + abstract toString(): String                              │
│  + clone(): Object                                          │
└─────────────────────────────────────────────────────────────���
                            ▲
                            │ extends
                            │
        ┌───────────────────┼───────────────────┐
        │                   │                   │
        ▼                   ▼                   ▼
   ┌─────────┐         ┌──────┐           ┌──────────┐
   │ Actor   │         │ Film │           │ Inventory│ ...
   │ (Entity)│         │(Entity)         │ (Entity) │
   └─────────┘         └──────┘           └──────────┘
        │                   │
        │ agregación        │ agregación
        │                   │
        ▼                   ▼
   ┌─────────┐         ┌──────────┐
   │ Country │         │ Language │
   │ (Entity)│         │ (Entity) │
   └─────────┘         └──────────┘

```

## Patrones de Diseño Implementados

### 1. **MVC (Model-View-Controller)**
- **Model**: Clases Entity (Actor, Film, etc.)
- **View**: ConsoleUI (interfaz de usuario)
- **Controller**: Managers (ActorManager, FilmManager, etc.)

### 2. **DAO (Data Access Object)**
- **DataContext**: Implementa el patrón DAO
- **Managers**: Acceso a datos específico de cada entidad

### 3. **Strategy Pattern**
- Diferentes estrategias de validación en RegexValidator

### 4. **Singleton Pattern**
- DatabaseConnection: Una única instancia de conexión

### 5. **Template Method Pattern**
- DataContext define el flujo general de CRUD
- Child classes implementan pasos específicos

## Flujo de Datos

```
ConsoleUI (Entrada del usuario)
    │
    ├─→ Controller/Manager (ActorManager, FilmManager, etc.)
    │
    ├─→ DataContext<T> (Lógica CRUD)
    │
    ├─→ DatabaseConnection (JDBC)
    │
    ├─→ MySQL Sakila Database
    │
    └─← Response/Entity Objects
         │
         └─→ ConsoleUI (Mostrar resultados)
```

## Gestión de Claves Primarias y Extranjeras

### Primary Key (Autoincrement)
```java
// No se especifica ID en INSERT
Actor actor = new Actor();
actor.setFirstName("John");
actor.setLastName("Doe");
actorManager.post(actor); // ID generado automáticamente
```

### Foreign Key (Composición)
```java
public class Film extends Entity {
    private int filmId;
    private String title;
    private Country country;        // Agregación de Country
    private Language language;      // Agregación de Language
    // ...
}
```

## Validaciones y Reglas de Negocio

1. **Validación de Email**: RFC 5322 pattern
2. **Validación de Teléfono**: Múltiples formatos soportados
3. **Validación de SSN**: XXX-XX-XXXX o XXXXXXXXX
4. **Validación de Fechas**: YYYY-MM-DD
5. **Soft Delete**: Nunca elimina físicamente, solo marca como inactivo
6. **Referential Integrity**: Respeta relaciones FK

## Gestión de Colecciones

```java
// ArrayList para almacenar múltiples registros
ArrayList<Actor> actors = actorManager.getAll();

// HashMap para búsquedas rápidas (en reportes)
HashMap<Integer, Actor> actorMap = new HashMap<>();
for (Actor a : actors) {
    actorMap.put(a.getId(), a);
}

// Búsqueda por campo
ArrayList<Actor> results = actorManager.getByField("lastName", "Doe");
```

## Estadísticas y Reportes

### Tipos de Reportes
1. **Listados**: CSV/JSON de todas las tablas
2. **Totales**: Count por tabla
3. **Promedios**: Rental duration, precio promedio, etc.
4. **Aging**: Cuentas por cobrar vencidas
5. **Métricas**: Películas por inventario, actores por película, etc.

### Exportación
- CSV: Apache Commons CSV
- JSON: Google Gson

---

**Nota**: Esta arquitectura permite escalabilidad futura y mantenimiento fácil del código.
