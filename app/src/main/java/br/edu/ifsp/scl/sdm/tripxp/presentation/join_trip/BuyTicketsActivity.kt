package br.edu.ifsp.scl.sdm.tripxp.presentation.join_trip

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.tripxp.R
import kotlinx.android.synthetic.main.activity_buy_tickets.*

class BuyTicketsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_tickets)

        val eventID = intent.getStringExtra("eventID") ?: ""

        var price = 100.00
        unitPriceTv.text = "R$ ${price}";
        var tickets = numberOfTicketsTv.text.toString().toInt()
        val available = numberAvailableTicketsTv.text.toString().toInt()
        var total = price
        totalPaymentTv2.text = "R$ ${total}";

            plusBt.setOnClickListener {
            if (tickets in 1 until available) {
                tickets++
                numberOfTicketsTv.text = tickets.toString()
                total = price * tickets.toDouble()
                totalPaymentTv2.text = "R$ ${total}";
            }
        }

        minusBt.setOnClickListener {
            if (tickets in 2..available) {
                tickets--
                numberOfTicketsTv.text = tickets.toString()
                total = price * tickets.toDouble()
                totalPaymentTv2.text = "R$ ${total}";
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