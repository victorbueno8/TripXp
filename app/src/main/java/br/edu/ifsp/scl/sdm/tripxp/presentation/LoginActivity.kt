package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.edu.ifsp.scl.sdm.tripxp.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.registerBt -> {
                // Botao Inicia activity de Registrar
                startActivity(Intent(this, RegistrationActivity::class.java))
            }
        }
    }
}