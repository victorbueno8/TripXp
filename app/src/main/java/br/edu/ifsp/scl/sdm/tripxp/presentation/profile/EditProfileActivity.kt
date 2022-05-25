package br.edu.ifsp.scl.sdm.tripxp.presentation.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.User
import br.edu.ifsp.scl.sdm.tripxp.presentation.LoginActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.SplashScreenActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.event.EventActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.MyTripsActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.EditOrganizerProfileActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.ManageEventsActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.edit.EditEventTermsActivity
import br.edu.ifsp.scl.sdm.tripxp.util.CircleTransform
import br.edu.ifsp.scl.sdm.tripxp.util.DateMask
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  db: FirebaseFirestore

    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(intent.getStringExtra("method") == "edit")

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setupFields()

        confirmProfileBt.setOnClickListener { view ->
            if (fullNameEt.text.toString().isNotEmpty() && birthdayEt.text.toString().isNotEmpty() && cityNameEt.text.toString().isNotEmpty() &&
                    cepEt.text.toString().isNotEmpty() && phoneContactEt.text.toString().isNotEmpty()) {
                        val userID: String = auth.currentUser!!.uid
                        val documentReference: DocumentReference = db.collection("users").document(userID)
                        documentReference.get().addOnSuccessListener { snap ->
                            val user = if(intent.getStringExtra("method") == "edit") {
                                User(
                                    name = snap.getString("name")!!,
                                    email = snap.getString("email")!!,
                                    fullname = fullNameEt.text.toString(),
                                    birthday = birthdayEt.text.toString(),
                                    city = cityNameEt.text.toString(),
                                    cep = cepEt.text.toString(),
                                    phone = phoneContactEt.text.toString(),
                                    phone2 = phoneContact2Et.text.toString(),
                                    profileImageUri = snap.getString("profileImageUri")!!,
                                    userType = snap.getString("userType")!!,
                                )
                            } else {
                                User(
                                    name = intent.getStringExtra("name")!!,
                                    email = intent.getStringExtra("email")!!,
                                    fullname = fullNameEt.text.toString(),
                                    birthday = birthdayEt.text.toString(),
                                    city = cityNameEt.text.toString(),
                                    cep = cepEt.text.toString(),
                                    phone = phoneContactEt.text.toString(),
                                    phone2 = phoneContact2Et.text.toString(),
                                    profileImageUri = "",
                                    userType = intent.getStringExtra("userType")!!
                                )
                            }
                            documentReference.set(user)
                                .addOnSuccessListener {
                                    if(imageUri != null) {
                                        uploadImage(view)
                                    } else {
                                        Snackbar.make(view, "Seu perfil foi salvo!", Snackbar.LENGTH_LONG).show()
                                        routeToMainUser()
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Erro: " + e.message, Toast.LENGTH_LONG).show()
                                }
                        }
            } else {
                Toast.makeText(this, "Preencha os campos obrigatórios acima!", Toast.LENGTH_LONG).show()
            }
        }

        selectAvatarBt.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
        }
    }

    fun setupFields() {
        birthdayEt.addTextChangedListener(DateMask.insert("##/##/####", birthdayEt))

        if(intent.getStringExtra("method") == "edit" && auth.currentUser != null) {
            val userID: String = auth.currentUser!!.uid
            val documentReference: DocumentReference = db.collection("users").document(userID)
            documentReference.get().addOnSuccessListener { snap ->
                fullNameEt.setText(snap.getString("fullname") ?: "")
                birthdayEt.setText(snap.getString("birthday") ?: "")
                cityNameEt.setText(snap.getString("city") ?: "")
                cepEt.setText(snap.getString("cep") ?: "")
                phoneContactEt.setText(snap.getString("phone") ?: "")
                phoneContact2Et.setText(snap.getString("phone2") ?: "")
                if(!snap.getString("profileImageUri").isNullOrEmpty()) {
                    Picasso.get().load(snap.getString("profileImageUri"))
                        .resize(120,120).transform(CircleTransform())
                        .into(selectAvatarBt)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            Picasso.get().load(imageUri)
                .resize(120,120).transform(CircleTransform())
                .into(selectAvatarBt)
        }
    }

    private fun uploadImage(view: View) {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("profile/images/$filename")

        ref.putFile(imageUri!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    val documentReference: DocumentReference = db.collection("users").document(auth.currentUser!!.uid)
                    documentReference.update("profileImageUri", uri.toString())
                        .addOnSuccessListener {
                            Snackbar.make(view, "Seu perfil foi salvo!", Snackbar.LENGTH_LONG).show()
                            routeToMainUser()
                        }
                        .addOnFailureListener{ e ->
                            Snackbar.make(view, "Erro: " + e.message, Snackbar.LENGTH_LONG).show()
                        }
                }
            }
    }

    private fun routeToMainUser() {
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid)
                .get().addOnSuccessListener { document ->
                    val userType = document.get("userType")
                    val intent = if (userType == "organizer") {
                        Intent(this, ManageEventsActivity::class.java)
                    } else {
                        Intent(this, MyTripsActivity::class.java)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)

                } .addOnFailureListener { e ->
                    // Redirect to registration
                    Toast.makeText(this, "Perfil de usuario não encontrado", Toast.LENGTH_LONG ).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}