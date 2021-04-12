package br.edu.ifsp.scl.sdm.tripxp.presentation

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import br.edu.ifsp.scl.sdm.tripxp.R

class PaymentMethodActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)

        val creditExpandableView : ConstraintLayout = findViewById(R.id.creditInfoContentCl);
        val creditArrowButton : ImageButton = findViewById(R.id.creditOptionIb);
        val creditOptionCardView : CardView = findViewById(R.id.creditOptionCardCv)
        expandCardView(creditExpandableView, creditArrowButton, creditOptionCardView)

        val debitExpandableView : ConstraintLayout = findViewById(R.id.debitInfoContentCl);
        val debitArrowButton : ImageButton = findViewById(R.id.debitOptionIb);
        val debitOptionCardView : CardView = findViewById(R.id.debitOptionCardCv)
        expandCardView(debitExpandableView, debitArrowButton, debitOptionCardView)

        val slipExpandableView : ConstraintLayout = findViewById(R.id.slipInfoContentCl);
        val slipArrowButton : ImageButton = findViewById(R.id.slipOptionIb);
        val slipOptionCardView : CardView = findViewById(R.id.slipOptionCardCv)
        expandCardView(slipExpandableView, slipArrowButton, slipOptionCardView)

        val transferExpandableView : ConstraintLayout = findViewById(R.id.transferInfoContentCl);
        val transferArrowButton : ImageButton = findViewById(R.id.transferOptionIb);
        val transferOptionCardView : CardView = findViewById(R.id.transferOptionCardCv)
        expandCardView(transferExpandableView, transferArrowButton, transferOptionCardView)

        val confirmPaymentButton : Button = findViewById(R.id.confirmCreditCardBt)
        confirmPaymentButton.setOnClickListener { view ->
            startActivity(Intent(this, EventActivity::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun expandCardView(expandableView: ConstraintLayout, expandButton: ImageButton, cardView: CardView) {
        expandButton.setOnClickListener{ view ->
            if (expandableView.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                expandableView.visibility = View.VISIBLE
                expandButton.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
            } else {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                expandableView.visibility = View.GONE
                expandButton.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }
        }
    }
}