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
// Importa ActivityResultContracts para registrar lanzadores de resultados de Activity
import androidx.activity.result.contract.ActivityResultContracts
// Importa la clase base AppCompatActivity para compatibilidad con versiones anteriores de Android
import androidx.appcompat.app.AppCompatActivity
// Importa ViewCompat para manejar eventos y compatibilidad de vistas
import androidx.core.view.ViewCompat
// Importa WindowInsetsCompat para obtener las dimensiones de las barras del sistema
import androidx.core.view.WindowInsetsCompat
// Importaciones de Google Play Services Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
// Importaciones de Firebase Auth y Firestore
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

// Declaración de la clase MainActivity que hereda de AppCompatActivity
class MainActivity : AppCompatActivity() {

    // Variable booleana a nivel de clase para rastrear si la contraseña está visible u oculta
    private var mostrandoPassword: Boolean = false
    // Variable entera a nivel de clase para contar los intentos fallidos de inicio de sesión
    private var intentosFallidos: Int = 0
    // Objeto de Firebase Auth para gestionar la autenticación
    private lateinit var auth: FirebaseAuth
    // Cliente de Google Sign-In
    private lateinit var googleSignInClient: GoogleSignInClient

    // Lanzador para manejar el resultado de la ventana de selección de cuenta de Google
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { idToken ->
                firebaseAuthWithGoogle(idToken)
            } ?: run {
                Toast.makeText(this, "No se pudo obtener el token de Google", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Toast.makeText(this, "Error inicio sesión Google: ${e.message} (Código ${e.statusCode})", Toast.LENGTH_LONG).show()
        }
    }

    // Método onCreate: se ejecuta automáticamente al iniciar/crear la Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializa la instancia de Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configuración de las opciones para Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Método que se ejecuta al presionar el botón "Continuar con Google"
    fun onGoogleSignInClick(view: View) {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    // Método para autenticar con Firebase utilizando el token de ID de Google
    private fun firebaseAuthWithGoogle(idToken: String) {
        Toast.makeText(this, "Autenticando con Google...", Toast.LENGTH_SHORT).show()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        guardarUsuarioEnFirestore(user, "Google") {
                            navegarABienvenida()
                        }
                    } else {
                        navegarABienvenida()
                    }
                } else {
                    val mensajeError = task.exception?.localizedMessage ?: "Error de autenticación con Google"
                    Toast.makeText(this, "Error Firebase: $mensajeError", Toast.LENGTH_LONG).show()
                }
            }
    }

    // Guarda o actualiza la información del usuario en Cloud Firestore
    private fun guardarUsuarioEnFirestore(user: FirebaseUser, proveedor: String, onComplete: () -> Unit) {
        val db = FirebaseFirestore.getInstance()

        val datosUsuario = hashMapOf(
            "uid" to user.uid,
            "nombre" to (user.displayName ?: user.email?.substringBefore("@") ?: "Usuario"),
            "email" to (user.email ?: ""),
            "fotoUrl" to (user.photoUrl?.toString() ?: ""),
            "proveedor" to proveedor,
            "fechaUltimoAcceso" to Timestamp.now()
        )

        // Enviamos la petición a Firestore (funciona incluso offline)
        db.collection("usuarios").document(user.uid)
            .set(datosUsuario, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity, "Usuario registrado en Firestore exitosamente.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@MainActivity, "Error al guardar en Firestore: ${e.message}", Toast.LENGTH_LONG).show()
            }

        // No esperamos a que el servidor confirme para avanzar,
        // permitimos que el usuario entre a la app inmediatamente.
        onComplete()
    }

    // Navega a la pantalla de perfil / bienvenida
    private fun navegarABienvenida() {
        val intent = Intent(this, BienvenidaActivity::class.java)
        startActivity(intent)
    }

    // Método que se ejecuta al presionar el ImageButton para mostrar u ocultar la contraseña
    fun onMostrarPasswordClick(view: View) {
        val edtPassword = findViewById<EditText>(R.id.edtPassword)

        if (!mostrandoPassword) {
            edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            mostrandoPassword = true
        } else {
            edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            mostrandoPassword = false
        }
        edtPassword.setSelection(edtPassword.text.length)
    }

    // Método que se ejecuta al presionar el botón "Ingresar"
    fun onIngresarClick(view: View) {
        val edtUsuario = findViewById<EditText>(R.id.edtUsuario)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val chkRecordarme = findViewById<CheckBox>(R.id.chkRecordarme)

        val usuario = edtUsuario.text.toString().trim()
        val password = edtPassword.text.toString()

        edtUsuario.error = null
        edtPassword.error = null

        var hayError = false

        if (usuario.isEmpty()) {
            edtUsuario.error = "El correo electrónico es requerido"
            hayError = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(usuario).matches()) {
            edtUsuario.error = "Ingresa un correo electrónico válido"
            hayError = true
        }

        if (password.isEmpty()) {
            edtPassword.error = "La contraseña es requerida"
            hayError = true
        } else if (password.length < 6) {
            edtPassword.error = "La contraseña debe tener al menos 6 caracteres"
            hayError = true
        }

        if (hayError) {
            intentosFallidos++
            Toast.makeText(this, "Completa usuario y contraseña (intento $intentosFallidos)", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Autenticando...", Toast.LENGTH_SHORT).show()
            auth.signInWithEmailAndPassword(usuario, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            guardarUsuarioEnFirestore(user, "Correo/Contraseña") {
                                navegarABienvenida()
                            }
                        } else {
                            navegarABienvenida()
                        }
                    } else {
                        intentosFallidos++
                        val mensajeError = task.exception?.localizedMessage ?: "Error de autenticación"
                        Toast.makeText(this, "Falló inicio de sesión: $mensajeError (intento $intentosFallidos)", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    // Método que se ejecuta al presionar el botón "Limpiar"
    fun onLimpiarClick(view: View) {
        val edtUsuario = findViewById<EditText>(R.id.edtUsuario)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val chkRecordarme = findViewById<CheckBox>(R.id.chkRecordarme)

        edtUsuario.setText("")
        edtPassword.setText("")
        edtUsuario.error = null
        edtPassword.error = null
        chkRecordarme.isChecked = false
        intentosFallidos = 0
    }
}