package br.edu.ifsp.scl.sdm.tripxp.presentation.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.entities.Post
import br.edu.ifsp.scl.sdm.tripxp.use_cases.UserUseCases
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_posts.*

/**
 * A fragment representing a list of Items.
 */
class PostsFragment : Fragment() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var postList: ArrayList<Post> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        UserUseCases().getUser { user ->
            if (user.userType != "organizer") {
                createPostLv.visibility = View.GONE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postFeedRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = MyPostsRecyclerViewAdapter(context, postList)

            val tripID = activity?.intent?.getStringExtra("eventID") ?: ""
            if (tripID != "") {
                db.collection("trips").document(tripID).collection("posts")
                    .orderBy("createdAt",Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        postList.addAll(snapshot.toObjects(Post::class.java))
                        (adapter as MyPostsRecyclerViewAdapter).notifyDataSetChanged()
                    }
                    .addOnFailureListener{ e ->
                        Snackbar.make(view, "Erro: " + e.message, Snackbar.LENGTH_LONG).show()
                    }

                sendPostBt.setOnClickListener { view ->
                    val text = postEt.text.toString()
                    UserUseCases().getUser { user ->
                        val post = Post(text = text, user = user, tripID = tripID)
                        db.collection("trips").document(tripID).collection("posts")
                            .add(post)
                            .addOnSuccessListener { document ->
                                postList.add(0, post.apply { id = document.id })
                                postEt.text.clear()
                                (adapter as MyPostsRecyclerViewAdapter).notifyDataSetChanged()
                                Snackbar.make(view, "Postagem publicada!", Snackbar.LENGTH_LONG).show()
                            }
                            .addOnFailureListener{ e ->
                                Snackbar.make(view, "Erro: " + e.message, Snackbar.LENGTH_LONG).show()
                            }
                    }

                }
            }
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(sectionNumber: Int) =
            PostsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
    }
}