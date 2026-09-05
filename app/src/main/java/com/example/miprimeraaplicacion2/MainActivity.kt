// Define el paquete al que pertenece este archivo dentro de la estructura de la app
package com.example.miprimeraaplicacion2

// Importa la clase Intent para la navegación y comunicación entre Activities (pantallas)
import android.content.Intent
// Importa Bundle para manejar el estado guardado de la Activity en el ciclo de vida
import android.os.Bundle
// Importa la clase View para interactuar con los elementos visuales de la interfaz
import android.view.View
// Importa el componente visual CheckBox (casilla de verificación)
import android.widget.CheckBox
// Importa el componente visual EditText (campo de entrada de texto)
import android.widget.EditText
// Importa Toast para mostrar notificaciones flotantes temporales en pantalla
import android.widget.Toast
// Importa InputType para modificar dinámicamente el tipo de entrada de texto del EditText
import android.text.InputType
// Importa Patterns para validar patrones estándar de texto como correos electrónicos
import android.util.Patterns
// Importa la función para habilitar el diseño de pantalla completa (borde a borde)
import androidx.activity.enableEdgeToEdge
// Importa la clase base AppCompatActivity para compatibilidad con versiones anteriores de Android
import androidx.appcompat.app.AppCompatActivity
// Importa ViewCompat para manejar eventos y compatibilidad de vistas
import androidx.core.view.ViewCompat
// Importa WindowInsetsCompat para obtener las dimensiones de las barras del sistema (estado, navegación)
import androidx.core.view.WindowInsetsCompat

// Declaración de la clase MainActivity que hereda de AppCompatActivity (pantalla principal de login)
class MainActivity : AppCompatActivity() {

    // Variable booleana a nivel de clase para rastrear si la contraseña está visible u oculta (inicialmente false)
    private var mostrandoPassword: Boolean = false
    // Variable entera a nivel de clase para contar los intentos fallidos de inicio de sesión (inicialmente 0)
    private var intentosFallidos: Int = 0

    // Método onCreate: se ejecuta automáticamente al iniciar/crear la Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        // Llama a la implementación de la clase padre para inicializar la Activity
        super.onCreate(savedInstanceState)
        // Habilita que la interfaz se extienda de borde a borde de la pantalla
        enableEdgeToEdge()
        // Asocia el archivo de diseño XML (activity_main.xml) con esta clase Kotlin
        setContentView(R.layout.activity_main)
        // Ajusta los márgenes/padding de la vista principal para no solaparse con las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Obtiene las dimensiones de las barras del sistema (barra de estado superior y navegación inferior)
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Aplica el relleno (padding) a la vista raíz según los márgenes del sistema
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            // Retorna los insets procesados
            insets
        }
    }

    // Método que se ejecuta al presionar el ImageButton para mostrar u ocultar la contraseña (vinculado por android:onClick)
    fun onMostrarPasswordClick(view: View) {
        // Busca y obtiene la referencia del campo EditText de contraseña por su ID
        val edtPassword = findViewById<EditText>(R.id.edtPassword)

        // Evalúa si la contraseña actualmente está oculta (mostrandoPassword es false)
        if (!mostrandoPassword) {
            // Cambia el inputType a texto visible para que la contraseña sea legible
            edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            // Actualiza el estado de la variable a true (indicando que ahora está visible)
            mostrandoPassword = true
        } else {
            // Cambia el inputType de vuelta a password para enmascarar los caracteres
            edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            // Actualiza el estado de la variable a false (indicando que ahora está oculta)
            mostrandoPassword = false
        }
        // Mantiene la posición del cursor al final del texto ingresado
        edtPassword.setSelection(edtPassword.text.length)
    }

    // Método que se ejecuta al presionar el botón "Ingresar" (vinculado por android:onClick)
    fun onIngresarClick(view: View) {
        // Busca y obtiene la referencia del campo EditText de usuario por su ID
        val edtUsuario = findViewById<EditText>(R.id.edtUsuario)
        // Busca y obtiene la referencia del campo EditText de contraseña por su ID
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        // Busca y obtiene la referencia del CheckBox "Recordarme" por su ID
        val chkRecordarme = findViewById<CheckBox>(R.id.chkRecordarme)

        // Extrae el texto ingresado en el campo de usuario y elimina espacios en los extremos
        val usuario = edtUsuario.text.toString().trim()
        // Extrae el texto ingresado en el campo de contraseña
        val password = edtPassword.text.toString()
        // Obtiene el estado booleano del CheckBox (true si está marcado, false si no)
        val recordar = chkRecordarme.isChecked

        // Limpia los mensajes de error previos en el campo de usuario
        edtUsuario.error = null
        // Limpia los mensajes de error previos en el campo de contraseña
        edtPassword.error = null

        // Bandera booleana para determinar si hubo algún error en la validación
        var hayError = false

        // Valida si el campo de usuario está vacío
        if (usuario.isEmpty()) {
            // Asigna un mensaje de error directo en el campo de usuario
            edtUsuario.error = "El correo electrónico es requerido"
            // Marca la bandera de error como verdadera
            hayError = true
        // Valida si el texto no cumple con el formato estándar de correo electrónico
        } else if (!Patterns.EMAIL_ADDRESS.matcher(usuario).matches()) {
            // Asigna un mensaje de error directo indicando formato inválido
            edtUsuario.error = "Ingresa un correo electrónico válido"
            // Marca la bandera de error como verdadera
            hayError = true
        }

        // Valida si el campo de contraseña está vacío
        if (password.isEmpty()) {
            // Asigna un mensaje de error directo en el campo de contraseña
            edtPassword.error = "La contraseña es requerida"
            // Marca la bandera de error como verdadera
            hayError = true
        // Valida si la contraseña tiene menos de 6 caracteres
        } else if (password.length < 6) {
            // Asigna un mensaje de error directo indicando la longitud mínima requerida
            edtPassword.error = "La contraseña debe tener al menos 6 caracteres"
            // Marca la bandera de error como verdadera
            hayError = true
        }

        // Evalúa si alguna de las validaciones falló
        if (hayError) {
            // Incrementa en 1 el contador de intentos fallidos
            intentosFallidos++
            // Muestra un mensaje emergente (Toast) indicando el número de intento fallido actual
            Toast.makeText(this, "Completa usuario y contraseña (intento $intentosFallidos)", Toast.LENGTH_SHORT).show()
        } else {
            // Si todas las validaciones son correctas, crea un Intent hacia BienvenidaActivity
            val intent = Intent(this, BienvenidaActivity::class.java)
            // Adjunta el correo/usuario como dato extra al Intent con la clave "usuario"
            intent.putExtra("usuario", usuario)
            // Inicia la nueva pantalla de bienvenida
            startActivity(intent)
        }
    }

    // Método que se ejecuta al presionar el botón "Limpiar" (vinculado por android:onClick)
    fun onLimpiarClick(view: View) {
        // Busca y obtiene la referencia del campo EditText de usuario
        val edtUsuario = findViewById<EditText>(R.id.edtUsuario)
        // Busca y obtiene la referencia del campo EditText de contraseña
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        // Busca y obtiene la referencia del CheckBox "Recordarme"
        val chkRecordarme = findViewById<CheckBox>(R.id.chkRecordarme)

        // Limpia el texto del campo de usuario dejándolo vacío
        edtUsuario.setText("")
        // Limpia el texto del campo de contraseña dejándolo vacío
        edtPassword.setText("")
        // Quita cualquier mensaje de error visible en el campo de usuario
        edtUsuario.error = null
        // Quita cualquier mensaje de error visible en el campo de contraseña
        edtPassword.error = null
        // Desmarca la casilla del CheckBox
        chkRecordarme.isChecked = false
        // Reinicia el contador de intentos fallidos a 0
        intentosFallidos = 0
    }
}