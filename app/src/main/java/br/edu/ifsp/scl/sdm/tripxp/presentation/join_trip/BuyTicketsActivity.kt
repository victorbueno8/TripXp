package br.edu.ifsp.scl.sdm.tripxp.presentation.join_trip

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Trip
import br.edu.ifsp.scl.sdm.tripxp.util.NumberFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_buy_tickets.*

class BuyTicketsActivity : AppCompatActivity() {
    private lateinit var eventID: String
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var  db: FirebaseFirestore = FirebaseFirestore.getInstance()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_tickets)

        val numFormatter = NumberFormat()

        eventID = intent.getStringExtra("eventID") ?: ""
        if (eventID.isNotEmpty()) {
            val documentReference: DocumentReference = db.collection("trips").document(eventID)
            documentReference.get()
                .addOnSuccessListener { snapshot ->
                    val event = snapshot.toObject(Trip::class.java)
                    if (event != null) {
                        if (event.eventImageUri.isNotEmpty()) {
                            Picasso.get().load(event.eventImageUri).into(tripImageIv2)
                        }
                        val price = event.ticketPrice
                        unitPriceTv.text = "R$ ${numFormatter.format(price)}";
                        var tickets = 1
                        val available = event.ticketQtd
                        numberAvailableTicketsTv.text = available.toString()
                        var total = price
                        totalPaymentTv2.text = "R$ ${numFormatter.format(total)}";

                        plusBt.setOnClickListener {
                            if (tickets in 1 until available) {
                                tickets++
                                numberOfTicketsTv.text = tickets.toString()
                                total = price * tickets.toDouble()
                                totalPaymentTv2.text = "R$ ${numFormatter.format(total)}";
                            }
                        }

                        minusBt.setOnClickListener {
                            if (tickets in 2..available) {
                                tickets--
                                numberOfTicketsTv.text = tickets.toString()
                                total = price * tickets.toDouble()
                                totalPaymentTv2.text = "R$ ${numFormatter.format(total)}";
                            }
                        }

                        buyTicketsBt.setOnClickListener { view ->
                            val termsAcceptPage = Intent(this, TermsActivity::class.java)
                            termsAcceptPage.putExtra("eventID", eventID)
                            termsAcceptPage.putExtra("qtd", tickets)
                            termsAcceptPage.putExtra("total", total)
                            startActivity(termsAcceptPage)
                        }

                    }
                }
        }
    }
}