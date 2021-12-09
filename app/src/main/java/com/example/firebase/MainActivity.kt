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

        //Iniciar FireBase

        auth = Firebase.auth

        //Creo el boton para darse de alta

        val darseDeAlta: Button = findViewById(R.id.Loguearse)
        darseDeAlta.setOnClickListener{

            //Declaro la variable de usuario para recojer el texto

            val usuario: EditText = findViewById(R.id.correo)

            //Declaro la variable de contraseña para recojer el texto

            val contraseña: EditText = findViewById(R.id.contraseña)

            //Llamo al metodo donde se crear cuenta y le paso de variables del correo y la contraseña

            crearCuenta(usuario.text.toString(),contraseña.text.toString())

        }
        //creo el boton para darse de alta

        val accederCuenta: Button = findViewById(R.id.Iniciarsesion)
        accederCuenta.setOnClickListener{

            //Declaro la variable de usuario para recojer el texto

            val usuario: EditText = findViewById(R.id.correo)

            //Declaro la variable de contraseña para recojer el texto

            val contraseña: EditText = findViewById(R.id.contraseña)

            //Llamo al metodo donde se  accede a la cuenta y le paso de variables el correo y la contraseña

            accederCuenta(usuario.text.toString(),contraseña.text.toString())
        }
    }
    public override fun onStart() {
        super.onStart()

        //Compruebe si el usuario ha iniciado sesión y que actualice la interfaz de usuario

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

        //Crear usuario

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Iniciar sesión correctamente
                    Log.i("bien", "Crear Usuario Bien")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // Mensaje de Error
                    Log.i("mal", "Crear Usuario Mal", task.exception)
                    Toast.makeText(baseContext, "Fallo en autenticación.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [Fin Crear Cuenta]
    }
    /**
     * @param email Correo del usuario
     * @param password Contraseña del usuario
     */

    private fun accederCuenta (email: String, password: String) {
        // acceder a cuenta
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Iniciar sesión correctament

                    Log.d("bien", "Acceso correcto")
                    val user = auth.currentUser
                    updateUI(user)
                    Toast.makeText(baseContext, "Bien.",
                        Toast.LENGTH_SHORT).show()
                } else {

                    // Mensaje de Error

                    Log.w("mal", "Acceso fallido", task.exception)
                    Toast.makeText(baseContext, "Fallo en la autenticacion",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [Fin de acceso a cuenta]
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun reload() {

    }


}