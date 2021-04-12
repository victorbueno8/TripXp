package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.edu.ifsp.scl.sdm.tripxp.MainActivity
import br.edu.ifsp.scl.sdm.tripxp.R

class SplashScreenActivity : AppCompatActivity() {
    private var TIME_OUT : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_OUT)
    }
}