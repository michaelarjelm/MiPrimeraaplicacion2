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

    // Método que se ejecuta al presionar el botón "Ingresar" (vinculado por android:onClick)
    fun onIngresarClick(view: View) {
        // Busca y obtiene la referencia del campo EditText de usuario por su ID
        val edtUsuario = findViewById<EditText>(R.id.edtUsuario)
        // Busca y obtiene la referencia del campo EditText de contraseña por su ID
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        // Busca y obtiene la referencia del CheckBox "Recordarme" por su ID
        val chkRecordarme = findViewById<CheckBox>(R.id.chkRecordarme)

        // Extrae el texto ingresado en el campo de usuario y lo convierte a String
        val usuario = edtUsuario.text.toString()
        // Extrae el texto ingresado en el campo de contraseña y lo convierte a String
        val password = edtPassword.text.toString()
        // Obtiene el estado booleano del CheckBox (true si está marcado, false si no)
        val recordar = chkRecordarme.isChecked

        // Valida si el campo de usuario O el campo de contraseña están vacíos
        if (usuario.isEmpty() || password.isEmpty()) {
            // Muestra un mensaje emergente (Toast) pidiendo completar ambos campos
            Toast.makeText(this, "Completa usuario y contraseña", Toast.LENGTH_SHORT).show()
        } else {
            // Si los campos tienen datos, crea un Intent para navegar desde MainActivity hacia BienvenidaActivity
            val intent = Intent(this, BienvenidaActivity::class.java)
            // Adjunta el nombre de usuario como dato extra al Intent usando la clave "usuario"
            intent.putExtra("usuario", usuario)
            // Inicia la nueva Activity (abre la pantalla de Bienvenida)
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
        // Desmarca la casilla del CheckBox
        chkRecordarme.isChecked = false
    }
}