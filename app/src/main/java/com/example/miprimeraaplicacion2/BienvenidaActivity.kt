// Define el paquete al que pertenece esta Activity
package com.example.miprimeraaplicacion2

// Importa Intent para la navegación entre Activities
import android.content.Intent
// Importa Bundle para la gestión del ciclo de vida de la Activity
import android.os.Bundle
// Importa View para interactuar con eventos de clic en la interfaz
import android.view.View
// Importa ImageView para mostrar la foto de perfil
import android.widget.ImageView
// Importa TextView para mostrar la información del usuario
import android.widget.TextView
// Importa la función para habilitar el diseño de pantalla completa
import androidx.activity.enableEdgeToEdge
// Importa la clase base AppCompatActivity para compatibilidad
import androidx.appcompat.app.AppCompatActivity
// Importa ViewCompat para manejar la compatibilidad de vistas
import androidx.core.view.ViewCompat
// Importa WindowInsetsCompat para manejar las barras del sistema
import androidx.core.view.WindowInsetsCompat
// Importa Glide para cargar imágenes desde URLs en ImageView
import com.bumptech.glide.Glide
// Importaciones de Firebase Auth y Firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Declaración de la clase BienvenidaActivity (pantalla de Perfil)
class BienvenidaActivity : AppCompatActivity() {

    // Instancia de Firebase Auth
    private lateinit var auth: FirebaseAuth

    // Método onCreate: se ejecuta al crearse esta pantalla
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bienvenida)

        auth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Carga y muestra los datos del perfil del usuario
        cargarPerfilUsuario()
    }

    // Método para recuperar los datos de Firebase Auth y Firestore y rellenar la vista de Perfil
    private fun cargarPerfilUsuario() {
        val currentUser = auth.currentUser

        val imgPerfil = findViewById<ImageView>(R.id.imgPerfil)
        val txtNombre = findViewById<TextView>(R.id.txtNombre)
        val txtEmail = findViewById<TextView>(R.id.txtEmail)
        val txtProveedor = findViewById<TextView>(R.id.txtProveedor)
        val txtUid = findViewById<TextView>(R.id.txtUid)

        if (currentUser != null) {
            val uid = currentUser.uid
            val nombre = currentUser.displayName ?: currentUser.email?.substringBefore("@") ?: "Usuario"
            val email = currentUser.email ?: "Sin correo"
            val photoUrl = currentUser.photoUrl

            txtNombre.text = nombre
            txtEmail.text = email
            txtUid.text = "UID: $uid"

            // Determina el proveedor de autenticación
            val providerId = currentUser.providerData.lastOrNull()?.providerId ?: ""
            txtProveedor.text = if (providerId.contains("google")) {
                "Autenticado con Google"
            } else {
                "Autenticado con Correo/Contraseña"
            }

            // Carga la foto de perfil del usuario si está disponible
            if (photoUrl != null) {
                Glide.with(this)
                    .load(photoUrl)
                    .placeholder(R.drawable.logo_inacap)
                    .error(R.drawable.logo_inacap)
                    .into(imgPerfil)
            }

            // Consulta Firestore para confirmar los datos guardados en la nube
            val db = FirebaseFirestore.getInstance()
            db.collection("usuarios").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val proveedorFS = document.getString("proveedor")
                        if (!proveedorFS.isNullOrEmpty()) {
                            txtProveedor.text = "Proveedor: $proveedorFS"
                        }
                    }
                }
        }
    }

    // Método para ir a Preferencias
    fun onPreferenciasClick(view: View) {
        val intent = Intent(this, PreferenciasActivity::class.java)
        intent.putExtra("usuario", auth.currentUser?.email ?: "")
        startActivity(intent)
    }

    // Método para cerrar sesión
    fun onCerrarSesionClick(view: View) {
        auth.signOut()
        finish()
    }
}
