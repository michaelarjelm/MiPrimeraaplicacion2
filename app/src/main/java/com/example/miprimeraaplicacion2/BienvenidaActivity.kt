// Define el paquete al que pertenece esta Activity
package com.example.miprimeraaplicacion2

// Importa Bundle para la gestión del ciclo de vida de la Activity
import android.os.Bundle
// Importa el componente visual TextView para mostrar textos en pantalla
import android.widget.TextView
// Importa la función para habilitar el diseño de pantalla completa (borde a borde)
import androidx.activity.enableEdgeToEdge
// Importa la clase base AppCompatActivity para compatibilidad
import androidx.appcompat.app.AppCompatActivity
// Importa ViewCompat para manejar la compatibilidad de vistas
import androidx.core.view.ViewCompat
// Importa WindowInsetsCompat para manejar las barras del sistema
import androidx.core.view.WindowInsetsCompat

// Declaración de la clase BienvenidaActivity (segunda pantalla)
class BienvenidaActivity : AppCompatActivity() {
    // Método onCreate: se ejecuta al crearse esta pantalla
    override fun onCreate(savedInstanceState: Bundle?) {
        // Llama al constructor de la superclase
        super.onCreate(savedInstanceState)
        // Habilita diseño de borde a borde
        enableEdgeToEdge()
        // Asocia la interfaz definida en activity_bienvenida.xml
        setContentView(R.layout.activity_bienvenida)
        // Aplica márgenes para evitar solapamiento con la barra de estado y de navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Obtiene las dimensiones de las barras del sistema
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Aplica padding a la vista
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            // Retorna los insets
            insets
        }

        // Recupera el dato de texto enviado desde MainActivity con la clave "usuario"
        val usuario = intent.getStringExtra("usuario")
        // Busca y obtiene la referencia del TextView central por su ID
        val txtBienvenida = findViewById<TextView>(R.id.txtBienvenida)
        // Asigna el texto personalizado mostrando el saludo con el nombre de usuario recibido
        txtBienvenida.text = "Bienvenido, $usuario"
    }
}
