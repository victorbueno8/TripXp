package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.edu.ifsp.scl.sdm.tripxp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()

        continuarBt.setOnClickListener {
            if(registerNameEt.text.trim().toString().isNotEmpty() && registerEmailEt.text.trim().toString().isNotEmpty() &&
                    registerSenhaEt.text.trim().toString().isNotEmpty() && registerConfirmSenhaEt.text.trim().toString().isNotEmpty()) {
                        registerUser(
                            registerNameEt.text.trim().toString(),
                            registerEmailEt.text.trim().toString(),
                            registerSenhaEt.text.trim().toString(),
                            registerConfirmSenhaEt.text.trim().toString(),
                            "user"
                        )
            } else {
                Toast.makeText(this, "Preencha os campos acima!", Toast.LENGTH_LONG).show()
            }
        }
        registerOrganizadorBt.setOnClickListener {
            if(registerNameEt.text.trim().toString().isNotEmpty() && registerEmailEt.text.trim().toString().isNotEmpty() &&
                registerSenhaEt.text.trim().toString().isNotEmpty() && registerConfirmSenhaEt.text.trim().toString().isNotEmpty()) {
                registerUser(
                    registerNameEt.text.trim().toString(),
                    registerEmailEt.text.trim().toString(),
                    registerSenhaEt.text.trim().toString(),
                    registerConfirmSenhaEt.text.trim().toString(),
                    "organizer"
                )
            } else {
                Toast.makeText(this, "Preencha os campos acima!", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser

        if (user != null) {
            startActivity(Intent(this, MyTripsActivity::class.java))
        }

    }

    private fun registerUser(name: String, email: String, password: String, passwordConfirmation: String, userType: String) {
        if(password == passwordConfirmation) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful) {
                            Toast.makeText(this, "Conta Criada!", Toast.LENGTH_LONG).show()
                            if (userType == "user") {
                                val userProfilePage: Intent = Intent(this, EditProfileActivity::class.java)
                                userProfilePage.putExtra("name", name)
                                userProfilePage.putExtra("email", email)
                                userProfilePage.putExtra("userType", userType)
                                startActivity(userProfilePage)
                            } else {
                                val organizerProfilePage: Intent = Intent(this, EditOrganizerProfileActivity::class.java)
                                organizerProfilePage.putExtra("name", name)
                                organizerProfilePage.putExtra("email", email)
                                organizerProfilePage.putExtra("userType", userType)
                                startActivity(organizerProfilePage)
                            }
                        } else {
                            Toast.makeText(this, "Erro ao criar conta: " + task.exception, Toast.LENGTH_LONG).show()
                        }
                    }
        } else {
            Toast.makeText(this, "Confirmação da senha errada!", Toast.LENGTH_LONG).show()
        }
    }
}