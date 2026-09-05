// Define el paquete al que pertenece esta Activity
package com.example.miprimeraaplicacion2

// Importa Bundle para la gestión del ciclo de vida de la Activity
import android.os.Bundle
// Importa Handler para programar tareas asíncronas con retraso en el hilo principal
import android.os.Handler
// Importa Looper para obtener el bucle de mensajes del hilo de interfaz de usuario
import android.os.Looper
// Importa View para interactuar con los eventos visuales y visibilidad de vistas
import android.view.View
// Importa el componente ProgressBar para mostrar indicadores de carga
import android.widget.ProgressBar
// Importa el componente RadioButton para leer opciones de selección única
import android.widget.RadioButton
// Importa Toast para mostrar notificaciones flotantes en pantalla
import android.widget.Toast
// Importa la función para habilitar el diseño de pantalla completa (borde a borde)
import androidx.activity.enableEdgeToEdge
// Importa la clase base AppCompatActivity para compatibilidad
import androidx.appcompat.app.AppCompatActivity
// Importa ViewCompat para manejar la compatibilidad de vistas
import androidx.core.view.ViewCompat
// Importa WindowInsetsCompat para manejar las barras del sistema
import androidx.core.view.WindowInsetsCompat

// Declaración de la clase PreferenciasActivity (tercera pantalla de configuración)
class PreferenciasActivity : AppCompatActivity() {

    // Método onCreate: se ejecuta automáticamente al crearse esta pantalla
    override fun onCreate(savedInstanceState: Bundle?) {
        // Llama a la inicialización de la superclase
        super.onCreate(savedInstanceState)
        // Habilita diseño de borde a borde
        enableEdgeToEdge()
        // Asocia el diseño XML de activity_preferencias.xml con esta clase
        setContentView(R.layout.activity_preferencias)
        // Ajusta el padding de la vista raíz para respetar las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Obtiene las dimensiones de las barras de estado y navegación
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Aplica los márgenes como padding en los 4 bordes
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            // Retorna los insets aplicados
            insets
        }
    }

    // Método que se ejecuta al presionar el botón "Guardar" (Incremento 19)
    fun onGuardarPreferenciaClick(view: View) {
        // Busca y obtiene la referencia de la barra de progreso por su ID
        val pbGuardando = findViewById<ProgressBar>(R.id.pbGuardando)
        // Busca y obtiene la referencia del RadioButton de Celsius por su ID
        val rbCelsius = findViewById<RadioButton>(R.id.rbCelsius)

        // Hace visible la barra de progreso en la pantalla
        pbGuardando.visibility = View.VISIBLE

        // Programa una ejecución diferida de 1000 milisegundos (1 segundo) sin congelar la interfaz
        Handler(Looper.getMainLooper()).postDelayed({
            // Oculta nuevamente la barra de progreso al finalizar la simulación
            pbGuardando.visibility = View.GONE
            // Determina la unidad de temperatura según cuál RadioButton está marcado
            val unidad = if (rbCelsius.isChecked) "Celsius" else "Fahrenheit"
            // Muestra un Toast confirmando el guardado de la preferencia
            Toast.makeText(this, "Guardado: temperatura en $unidad", Toast.LENGTH_SHORT).show()
        }, 1000)
    }

    // Método que se ejecuta al presionar el botón "Cerrar sesión" (Incremento 20)
    fun onCerrarSesionClick(view: View) {
        // Cierra todas las actividades activas en la pila (Preferencias y Bienvenida) y regresa al Login
        finishAffinity()
    }
}
