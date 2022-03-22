package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import br.edu.ifsp.scl.sdm.tripxp.MainActivity
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.MyTripsActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.ManageEventsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var TIME_OUT : Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        Handler().postDelayed({
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
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }, TIME_OUT)
    }
}