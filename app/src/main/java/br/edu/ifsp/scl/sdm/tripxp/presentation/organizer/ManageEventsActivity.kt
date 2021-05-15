package br.edu.ifsp.scl.sdm.tripxp.presentation.organizer

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.databinding.ActivityManageEventsBinding
import br.edu.ifsp.scl.sdm.tripxp.presentation.LoginActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.UserProfileActivity
import br.edu.ifsp.scl.sdm.tripxp.presentation.organizer.ui.main.SectionsPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ManageEventsActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var binding: ActivityManageEventsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding = ActivityManageEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            db.collection("companies")
                .whereEqualTo("userID", auth.currentUser.uid)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        Snackbar.make(
                            view,
                            "Cadastre os dados de sua empresa",
                            Snackbar.LENGTH_LONG
                        ).setAction("Action", null).show()
                    } else {
                        val companyID = documents.first().id
                        val intent = Intent(this, EditEventActivity::class.java)
                        intent.putExtra("company", companyID)
                        startActivity(intent)
                    }
                }
                .addOnFailureListener { exception ->
                    Snackbar.make(view, "Não foi encontrado sua empresa", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.my_profile -> {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.logout -> {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}