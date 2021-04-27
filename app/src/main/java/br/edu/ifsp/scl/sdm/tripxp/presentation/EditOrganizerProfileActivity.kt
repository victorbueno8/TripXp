package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.edu.ifsp.scl.sdm.tripxp.R
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
                val companyData: HashMap<String, Any> = HashMap()
                companyData.put("userID", auth.currentUser.uid)
                companyData.put("name", excursionCompanyNameET.text.toString())
                companyData.put("document", permissionDocumentEt.text.toString())
                companyData.put("city", companyCityEt.text.toString())
                companyData.put("tripServices", tripsTypesEt.text.toString())
                companyData.put("description", companyDescriptionEt.text.toString())
                db.collection("companies")
                    .add(companyData)
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