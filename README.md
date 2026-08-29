# Mi Primera Aplicación 2 (INACAP) 📱

Proyecto de aplicación nativa Android desarrollado en **Kotlin** como parte de las actividades prácticas de INACAP.

---

## 📋 Bitácora de Desarrollo

### Fecha: 28 de Agosto, 2026

#### 1. 🔍 Exploración y Análisis del Proyecto
- Se realizó una lectura y comprensión de la arquitectura base del proyecto en `/Users/mike/MiPrimeraaplicacion2`.
- **Configuración técnica identificada:**
  - **Lenguaje**: Kotlin con Gradle (Kotlin DSL - `build.gradle.kts`).
  - **SDKs**: `compileSdk = 37`, `targetSdk = 37`, `minSdk = 24` (Android 7.0+).
  - **Arquitectura base**: `MainActivity.kt` (`AppCompatActivity`) con soporte Edge-to-Edge y `activity_main.xml` basado en `ConstraintLayout`.

#### 2. 🎨 Modificación y Diseño de la Interfaz (`activity_main.xml`)
- Se incorporó un nuevo elemento `ImageView` por encima del `TextView` existente.
- **Especificaciones aplicadas al logo:**
  - **Recurso de imagen**: `@drawable/logo_inacap`
  - **Dimensiones**: Ancho y alto de `96dp` (`android:layout_width="96dp"`, `android:layout_height="96dp"`).
  - **Posicionamiento**: Centrado horizontalmente en la pantalla (`app:layout_constraintStart_toStartOf="parent"`, `app:layout_constraintEnd_toEndOf="parent"`).
  - **Margen**: Margen superior de `32dp` con respecto a la parte superior (`android:layout_marginTop="32dp"`, `app:layout_constraintTop_toTopOf="parent"`).
  - **Accesibilidad**: Inclusión de `contentDescription="Logo Inacap"`.

#### 3. ⚙️ Configuración del Control de Versiones (Git)
- Se inicializó el repositorio Git local en la rama principal `main`.
- Se validaron los patrones de exclusión en `.gitignore` para evitar subir archivos temporales, caché de compilación (`.gradle`, `build/`) y credenciales locales (`local.properties`).
- Se empaquetaron y registraron los cambios en el commit inicial con el mensaje:
  ```bash
  feat: initial commit - app Android con logo INACAP
  ```

#### 4. 🚀 Publicación en GitHub
- Se configuró la autenticación con la cuenta de GitHub **`michaelarjelm`**.
- Se creó y publicó el repositorio con visibilidad **Pública**:
  - **URL del Repositorio**: [https://github.com/michaelarjelm/MiPrimeraaplicacion2](https://github.com/michaelarjelm/MiPrimeraaplicacion2)
  - **Rama**: `main`

---

## 📂 Estructura Principal del Repositorio

```text
MiPrimeraaplicacion2/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/miprimeraaplicacion2/
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   │   └── logo_inacap.png
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml
│   │   │   │   └── values/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🛠️ Tecnologías y Dependencias
- **Kotlin** & **Android Gradle Plugin**
- **AndroidX**: `appcompat`, `constraintlayout`, `core-ktx`, `activity-ktx`
- **Material Components for Android**
