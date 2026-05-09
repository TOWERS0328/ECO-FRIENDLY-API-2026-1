# EcoFriendly API

## Descripción del Proyecto

EcoFriendly API es una aplicación RESTful desarrollada con Spring Boot que proporciona un sistema completo para gestionar actividades ecológicas en instituciones educativas. La plataforma permite a estudiantes registrarse, autenticarse y participar en iniciativas de reciclaje y sostenibilidad ambiental, acumulando puntos y visualizando su impacto ecológico a través de un dashboard personalizado.

Esta API está diseñada siguiendo las mejores prácticas de desarrollo, implementando autenticación JWT, arquitectura limpia con separación de capas (Controller, Service, Repository), y utilizando tecnologías modernas como Spring Security, JPA/Hibernate y base de datos H2 para desarrollo.

## Características Principales

### 🔐 Autenticación y Autorización
- Registro de usuarios con validación completa
- Autenticación JWT (JSON Web Tokens)
- Roles de usuario (Estudiante por defecto)
- Filtros de seguridad personalizados

### 📊 Dashboard Interactivo
- Resumen personalizado del usuario
- Estadísticas de puntos y ranking institucional
- Visualización de actividades recientes
- Impacto ambiental calculado

### 👥 Gestión de Usuarios
- Perfiles de estudiantes con información académica
- Gestión de roles y permisos
- Validación de datos únicos (email, cédula)

### 🌱 Sistema de Reciclaje
- Registro de actividades de reciclaje
- Acumulador de puntos por participación
- Rankings institucionales
- Métricas de impacto ambiental

## Arquitectura del Sistema

```
EcoFriendly API
├── Controllers (API Endpoints)
│   ├── AuthController - Autenticación (/api/v1/auth)
│   └── DashboardController - Dashboard (/api/v1/dashboard)
├── Services (Lógica de Negocio)
│   ├── AuthService - Gestión de autenticación
│   └── CustomUserDetailsService - Detalles de usuario para Spring Security
├── Repositories (Acceso a Datos)
│   └── UserRepository - Operaciones CRUD para usuarios
├── Models (Entidades)
│   └── User - Entidad de usuario con UserDetails
├── DTOs (Data Transfer Objects)
│   ├── LoginRequest/Response
│   ├── RegisterRequest
│   ├── ApiResponse
│   └── DashboardSummary
├── Security
│   ├── SecurityConfig - Configuración de Spring Security
│   ├── JwtTokenProvider - Generación y validación de tokens
│   └── JwtAuthenticationFilter - Filtro de autenticación JWT
└── Configuration
    └── application.properties - Configuración de la aplicación
```

## Tecnologías Utilizadas

### Backend
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.5.14** - Framework principal
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia de datos
- **Spring Validation** - Validación de datos
- **JWT (io.jsonwebtoken)** - Tokens de autenticación
- **H2 Database** - Base de datos en memoria para desarrollo
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias y build

### Base de Datos
- **H2** (desarrollo) - Base de datos embebida
- **DDL Auto: create-drop** - Recreación automática del esquema
- **Esquema SQL personalizado** - Definición de tablas en `schema.sql`

## Requisitos del Sistema

- **Java**: JDK 21 o superior
- **Maven**: 3.6+ (incluido con Maven Wrapper)
- **RAM**: Mínimo 512MB, recomendado 1GB
- **SO**: Windows, Linux, macOS

## Instalación y Configuración

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd eco-friendly-ap
```

### 2. Configurar Variables de Entorno (Opcional)
Crear un archivo `.env` en la raíz del proyecto o configurar variables de sistema:

```bash
# JWT Secret (opcional, tiene valor por defecto)
JWT_SECRET=tu-clave-secreta-aqui

# Puerto del servidor (opcional, por defecto 8081)
SERVER_PORT=8081
```

### 3. Ejecutar la Aplicación
```bash
# Usando Maven Wrapper (recomendado)
./mvnw spring-boot:run

# O usando Maven instalado
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8081`

### 4. Verificar Instalación
- **API Health**: `GET http://localhost:8081/actuator/health` (si configurado)
- **H2 Console**: `http://localhost:8081/h2-console`
  - JDBC URL: `jdbc:h2:mem:ecodb`
  - Username: `sa`
  - Password: (vacío)

## Configuración de Base de Datos

### Desarrollo (H2)
La aplicación utiliza H2 en memoria por defecto:
```properties
spring.datasource.url=jdbc:h2:mem:ecodb
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
```

### Producción (Recomendado)
Para producción, configurar una base de datos persistente como PostgreSQL o MySQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecofriendly
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## API Endpoints

### Autenticación
```
POST /api/v1/auth/register
- Registra un nuevo usuario
- Body: RegisterRequest (cedula, nombre, apellido, email, password, etc.)

POST /api/v1/auth/login
- Autentica un usuario
- Body: LoginRequest (email, password)
- Response: LoginResponse (token, user info)
```

### Dashboard
```
GET /api/v1/dashboard/summary
- Obtiene resumen del dashboard del usuario autenticado
- Headers: Authorization: Bearer <jwt-token>
- Response: DashboardSummary (estadísticas, actividades, impacto)
```

### Headers Requeridos
Todos los endpoints protegidos requieren:
```
Authorization: Bearer <jwt-token>
Content-Type: application/json
```

## Modelos de Datos

### User
```json
{
  "id": "UUID",
  "cedula": "string (único)",
  "nombre": "string",
  "apellido": "string",
  "genero": "string",
  "email": "string (único)",
  "carrera": "string",
  "password": "string (encriptado)",
  "rol": "string (default: ESTUDIANTE)",
  "createdAt": "timestamp"
}
```

### RegisterRequest
```json
{
  "cedula": "string",
  "nombre": "string",
  "apellido": "string",
  "genero": "string",
  "email": "string",
  "carrera": "string",
  "password": "string"
}
```

### LoginRequest
```json
{
  "email": "string",
  "password": "string"
}
```

## Seguridad

### JWT Configuration
- **Secret**: Configurable via `app.jwt.secret`
- **Expiración**: 24 horas (86400000 ms) por defecto
- **Algoritmo**: HS256

### Endpoints Públicos vs Protegidos
- **Públicos**: `/api/v1/auth/*`
- **Protegidos**: `/api/v1/dashboard/*` (requieren Bearer token)

### Validaciones
- Email único y formato válido
- Cédula única
- Password con requisitos de seguridad
- Campos requeridos validados

## Testing

### Ejecutar Tests
```bash
# Todos los tests
./mvnw test

# Tests específicos
./mvnw test -Dtest=EcoFriendlyApApplicationTests

# Con reporte de cobertura (si configurado)
./mvnw test jacoco:report
```

### Cobertura de Tests
- Unit tests para servicios
- Integration tests para controladores
- Security tests para autenticación

## Despliegue

### Build para Producción
```bash
# Crear JAR
./mvnw clean package

# Ejecutar JAR
java -jar target/eco-friendly-ap-0.0.1-SNAPSHOT.jar
```

### Docker (Futuro)
```dockerfile
FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Variables de Entorno para Producción
```bash
export JWT_SECRET=tu-clave-produccion-super-segura
export SPRING_PROFILES_ACTIVE=prod
export SERVER_PORT=8080
```

## Monitoreo y Logs

### Logs de Aplicación
- Spring Boot logging configurado por defecto
- SQL queries visibles en desarrollo (`spring.jpa.show-sql=true`)
- Logs de seguridad y autenticación

### Health Checks
- Spring Boot Actuator disponible en `/actuator/health`
- Métricas de aplicación en `/actuator/metrics`

## Desarrollo

### Estructura del Proyecto
```
src/
├── main/
│   ├── java/com/ecofriendly/api/
│   │   ├── EcoFriendlyApApplication.java
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── security/
│   │   └── service/
│   └── resources/
│       ├── application.properties
│       └── templates/schema.sql
└── test/
    └── java/com/ecofriendly/api/
        └── EcoFriendlyApApplicationTests.java
```

### Extensiones y Mejoras Futuras
- [ ] Sistema completo de actividades de reciclaje
- [ ] API de administración para instituciones
- [ ] Notificaciones push
- [ ] Integración con apps móviles
- [ ] Reportes avanzados
- [ ] Gamificación adicional

## Contribución

1. Fork el proyecto
2. Crear rama feature (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

### Estándares de Código
- Usar Lombok para reducir boilerplate
- Validar todos los inputs
- Manejar excepciones apropiadamente
- Escribir tests para nueva funcionalidad
- Seguir convenciones de nombres de Spring Boot

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Soporte

Para soporte técnico o preguntas:
- Crear issue en el repositorio
- Contactar al equipo de desarrollo
- Revisar documentación de Spring Boot

## Versiones

- **v0.0.1-SNAPSHOT**: Versión inicial con autenticación y dashboard básico
- Próximas versiones incluirán sistema completo de reciclaje y administración

---

**Desarrollado con ❤️ para promover la sostenibilidad ambiental en instituciones educativas.**"# ECO-FRIENDLY-API-2026-1" 
