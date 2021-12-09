package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        val darseDeAlta: Button = findViewById(R.id.Loguearse)
        darseDeAlta.setOnClickListener{
            val usuario: EditText = findViewById(R.id.correo)
            val contraseña: EditText = findViewById(R.id.contraseña)
            crearCuenta(usuario.text.toString(),contraseña.text.toString())
        }

        val accederCuenta: Button = findViewById(R.id.Iniciarsesion)
        accederCuenta.setOnClickListener{
            val usuario: EditText = findViewById(R.id.correo)
            val contraseña: EditText = findViewById(R.id.contraseña)
            accederCuenta(usuario.text.toString(),contraseña.text.toString())
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    /**
     * @param email Correo del usuario
     * @param password Contraseña del usuario
     */
    private fun crearCuenta(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.i("bien", "Crear Usuario Bien")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.i("mal", "Crear Usuario Mal", task.exception)
                    Toast.makeText(baseContext, "Fallo en autenticación.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    /**
     * @param email Correo del usuario
     * @param password Contraseña del usuario
     */

    private fun accederCuenta (email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("bien", "Acceso correcto")
                    val user = auth.currentUser
                    updateUI(user)
                    Toast.makeText(baseContext, "Bien.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.w("mal", "Acceso fallido", task.exception)
                    Toast.makeText(baseContext, "Fallo en la autenticacion",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun reload() {

    }


}