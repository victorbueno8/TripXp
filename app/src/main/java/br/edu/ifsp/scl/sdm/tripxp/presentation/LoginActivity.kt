package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import br.edu.ifsp.scl.sdm.tripxp.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBt: Button = findViewById(R.id.loginBt)
        loginBt.setOnClickListener { view ->
            // Para activity lista de viagens do usuário/organizador
            startActivity(Intent(this, MyTripsActivity::class.java))
        }

        val registerBt: Button = findViewById(R.id.registerBt)
        registerBt.setOnClickListener{ view ->
            // Botao Inicia activity de Registrar
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }
}