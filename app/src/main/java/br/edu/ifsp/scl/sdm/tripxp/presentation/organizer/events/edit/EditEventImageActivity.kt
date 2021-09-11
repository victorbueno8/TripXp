package br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.events.edit

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import br.edu.ifsp.scl.sdm.tripxp.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_event_image.*
import kotlinx.android.synthetic.main.activity_edit_event_terms.*
import java.util.*

class EditEventImageActivity : AppCompatActivity() {
    private lateinit var eventID: String
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var  db: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event_image)

        eventID = intent.getStringExtra("eventID") ?: ""

        eventImagePreviewIv.visibility = View.GONE
        selectImageBt.setOnClickListener { view ->
            selectImage()
        }

        imageEditBtn.setOnClickListener { view ->
            imageEditBtn.isEnabled = false
            uploadImage(view)
        }
        imageEditBtn.isEnabled = false
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            eventImagePreviewIv.visibility = View.VISIBLE
            imageUri = data?.data!!
            Picasso.get().load(imageUri).into(eventImagePreviewIv)
            imageEditBtn.isEnabled = true
        }
    }

    private fun uploadImage(view: View) {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("events/images/$filename")

        ref.putFile(imageUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    val documentReference: DocumentReference = db.collection("trips").document(eventID)
                    documentReference.update("eventImageUri", uri.toString())
                        .addOnSuccessListener {
                            Snackbar.make(view, "A imagem da excursão foi salva!", Snackbar.LENGTH_LONG).show()
                            if (intent.getStringExtra("method")  == "patch") {
                                finish()
                            } else {
                                val termsPage = Intent(this, EditEventTermsActivity::class.java)
                                termsPage.putExtra("eventID", eventID)
                                startActivity(termsPage)
                            }
                        }
                        .addOnFailureListener{ e ->
                            Snackbar.make(view, "Erro: " + e.message, Snackbar.LENGTH_LONG).show()
                        }
                }
            }
    }

}