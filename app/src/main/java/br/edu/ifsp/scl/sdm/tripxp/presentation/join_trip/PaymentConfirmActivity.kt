package br.edu.ifsp.scl.sdm.tripxp.presentation.join_trip

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Ticket
import br.edu.ifsp.scl.sdm.tripxp.presentation.EditProfileActivity
import br.edu.ifsp.scl.sdm.tripxp.use_cases.TripUseCases
import br.edu.ifsp.scl.sdm.tripxp.util.DateFormat
import br.edu.ifsp.scl.sdm.tripxp.util.NumberFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_payment_confirm.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class PaymentConfirmActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var  db: FirebaseFirestore = FirebaseFirestore.getInstance()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_confirm)

        val numFormat: NumberFormat = NumberFormat()

        val tripID = intent.getStringExtra("eventID") ?: ""
        TripUseCases().getTrip(tripID) {trip ->
            tripTitleTv.text = trip.name.uppercase(Locale.ROOT)
            ticketPriceTv.text = "R$ ${numFormat.format(trip.ticketPrice)}"
            db.collection("trips").document(tripID)
                .update("ticketQtd",trip.ticketQtd - ticketQtdTv.text.toString().toInt())
        }
        val qtd = intent.getIntExtra("qtd", 0)
        ticketQtdTv.text = qtd.toString()
        val acceptedTerms = intent.getBooleanExtra("acceptedTerms", true)

        paymentDateTv.text = DateFormat("dd/MM/yyyy").toString(Date())

        val total = intent.getDoubleExtra("total", 0.0)
        totalPaymentTv.text = "R$ ${numFormat.format(total)}"

        confirmPaymentBt.setOnClickListener { view ->
            val ticket = Ticket(
                userID = auth.currentUser!!.uid,
                tripID = tripID,
                tripName = tripTitleTv.text.toString(),
                unitPrice = numFormat.parse(ticketPriceTv.text.toString()),
                qtd = ticketQtdTv.text.toString().toInt(),
                paymentMethod = paymentMethodTv.text.toString(),
                total = numFormat.parse(totalPaymentTv.text.toString())
            )
            db.collection("trips").document(tripID).collection("tickets")
                .add(ticket)
                .addOnSuccessListener{
                    val finishPage = Intent(this, JoinTripConfirmActivity::class.java)
                    finishPage.putExtra("eventID", tripID)
                    finishPage.putExtra("ticketID", it.id)
                    startActivity(finishPage)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro: " + e.message, Toast.LENGTH_LONG).show()
                }
        }
    }
}