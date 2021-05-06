package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Company
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_organizer_profile.*

class EditOrganizerProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_organizer_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        companyConfirmBt.setOnClickListener {
            if (excursionCompanyNameET.text.toString().isNotEmpty() && permissionDocumentEt.text.toString().isNotEmpty() &&
                    companyCityEt.text.toString().isNotEmpty() && tripsTypesEt.text.toString().isNotEmpty()) {
                val company: Company = Company(
                        userID = auth.currentUser.uid,
                        name = excursionCompanyNameET.text.toString(),
                        document = permissionDocumentEt.text.toString(),
                        city = companyCityEt.text.toString(),
                        tripServices = tripsTypesEt.text.toString(),
                        description = companyDescriptionEt.text.toString()
                )
                db.collection("companies")
                    .add(company)
                    .addOnSuccessListener{
                        Toast.makeText(
                            this,
                            "Sua organização de viagens foi cadastrada!",
                            Toast.LENGTH_LONG
                        ).show()
                        val editProfilePage = Intent(this, EditProfileActivity::class.java)
                        editProfilePage.putExtra("name", intent.getStringExtra("name") ?: "")
                        editProfilePage.putExtra("email", intent.getStringExtra("email") ?: "")
                        editProfilePage.putExtra("userType", "organizer")
                        startActivity(editProfilePage)
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