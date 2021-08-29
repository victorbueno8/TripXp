package br.edu.ifsp.scl.sdm.tripxp.presentation.organizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Company
import br.edu.ifsp.scl.sdm.tripxp.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_organizer_profile.*

class OrganizerProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  db: FirebaseFirestore
    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizer_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userID = auth.currentUser!!.uid
        val companyId = intent.getStringExtra("companyID")

        if (companyId != null) {
            val documentReference: DocumentReference = db.collection("companies").document(companyId)
            documentReference.get().addOnSuccessListener { documentSnapshot ->
                val company = documentSnapshot.toObject(Company::class.java)
                organizerNameTv.text = company?.name
                organizerCityTv.text = company?.city
            }
        }
        val documentReference: DocumentReference = db.collection("users").document(userID)
        documentReference.get().addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(User::class.java)
            organizerPhoneNumberTv.text = user?.phone
            organizerEmailAddressTv.text = user?.email
        }
    }
}