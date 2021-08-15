package br.edu.ifsp.scl.sdm.tripxp.presentation.event.posts

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sdm.tripxp.databinding.FragmentCommentItemBinding
import br.edu.ifsp.scl.sdm.tripxp.entities.PostComment
import br.edu.ifsp.scl.sdm.tripxp.util.DateFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CommentsRecyclerViewAdapter(
    private val comments: ArrayList<PostComment>
) : RecyclerView.Adapter<CommentsRecyclerViewAdapter.ViewHolder>() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        return ViewHolder(
            FragmentCommentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]
        Log.d("BindComment",comment.toString())

        holder.commentUserNameTv.text = comment.user.name
        holder.commentCreateDateTv.text = DateFormat("HH:mm dd/MM/yyyy").toString(comment.createdAt)
        holder.commentMessageTv.text = comment.text
    }

    override fun getItemCount(): Int = comments.size

    inner class ViewHolder(binding: FragmentCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val commentUserNameTv: TextView = binding.commentUserNameTv
        val commentCreateDateTv: TextView = binding.commentCreateDateTv
        val commentMessageTv: TextView = binding.commentMessageTv
    }

}