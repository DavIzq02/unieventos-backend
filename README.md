# 🎓 Unieventos — Backend

API REST desarrollada con **Spring Boot 3** para la gestión integral de eventos universitarios. Permite crear y administrar eventos, jornadas, inscripciones, asistencias, reseñas y usuarios dentro de una plataforma institucional.

---

## 📋 Tabla de Contenidos

- [Especificaciones Técnicas](#especificaciones-técnicas)
- [Dependencias y Librerías](#dependencias-y-librerías)
- [Arquitectura del Proyecto](#arquitectura-del-proyecto)
- [Patrón MVC](#patrón-mvc)
- [Árbol del Proyecto](#árbol-del-proyecto)
- [Base de Datos](#base-de-datos)
- [Arquitectura de la API](#arquitectura-de-la-api)
- [Endpoints de la Aplicación](#endpoints-de-la-aplicación)
- [Almacenamiento de Archivos](#almacenamiento-de-archivos)
- [Docker](#docker)
- [Variables de Entorno](#variables-de-entorno)
- [Ejecución Local](#ejecución-local)

---

## ⚙️ Especificaciones Técnicas

| Propiedad | Valor |
|-----------|-------|
| **Lenguaje** | Java 17 |
| **Framework principal** | Spring Boot 3.4.4 |
| **Herramienta de build** | Maven (mvnw wrapper incluido) |
| **Puerto por defecto** | `8080` (configurable con variable `PORT`) |
| **Base de datos** | PostgreSQL (Supabase — AWS us-east-1) |
| **ORM** | Hibernate / Spring Data JPA |
| **Estrategia DDL** | `none` (esquema manual, sin auto-generación) |
| **Autenticación** | Spring Security (sin JWT; endpoints abiertos para desarrollo) |
| **Almacenamiento multimedia** | GitHub API + jsDelivr CDN |
| **Generación de QR** | ZXing 3.5.3 |
| **Contenerización** | Docker (multi-stage build) |
| **Tamaño máx. de archivo** | 20 MB |

---

## 📦 Dependencias y Librerías

| Dependencia | GroupId / ArtifactId | Versión | Propósito |
|---|---|---|---|
| Spring Boot Starter Web | `spring-boot-starter-web` | 3.4.4 | Servidor HTTP, controladores REST |
| Spring Boot Starter Data JPA | `spring-boot-starter-data-jpa` | 3.4.4 | ORM con Hibernate, repositorios |
| Spring Boot Starter Security | `spring-boot-starter-security` | 3.4.4 | Seguridad, hashing de contraseñas (BCrypt) |
| PostgreSQL Driver | `org.postgresql:postgresql` | (runtime) | Conector JDBC para PostgreSQL |
| ZXing Core | `com.google.zxing:core` | 3.5.3 | Generación de códigos QR (núcleo) |
| ZXing JavaSE | `com.google.zxing:javase` | 3.5.3 | Renderizado de QR a imagen PNG |
| Commons Codec | `commons-codec:commons-codec` | 1.16.0 | Codificación Base64 / hashing para tokens de seguridad |
| Spring Boot Starter Test | `spring-boot-starter-test` | 3.4.4 | Pruebas unitarias e integración |

---

## 🏛️ Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas basada en el patrón **MVC (Model – View – Controller)**, adaptado a una API REST donde la "vista" es sustituida por respuestas JSON estandarizadas.

```
┌─────────────────────────────────────────────────────────────┐
│                        CLIENTE (Frontend / App)              │
│              Angular / Navegador / Herramienta REST           │
└─────────────────────────┬───────────────────────────────────┘
                           │ HTTP/JSON
┌─────────────────────────▼───────────────────────────────────┐
│                  CAPA DE CONTROLADORES                        │
│        @RestController  — Reciben y responden peticiones      │
│        Retornan ApiResponse<T> estandarizado                  │
└─────────────────────────┬───────────────────────────────────┘
                           │ Llama a
┌─────────────────────────▼───────────────────────────────────┐
│                   CAPA DE SERVICIOS                           │
│     @Service — Lógica de negocio, validaciones, reglas        │
│     Orquestan repositorios y servicios externos               │
└──────────────┬──────────────────────────┬───────────────────┘
               │                          │
┌──────────────▼──────┐    ┌──────────────▼──────────────────┐
│  CAPA DE            │    │  SERVICIOS EXTERNOS               │
│  REPOSITORIOS       │    │  • GitService (GitHub API)        │
│  @Repository        │    │  • QrService (ZXing)              │
│  JpaRepository<T>   │    │  • SeguridadService (tokens)      │
│  JPQL Queries       │    └──────────────────────────────────┘
└──────────────┬──────┘
               │ Hibernate / JPA
┌──────────────▼──────────────────────────────────────────────┐
│                BASE DE DATOS — PostgreSQL (Supabase)          │
│                     AWS us-east-1 / SSL                       │
└─────────────────────────────────────────────────────────────┘
```

### Flujo de una petición típica

```
Request HTTP → Controller → Service → Repository → DB
                                   ↘ GitService (si hay multimedia)
                ← ApiResponse<T> ← ← ← ← ← ← ←
```

---

## 🎨 Patrón MVC

### Model (Modelo)
Los modelos son entidades JPA que representan directamente las tablas de la base de datos. Ubicados en el paquete `models/`.

- Sin herencia ni mapeo de jerarquías.
- Usan `@Entity`, `@Table`, `@Column`, `@ManyToOne`, `@JoinColumn`.
- Incluyen constructores de proyección para JPQL (sin cargar relaciones innecesarias).
- DDL manual (`ddl-auto=none`): la base de datos se crea con scripts SQL directamente.

### View (Vista → JSON)
No existe una vista tradicional. La "vista" es la clase genérica `ApiResponse<T>` que estandariza todas las respuestas:

```json
{
  "codigo": 200,
  "mensaje": "Consulta realizada exitosamente",
  "data": { ... },
  "listaRespuesta": [ ... ]
}
```

Códigos manejados: `200`, `201`, `202`, `404`, `500`, `501`.

### Controller (Controlador)
Clases anotadas con `@RestController` que:
- Exponen endpoints REST (`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`).
- Delegan toda la lógica al `Service` correspondiente.
- Retornan siempre `ApiResponse<T>`.

### DTOs (Data Transfer Objects)
En el paquete `dto/`, los DTOs permiten proyectar consultas JPQL complejas sin exponer entidades completas:

| DTO | Propósito |
|---|---|
| `ApiResponse<T>` | Envoltura estándar de todas las respuestas |
| `EventoDTO` | Proyección de evento con tipo y metadatos |
| `JornadaDTO` | Proyección de jornada con datos de sesión |
| `AsistenciaEventoDTO` | Proyección de asistencia |
| `ResenaDTO` | Reseña enriquecida con lista de multimedia |
| `PayLoad` | Datos de autenticación |

---

## 🌳 Árbol del Proyecto

```
unieventos-backend/
├── Dockerfile                          # Build multi-etapa (Maven + JRE 17)
├── config.env                          # Variables de entorno (token GitHub)
├── pom.xml                             # Dependencias Maven
├── mvnw / mvnw.cmd                     # Maven Wrapper
└── src/
    └── main/
        ├── java/com/example/unieventos/
        │   ├── UnieventosApplication.java       # Punto de entrada Spring Boot
        │   │
        │   ├── config/
        │   │   ├── CorsConfig.java              # Configuración CORS global
        │   │   └── SecurityConfig.java          # BCryptPasswordEncoder + FilterChain
        │   │
        │   ├── models/                          # Entidades JPA (tablas DB)
        │   │   ├── Usuario.java
        │   │   ├── Rol.java
        │   │   ├── Permiso.java
        │   │   ├── PermisoRol.java
        │   │   ├── Comunidad.java
        │   │   ├── TipoDeEvento.java
        │   │   ├── Evento.java
        │   │   ├── EventoComunidad.java
        │   │   ├── EventoInteresUsuario.java
        │   │   ├── Jornada.java
        │   │   ├── Preinscripcion.java
        │   │   ├── PreinscripcionJornada.java
        │   │   ├── AsistenciaEvento.java
        │   │   ├── Resena.java                  ← nuevo
        │   │   └── ResenaMultimedia.java        ← nuevo
        │   │
        │   ├── dto/                             # Objetos de transferencia de datos
        │   │   ├── ApiResponse.java             # Envoltura estándar de respuestas
        │   │   ├── EventoDTO.java
        │   │   ├── JornadaDTO.java
        │   │   ├── AsistenciaEventoDTO.java
        │   │   ├── ResenaDTO.java               ← nuevo
        │   │   └── PayLoad.java
        │   │
        │   ├── repositories/                    # Interfaces JpaRepository + JPQL
        │   │   ├── UsuarioRepository.java
        │   │   ├── RolRepository.java
        │   │   ├── PermisoRepository.java
        │   │   ├── ComunidadRepository.java
        │   │   ├── TipoDeEventoRepository.java
        │   │   ├── EventoRepository.java
        │   │   ├── EventoComunidadRepository.java
        │   │   ├── EventoInteresUsuarioRepository.java
        │   │   ├── JornadaRepository.java
        │   │   ├── PreinscripcionRepository.java
        │   │   ├── PreinscripcionJornadaRepository.java
        │   │   ├── AsistenciaEventoRepository.java
        │   │   ├── ResenaRepository.java        ← nuevo
        │   │   └── ResenaMultimediaRepository.java ← nuevo
        │   │
        │   ├── services/                        # Lógica de negocio
        │   │   ├── UsuarioService.java
        │   │   ├── ComunidadService.java
        │   │   ├── EventoService.java
        │   │   ├── EventoComunidadService.java
        │   │   ├── EventoInteresUsuarioService.java
        │   │   ├── JornadaService.java
        │   │   ├── PreinscripcionService.java
        │   │   ├── PreinscripcionJornadaService.java
        │   │   ├── AsistenciaEventoService.java
        │   │   ├── ResenaService.java           ← nuevo
        │   │   ├── ResenaMultimediaService.java ← nuevo
        │   │   ├── GitService.java              # Subida de archivos a GitHub
        │   │   ├── QrService.java               # Generación de QR con ZXing
        │   │   └── SeguridadService.java        # Tokens HMAC para QR
        │   │
        │   └── controllers/                     # Endpoints REST
        │       ├── HomeController.java
        │       ├── UsuarioController.java
        │       ├── RolController.java
        │       ├── PermisoController.java
        │       ├── ComunidadController.java
        │       ├── TipoDeEventoController.java
        │       ├── EventoController.java
        │       ├── EventoComunidadController.java
        │       ├── EventoInteresUsuarioController.java
        │       ├── JornadaController.java
        │       ├── PreinscripcionController.java
        │       ├── PreinscripcionJornadaController.java
        │       ├── AsistenciaEventoController.java
        │       ├── QrController.java
        │       ├── ResenaController.java        ← nuevo
        │       └── ResenaMultimediaController.java ← nuevo
        │
        └── resources/
            └── application.properties           # Configuración de BD, puertos, GitHub
```

---

## 🗄️ Base de Datos

**Motor:** PostgreSQL  
**Proveedor:** [Supabase](https://supabase.com) — instancia en AWS `us-east-1`  
**Conexión:** Pool de conexiones sobre SSL (`sslmode=require`)

### Cadena de conexión
```
jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:5432/postgres?sslmode=require
```

### Configuración Hibernate
| Propiedad | Valor |
|---|---|
| `ddl-auto` | `none` — el esquema es gestionado manualmente con SQL |
| `show-sql` | `true` — queries JPQL se imprimen en consola |
| `dialect` | `PostgreSQLDialect` |

### Esquema de Tablas

```
usuario ──────────────┐
  ├── id_rol → rol    │
  └── id_comunidad    │
       → comunidad    │
                      │
evento ───────────────┤
  ├── id_tipo         │
  │   → tipo_de_evento│
  └── id_usuario_creador → usuario
                      │
evento_comunidad ─────┤  (N:M evento ↔ comunidad)
evento_interes_usuario┤  (usuario marca interés en evento)
jornada ──────────────┤  (sesiones de un evento)
preinscripcion ───────┤  (usuario → evento)
preinscripcion_jornada┤  (preinscripcion → jornada)
asistencia_evento ────┤  (usuario asistió a jornada de evento)
resena ───────────────┤  (reseña de un usuario post-asistencia)
resena_multimedia ────┘  (archivos multimedia de una reseña)

permiso ─────────────────────────────────────────────────────
permiso_rol → (permiso ↔ rol)
```

---

## 🌐 Arquitectura de la API

- **Estilo:** REST sobre HTTP/HTTPS
- **Formato de intercambio:** JSON
- **Base path:** `/api/`
- **Puerto:** `8080` por defecto
- **CORS:** habilitado para todos los orígenes (`*`) en todos los controladores
- **Seguridad:** Spring Security con `anyRequest().permitAll()` (sin JWT en producción actual); BCrypt para contraseñas

### Estructura estándar de respuesta

```json
// Respuesta exitosa con un objeto
{
  "codigo": 200,
  "mensaje": "Consulta realizada exitosamente",
  "data": { ... }
}

// Respuesta exitosa con lista
{
  "codigo": 200,
  "mensaje": "Consulta realizada exitosamente",
  "listaRespuesta": [ ... ]
}

// Recurso creado
{
  "codigo": 201,
  "mensaje": "Recurso creado exitosamente",
  "data": { ... }
}

// Sin resultados
{
  "codigo": 404,
  "mensaje": "Consulta sin resultados"
}

// Error
{
  "codigo": 500,
  "mensaje": "Descripción del error"
}
```

---

## 🔗 Endpoints de la Aplicación

### 🏠 Home — `/api`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/test` | Health check del servidor |

---

### 👤 Usuarios — `/api/usuario`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `GET` | `/api/usuario/listAll` | — | Listar todos los usuarios |
| `GET` | `/api/usuario/{id}` | — | Buscar usuario por ID |
| `POST` | `/api/usuario/create` | `Usuario` | Crear usuario (desde admin) |
| `POST` | `/api/usuario/login` | `{correo, contrasena}` | Autenticación |
| `POST` | `/api/usuario/createPostLogin` | `multipart: data+imagen` | Crear usuario con foto de perfil |
| `POST` | `/api/usuario/updateFotoPerfil` | `multipart: data+imagen` | Actualizar foto de perfil |
| `PUT` | `/api/usuario/update` | `Usuario` | Actualizar datos del usuario |
| `PUT` | `/api/usuario/cambiar-estado` | `{id, activo}` | Activar / desactivar usuario |
| `DELETE` | `/api/usuario/delete/{id}` | — | Eliminar usuario |

---

### 🎭 Roles — `/api/rol`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `GET` | `/api/rol/listAll` | — | Listar todos los roles |
| `POST` | `/api/rol/create` | `Rol` | Crear nuevo rol |

---

### 🔐 Permisos — `/api/permiso`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `GET` | `/api/permiso/listAll` | — | Listar todos los permisos |

---

### 🏘️ Comunidades — `/api/comunidad`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `GET` | `/api/comunidad/listAll` | — | Listar todas las comunidades |
| `GET` | `/api/comunidad/listarbyEvento/{id}` | — | Listar comunidades de un evento |
| `POST` | `/api/comunidad/create` | `Comunidad` | Crear comunidad |

---

### 🏷️ Tipos de Evento — `/api/tipos-eventos`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `GET` | `/api/tipos-eventos/listAll` | — | Listar todos los tipos de evento |
| `POST` | `/api/tipos-eventos/create` | `TipoDeEvento` | Crear tipo de evento |

---

### 📅 Eventos — `/api/evento`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `GET` | `/api/evento/findById/{id}` | — | Obtener evento por ID |
| `GET` | `/api/evento/listarEventosActivos` | — | Listar eventos activos y en curso |
| `GET` | `/api/evento/listarEventosProximos` | — | Listar eventos próximos |
| `POST` | `/api/evento/listarByUsuario` | `{id}` | Listar eventos creados por un usuario |
| `POST` | `/api/evento/create` | `Evento` | Crear nuevo evento |
| `POST` | `/api/evento/upLoadPortadaEvento/{id}` | `multipart: imagen` | Subir imagen de portada |
| `PUT` | `/api/evento/update` | `Evento` | Modificar evento |
| `PUT` | `/api/evento/updatePortadaEvento/{id}` | `multipart: imagen` | Actualizar portada |
| `PUT` | `/api/evento/iniciar/{id}` | — | Iniciar/abrir evento (`abierto=true`) |
| `PUT` | `/api/evento/cerrar/{id}` | — | Cerrar evento (`abierto=false`) |
| `PUT` | `/api/evento/inactivar/{id}` | — | Inactivar evento (`activo=false`) |
| `DELETE` | `/api/evento/delete/{id}` | — | Eliminar evento |

---

### 🔗 Evento–Comunidad — `/api/evento-comunidad`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `POST` | `/api/evento-comunidad/listarByComunidad` | `{id}` | Eventos disponibles para una comunidad |
| `POST` | `/api/evento-comunidad/create/{id}` | `[Comunidad]` | Asignar comunidades a un evento |
| `PUT` | `/api/evento-comunidad/update/{id}` | `[Comunidad]` | Actualizar comunidades de un evento |

---

### ⭐ Interés de Usuario en Eventos — `/api/interes-usuario`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `POST` | `/api/interes-usuario/listarByInteres` | `{id}` | Eventos de interés del usuario |

---

### 📆 Jornadas — `/api/jornada-evento`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `POST` | `/api/jornada-evento/listByEvento` | `{id}` | Listar jornadas de un evento |
| `POST` | `/api/jornada-evento/createJornadas` | `[Jornada]` | Crear jornadas en lote |
| `PUT` | `/api/jornada-evento/updateJornadas` | `[Jornada]` | Actualizar jornadas en lote |

---

### 📝 Pre-inscripciones — `/api/preinscripcion`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `POST` | `/api/preinscripcion/create` | `Preinscripcion` | Crear (o recuperar existente) pre-inscripción |
| `POST` | `/api/preinscripcion/listar` | `Preinscripcion` | Buscar pre-inscripción |

---

### 📋 Pre-inscripción por Jornada — `/api/preinscripcion-jornada`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `POST` | `/api/preinscripcion-jornada/create` | `PreinscripcionJornada` | Registrar usuario en jornada |
| `POST` | `/api/preinscripcion-jornada/listar` | `Preinscripcion` | Listar jornadas de una pre-inscripción |
| `POST` | `/api/preinscripcion-jornada/listarByJornada` | `{id}` | Listar usuarios pre-inscritos en jornada |

---

### ✅ Asistencia a Eventos — `/api/asistencia`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `POST` | `/api/asistencia/create` | `AsistenciaEvento` | Registrar asistencia (valida comunidad, estado, código, preinscripción) |
| `POST` | `/api/asistencia/listarByJornada` | `{id}` | Listar usuarios que asistieron a una jornada |
| `POST` | `/api/asistencia/findAsistenciaByUsuario` | `{id}` | Listar eventos pasados con asistencia del usuario |

---

### 🔲 Códigos QR — `/api/evento/qr`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/evento/qr/{eventoId}/{jornadaId}/{codigo}` | Genera imagen PNG del QR firmado (HMAC) con URL de asistencia |

El QR codifica:
```
https://davizq02.github.io/unieventos-frontend/#/asistencia/?e={id}&j={jornadaId}&ts={timestamp}&tk={token}&c={codigo}
```

---

### ⭐ Reseñas — `/api/resena`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `POST` | `/api/resena/create` | `Resena` | Crear reseña (1 por asistencia, calificación 1–5) |
| `PUT` | `/api/resena/update` | `Resena` | Modificar título, descripción y calificación |
| `DELETE` | `/api/resena/delete/{id}` | — | Soft-delete de reseña (`activo=false`) |
| `GET` | `/api/resena/get/{id}` | — | Obtener reseña por ID |
| `POST` | `/api/resena/listarPorEvento` | `{id}` | Listar reseñas de un evento **con multimedia** |
| `POST` | `/api/resena/listarPorUsuario` | `{id}` | Listar reseñas de un usuario **con multimedia** |
| `POST` | `/api/resena/calificacionPromedio` | `{id}` | Calificación promedio otorgada por el usuario |

---

### 🖼️ Multimedia de Reseñas — `/api/resena-multimedia`

| Método | Ruta | Body | Descripción |
|--------|------|------|-------------|
| `POST` | `/api/resena-multimedia/create` | `ResenaMultimedia` | Agregar URL multimedia a una reseña |
| `DELETE` | `/api/resena-multimedia/delete/{id}` | — | Soft-delete de multimedia |
| `GET` | `/api/resena-multimedia/get/{id}` | — | Obtener multimedia por ID |
| `POST` | `/api/resena-multimedia/listarPorResena` | `{id}` | Listar multimedia activa de una reseña |

---

## 🗃️ Almacenamiento de Archivos

Las imágenes (portadas de eventos y fotos de perfil) **no se almacenan en el servidor**. Se usa una estrategia CDN gratuita:

```
Archivo → GitHub API (PUT) → Repositorio DavIzq02/assets-unieventos
                           → jsDelivr CDN
                           → URL pública guardada en BD
```

- **Subida:** `GitService.upLoadFile()` convierte el `MultipartFile` a Base64 y lo sube vía GitHub Contents API.
- **Actualización:** Si ya existe el archivo, se obtiene el SHA y se reemplaza; luego se purga el caché de jsDelivr.
- **URL resultante:** `https://cdn.jsdelivr.net/gh/DavIzq02/assets-unieventos@main/{carpeta}/{nombre}`

---

## 🐳 Docker

El proyecto incluye un `Dockerfile` con **build multi-etapa**:

```dockerfile
# Etapa 1: Compilación con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de producción ligera
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Build y ejecución:**
```bash
docker build -t unieventos-backend .
docker run -p 8080:8080 unieventos-backend
```

---

## 🔑 Variables de Entorno

| Variable | Descripción |
|---|---|
| `PORT` | Puerto del servidor (por defecto `8080`) |
| `GITHUB_TOKEN` | Token de acceso personal de GitHub para subir archivos |
| `GITHUB_REPO` | Repositorio de assets en formato `usuario/repo` |

Ejemplo de archivo `config.env`:
```env
GITHUB_TOKEN=ghp_xxxxxxxxxxxxxxxxxxxxx
GITHUB_REPO=DavIzq02/assets-unieventos
```

---

## 🚀 Ejecución Local

### Prerequisitos
- Java 17+
- Maven 3.9+ (o usar el wrapper `mvnw`)
- Acceso a la base de datos PostgreSQL (Supabase)

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/DavIzq02/unieventos-backend.git
cd unieventos-backend

# 2. Ejecutar con Maven Wrapper
./mvnw spring-boot:run

# 3. Verificar que el servidor está corriendo
curl http://localhost:8080/api/test
```

### Compilar JAR
```bash
./mvnw clean package -DskipTests
java -jar target/unieventos-0.0.1-SNAPSHOT.jar
```

---

## 🧩 Reglas de Negocio Destacadas

| Módulo | Regla |
|---|---|
| **Asistencia** | El usuario debe pertenecer a la comunidad del evento |
| **Asistencia** | El evento debe estar activo (abierto=true y en rango de fechas) |
| **Asistencia** | Si `requiereInscripcion=true`, el usuario debe estar preinscrito |
| **Asistencia** | Si `requiereCodigo=true`, el código enviado debe coincidir |
| **Asistencia** | No se permite registrar asistencia dos veces en la misma jornada |
| **Reseña** | Solo se permite una reseña por registro de asistencia |
| **Reseña** | La calificación debe ser un entero entre 1 y 5 |
| **Reseña / Multimedia** | El eliminado es lógico (`activo=false`), nunca físico |
| **QR** | El token del QR se firma con HMAC e incluye timestamp para expiración |
| **Contraseña** | Almacenada con BCrypt (Spring Security `BCryptPasswordEncoder`) |

---

*Desarrollado para el sistema de gestión de eventos de la Universidad — 2026*
