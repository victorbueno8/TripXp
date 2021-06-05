package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.User
import br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.MyTripsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        confirmProfileBt.setOnClickListener {
            if (fullNameEt.text.toString().isNotEmpty() && birthdayEt.text.toString().isNotEmpty() && cityNameEt.text.toString().isNotEmpty() &&
                    cepEt.text.toString().isNotEmpty() && phoneContactEt.text.toString().isNotEmpty()) {
                        val userID: String = auth.currentUser.uid
                        val documentReference: DocumentReference = db.collection("users").document(userID)
                        val user: User = User(
                                name = intent.getStringExtra("name") ?: "",
                                email = intent.getStringExtra("email") ?: "",
                                fullname = fullNameEt.text.toString(),
                                birthday = birthdayEt.text.toString(),
                                city = cityNameEt.text.toString(),
                                cep = cepEt.text.toString(),
                                phone = phoneContactEt.text.toString(),
                                phone2 = phoneContact2Et.text.toString(),
                                userType = intent.getStringExtra("userType") ?: "user"
                        )
                        documentReference.set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                            this,
                                            "Perfil do usuario foi salvo!",
                                            Toast.LENGTH_LONG
                                    ).show()
                                    val mainUserPage = Intent(this, MyTripsActivity::class.java)
                                    startActivity(mainUserPage)
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Erro: " + e.message, Toast.LENGTH_LONG).show()
                                }


            } else {
                Toast.makeText(this, "Preencha os campos obrigatórios acima!", Toast.LENGTH_LONG).show()
            }
        }
    }
}