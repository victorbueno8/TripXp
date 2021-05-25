package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.ManageEventsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val loginBt: Button = findViewById(R.id.loginBt)
        loginBt.setOnClickListener { view ->
            if (emailEt.text.trim().toString().isEmpty())
                Toast.makeText(this, "Preencha o campo email!", Toast.LENGTH_LONG).show()
            else if (passwordEt.text.trim().toString().isEmpty())
                Toast.makeText(this, "Preencha o campo de Senha", Toast.LENGTH_LONG).show()
            else {
                signInUser(emailEt.text.trim().toString(), passwordEt.text.trim().toString())
            }
        }

        val registerBt: Button = findViewById(R.id.registerBt)
        registerBt.setOnClickListener{ view ->
            // Botao Inicia activity de Registrar
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        redirectUser()
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener (this) { task ->
                    if (task.isSuccessful){
                        redirectUser()
                    } else {
                        Toast.makeText(this, "Erro:" + task.exception, Toast.LENGTH_LONG ).show()
                    }
                }
    }

    private fun redirectUser() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid)
                .get().addOnSuccessListener { document ->
                    val userType = document.get("userType")
                    if (userType == "organizer") {
                        startActivity(Intent(this, ManageEventsActivity::class.java))
                    } else {
                        startActivity(Intent(this, MyTripsActivity::class.java))
                    }

                } .addOnFailureListener { e ->
                    // Redirect to registration
                    Toast.makeText(this, "Perfil de usuario não encontrado", Toast.LENGTH_LONG ).show()
                }
        }
    }
}