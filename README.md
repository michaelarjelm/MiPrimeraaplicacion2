# Mi Primera Aplicación 2 (INACAP) 📱

Proyecto de aplicación nativa Android desarrollado en **Kotlin** como parte de la asignatura **Aplicaciones Móviles para IoT (TI3V42)** en **INACAP**.

---

## 📋 Bitácora de Desarrollo y Funcionalidades

### 🗓️ Sesión 3 y 4 (Agosto - Septiembre 2026)

#### 1. 🏗️ Arquitectura y Configuración Base
- **Lenguaje**: Kotlin con Gradle (Kotlin DSL - `build.gradle.kts`).
- **Compatibilidad**: `compileSdk = 37`, `targetSdk = 37`, `minSdk = 24` (Android 7.0+).
- **Diseño**: `ConstraintLayout` con soporte moderno Edge-to-Edge (`enableEdgeToEdge()`).
- **Arquitectura Multiactivity**: Navegación mediante `Intent` explícito entre `MainActivity` y `BienvenidaActivity`.

---

#### 2. 🧩 Los 20 Incrementales de la Interfaz y Lógica

##### Sesión 3: Login y Navegación Base (Incrementos 1 al 10)
1. **Incremento 1 — `ImageView` con Logo INACAP:** Logo institucional (`@drawable/logo_inacap`) centrado horizontalmente.
2. **Incremento 2 — `TextView` de Título:** Título `"Iniciar sesión"` (24sp, negrita).
3. **Incremento 3 — `EditText` para Usuario/Correo:** Campo `edtUsuario` con ancho dinámico (`0dp`) y márgenes laterales de 24dp.
4. **Incremento 4 — `EditText` para Contraseña:** Campo `edtPassword` con `android:inputType="textPassword"`.
5. **Incremento 5 — `CheckBox` Recordarme:** Casilla de verificación `chkRecordarme`.
6. **Incremento 6 — `Button` Ingresar:** Botón de inicio de sesión de ancho completo.
7. **Incremento 7 — Lógica en Kotlin (`onIngresarClick`):** Manejo de clics y lectura de datos con `findViewById`.
8. **Incremento 8 — Segunda Pantalla (`BienvenidaActivity`):** Creación de la actividad y registro en `AndroidManifest.xml`.
9. **Incremento 9 — Navegación con `Intent`:** Transición hacia `BienvenidaActivity`.
10. **Incremento 10 — Transferencia de Datos entre Pantallas:** Envío vía `putExtra("usuario", usuario)` y lectura en `BienvenidaActivity`.

##### Sesión 4: Sube el Nivel (Incrementos 11 al 20)
11. **Incremento 11 — Botón Limpiar:** Botón `btnLimpiar` con `onLimpiarClick` para vaciar campos, resetear errores y contador.
12. **Incremento 12 — ImageButton para Contraseña:** Alternancia de visibilidad de contraseña con icono vectorial `@drawable/ic_visibility_24`.
13. **Incremento 13 — Contador de Intentos Fallidos:** Conteo de intentos en memoria y despliegue en mensaje `Toast`.
14. **Incremento 14 — Validación Completa:** Verificación de formato de correo con `Patterns.EMAIL_ADDRESS`, contraseña >= 6 caracteres y feedback con `.error`.
15. **Incremento 15 — Tercera Pantalla (`PreferenciasActivity`):** Botón `btnPreferencias` en bienvenida y navegación con paso de parámetros.
16. **Incremento 16 — LinearLayout Vertical + Switch:** Contenedor lineal apilado y control `swNotificaciones`.
17. **Incremento 17 — Spinner de Idioma:** Desplegable `spIdioma` conectado a `@array/idiomas` en `strings.xml`.
18. **Incremento 18 — RadioGroup y RadioButton:** Selector de unidad de temperatura (Celsius / Fahrenheit).
19. **Incremento 19 — ProgressBar Asíncrono:** Barra de progreso `pbGuardando` con retardo simulado de 1 segundo vía `Handler.postDelayed`.
20. **Incremento 20 — Cerrar Sesión:** Botón `btnCerrarSesion` con `finishAffinity()` para vaciar el stack de navegación y volver al Login.

---

## 📂 Estructura del Proyecto

```text
MiPrimeraaplicacion2/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/miprimeraaplicacion2/
│   │   │   │   ├── MainActivity.kt          # Pantalla de Login, validaciones e Intents
│   │   │   │   ├── BienvenidaActivity.kt    # Pantalla de Bienvenida y navegación a Preferencias
│   │   │   │   └── PreferenciasActivity.kt  # Pantalla de Preferencias y controles de configuración
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── logo_inacap.png      # Logo institucional INACAP
│   │   │   │   │   └── ic_visibility_24.xml # Icono vectorial para ver contraseña
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml         # Layout del Login con ConstraintLayout
│   │   │   │   │   ├── activity_bienvenida.xml   # Layout de Bienvenida
│   │   │   │   │   └── activity_preferencias.xml # Layout de Preferencias con LinearLayout
│   │   │   │   └── values/
│   │   │   │       ├── colors.xml
│   │   │   │       ├── strings.xml               # Recursos de texto y array de idiomas
│   │   │   │       └── themes.xml
│   │   │   └── AndroidManifest.xml          # Registro de actividades y permisos
│   │   └── test/
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml                   # Catálogo de versiones y dependencias
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🛠️ Tecnologías y Dependencias
- **Kotlin** & **Android Gradle Plugin (AGP)**
- **AndroidX**: `appcompat`, `constraintlayout`, `core-ktx`, `activity-ktx`
- **Material Components for Android**
- **Android Patterns API** para validación de expresiones regulares.
