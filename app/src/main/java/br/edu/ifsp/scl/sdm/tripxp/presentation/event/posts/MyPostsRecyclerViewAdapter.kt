package br.edu.ifsp.scl.sdm.tripxp.presentation.event.posts

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.sdm.tripxp.databinding.FragmentPostItemBinding
import br.edu.ifsp.scl.sdm.tripxp.entities.Post
import br.edu.ifsp.scl.sdm.tripxp.entities.PostComment

import br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.placeholder.PlaceholderContent.PlaceholderItem
import br.edu.ifsp.scl.sdm.tripxp.use_cases.UserUseCases
import br.edu.ifsp.scl.sdm.tripxp.util.DateFormat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_posts.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyPostsRecyclerViewAdapter(
    private val context: Context,
    private val posts: ArrayList<Post>
) : RecyclerView.Adapter<MyPostsRecyclerViewAdapter.ViewHolder>() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        return ViewHolder(
            FragmentPostItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        var comments: ArrayList<PostComment> = ArrayList()
        val commentsCollection = db.collection("trips").document(post.tripID)
            .collection("posts").document(post.id)
            .collection("comments")
        Log.d("BindPost",post.toString())

        holder.postUserNameTv.text = post.user.name
        holder.postCreateDateTv.text = DateFormat("HH:mm dd/MM/yyyy").toString(post.createdAt)
        holder.postMessageTv.text = post.text
        holder.commentCount.text = "0 comments"
        holder.commentsRv.layoutManager = LinearLayoutManager(context)
        holder.commentsRv.adapter = CommentsRecyclerViewAdapter(comments)
        commentsCollection.orderBy("createdAt", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { snapshot ->
                holder.commentCount.text = "${snapshot.count()} comments"
                holder.commentsRv.layoutManager = LinearLayoutManager(context)
                comments = snapshot.toObjects(PostComment::class.java) as ArrayList<PostComment>
                holder.commentsRv.adapter = CommentsRecyclerViewAdapter(comments)

                holder.commentBt.setOnClickListener { view ->
                    UserUseCases().getUser { user ->
                        val comment = PostComment(user = user, postID = post.id, text = holder.commentEt.text.toString())
                        commentsCollection.add(comment)
                            .addOnSuccessListener { snapshot ->
                                holder.commentEt.text.clear()
                                comments.add(0, comment.apply { id = snapshot.id })
                                holder.commentsRv.adapter = CommentsRecyclerViewAdapter(comments)
                                Snackbar.make(view, "Comentário publicado!", Snackbar.LENGTH_LONG).show()
                            }
                            .addOnFailureListener{ e ->
                                Snackbar.make(view, "Erro: " + e.message, Snackbar.LENGTH_LONG).show()
                            }
                    }
                }
            }

    }

    override fun getItemCount(): Int = posts.size

    inner class ViewHolder(binding: FragmentPostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val postUserNameTv: TextView = binding.postUserNameTv
        val postCreateDateTv: TextView = binding.postCreateDateTv
        val postMessageTv: TextView = binding.postMessageTv
        val commentCount: TextView = binding.commentsCountTv
        val commentEt: EditText = binding.commentEt
        val commentBt: Button = binding.commentBt
        val commentsRv: RecyclerView = binding.commentsRv

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }

}