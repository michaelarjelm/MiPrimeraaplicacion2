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

#### 2. 🧩 Los 10 Incrementales de la Interfaz y Lógica

1. **Incremento 1 — `ImageView` con Logo INACAP:**
   - Incorporación del logo institucional (`@drawable/logo_inacap`) centrado horizontalmente (96dp x 96dp, margen superior de 32dp).
2. **Incremento 2 — `TextView` de Título:**
   - Título `"Iniciar sesión"` (24sp, negrita, centrado horizontalmente bajo el logo).
3. **Incremento 3 — `EditText` para Usuario/Correo:**
   - Campo de entrada `edtUsuario` con hint `"Usuario"`, ancho dinámico (`0dp`) y márgenes laterales de 24dp.
4. **Incremento 4 — `EditText` para Contraseña:**
   - Campo `edtPassword` con `android:inputType="textPassword"` para enmascarar caracteres.
5. **Incremento 5 — `CheckBox` Recordarme:**
   - Casilla de verificación `chkRecordarme` con texto `"Recordarme"`.
6. **Incremento 6 — `Button` Ingresar:**
   - Botón `btnIngresar` de ancho completo con texto `"Ingresar"`.
7. **Incremento 7 — Lógica en Kotlin (`onIngresarClick`):**
   - Vinculación mediante `android:onClick` para leer las vistas con `findViewById` y procesar el formulario.
8. **Incremento 8 — Segunda Pantalla (`BienvenidaActivity`):**
   - Creación de `BienvenidaActivity`, diseño `activity_bienvenida.xml` con `txtBienvenida` centrado y registro formal en `AndroidManifest.xml`.
9. **Incremento 9 — Navegación con `Intent`:**
   - Transición fluida desde `MainActivity` hacia `BienvenidaActivity` al superar las validaciones.
10. **Incremento 10 — Transferencia de Datos entre Pantallas:**
    - Envío del usuario vía `intent.putExtra("usuario", usuario)` y recepción en `BienvenidaActivity` mostrando `"Bienvenido, <usuario>"`.

---

#### 3. ✨ Mejoras y Validaciones Avanzadas Implementadas

* **👁️ Mostrar / Ocultar Contraseña:**
  - `ImageButton` interactivo (`btnMostrarPassword`) con icono vectorial `@drawable/ic_visibility_24`.
  - Alternancia dinámica de `InputType` (`TYPE_TEXT_VARIATION_VISIBLE_PASSWORD` / `TYPE_TEXT_VARIATION_PASSWORD`) manteniendo la posición del cursor.
* **📧 Validación de Formato de Correo Electrónico:**
  - Uso de `android.util.Patterns.EMAIL_ADDRESS` para asegurar que el usuario ingrese una dirección de correo válida.
* **🔒 Validación de Longitud de Contraseña:**
  - Comprobación de longitud mínima de al menos 6 caracteres.
* **⚠️ Notificación Visual de Errores (`.error`):**
  - Señalización directa en los campos de entrada (`edtUsuario.error` y `edtPassword.error`) indicando el motivo exacto del fallo.
* **🔢 Contador de Intentos Fallidos:**
  - Variable `intentosFallidos` a nivel de clase que contabiliza los intentos erróneos y los refleja en el mensaje flotante `Toast` (ej. `"Completa usuario y contraseña (intento 2)"`).
* **🧹 Función de Limpieza de Formulario (`onLimpiarClick`):**
  - Botón dedicado que restablece los campos de texto, desmarca el checkbox, borra los mensajes de error y reinicia el contador de intentos a 0.
* **📝 Código 100% Comentado:**
  - Cada línea en Kotlin y XML cuenta con comentarios explicativos de su funcionamiento y propósito arquitectónico.

---

## 📂 Estructura del Proyecto

```text
MiPrimeraaplicacion2/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/miprimeraaplicacion2/
│   │   │   │   ├── MainActivity.kt          # Pantalla de Login, validaciones e Intents
│   │   │   │   └── BienvenidaActivity.kt    # Pantalla de destino y recepción de extras
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── logo_inacap.png      # Logo institucional INACAP
│   │   │   │   │   └── ic_visibility_24.xml # Icono vectorial para ver contraseña
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml    # Layout del Login con ConstraintLayout
│   │   │   │   │   └── activity_bienvenida.xml # Layout de Bienvenida
│   │   │   │   └── values/
│   │   │   │       ├── colors.xml
│   │   │   │       ├── strings.xml
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
