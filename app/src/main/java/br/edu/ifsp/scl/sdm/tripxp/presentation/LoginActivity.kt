package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import br.edu.ifsp.scl.sdm.tripxp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

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

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener (this) { task ->
                    if (task.isSuccessful){
                        startActivity(Intent(this, MyTripsActivity::class.java))
                    } else {
                        Toast.makeText(this, "Erro:" + task.exception, Toast.LENGTH_LONG ).show()
                    }
                }
    }
}