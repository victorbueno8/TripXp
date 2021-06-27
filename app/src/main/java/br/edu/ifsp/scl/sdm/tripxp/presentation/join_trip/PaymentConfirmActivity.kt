package br.edu.ifsp.scl.sdm.tripxp.presentation.join_trip

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Ticket
import br.edu.ifsp.scl.sdm.tripxp.presentation.EditProfileActivity
import br.edu.ifsp.scl.sdm.tripxp.use_cases.TripUseCases
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_confirm)

        val tripID = intent.getStringExtra("eventID") ?: ""
        TripUseCases().getTrip(tripID) {trip ->
            tripTitleTv.text = trip.name.toUpperCase(Locale.ROOT)
        }
        val qtd = intent.getIntExtra("qtd", 0)
        ticketQtdTv.text = qtd.toString()
        val acceptedTerms = intent.getBooleanExtra("acceptedTerms", true)

        paymentDateTv.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().time)

        val total = intent.getDoubleExtra("total", 0.0)
        totalPaymentTv.text = "R$ ${total}"

        confirmPaymentBt.setOnClickListener { view ->
            val df: DecimalFormat = DecimalFormat()
            val symbols = DecimalFormatSymbols()
            symbols.decimalSeparator = ','
            symbols.groupingSeparator = ' ';
            df.decimalFormatSymbols = symbols
            val ticket = Ticket(
                userID = auth.currentUser.uid,
                tripID = tripID,
                tripName = tripTitleTv.text.toString(),
                unitPrice = df.parse(ticketPriceTv.text.toString().removePrefix("R$ ")).toDouble(),
                qtd = ticketQtdTv.text.toString().toInt(),
                paymentMethod = paymentMethodTv.text.toString(),
                paymentDate = paymentDateTv.text.toString(),
                total = df.parse(totalPaymentTv.text.toString().removePrefix("R$ ")).toDouble()
            )
            db.collection("tickets")
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