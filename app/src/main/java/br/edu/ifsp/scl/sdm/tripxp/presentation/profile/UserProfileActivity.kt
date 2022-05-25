package br.edu.ifsp.scl.sdm.tripxp.presentation.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.User
import br.edu.ifsp.scl.sdm.tripxp.util.CircleTransform
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  db: FirebaseFirestore
    private lateinit var userID: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userID = intent.getStringExtra("userID")!!

        val documentReference: DocumentReference = db.collection("users").document(userID)
        documentReference.get().addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(User::class.java)
            userNameTv.text = user?.fullname
            userCityTv.text = user?.city
            userEmailAddressTv.text = user?.email
            userPhoneNumberTv.text = user?.phone
            if(user != null && user.profileImageUri.isNotEmpty()) {
                Picasso.get().load(user.profileImageUri)
                    .resize(120,120).transform(CircleTransform())
                    .into(userImageIv)
            }
        }

        editProfileBt.setOnClickListener {
            val editProfilePage = Intent(this, EditProfileActivity::class.java)
            editProfilePage.putExtra("method", "edit")
            startActivity(editProfilePage)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}