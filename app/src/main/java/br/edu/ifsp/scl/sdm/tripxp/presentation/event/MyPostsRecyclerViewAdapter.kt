package br.edu.ifsp.scl.sdm.tripxp.presentation.event

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import br.edu.ifsp.scl.sdm.tripxp.databinding.FragmentPostItemBinding
import br.edu.ifsp.scl.sdm.tripxp.entities.Post

import br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.placeholder.PlaceholderContent.PlaceholderItem
import br.edu.ifsp.scl.sdm.tripxp.util.DateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyPostsRecyclerViewAdapter(
    private val posts: ArrayList<Post>
) : RecyclerView.Adapter<MyPostsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

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
        holder.postUserNameTv.text = post.user.name
        holder.postCreateDateTv.text = DateFormat("HH:mm dd/MM/yyyy").toString(post.createdAt)
        holder.postMessageTv.text = post.text
    }

    override fun getItemCount(): Int = posts.size

    inner class ViewHolder(binding: FragmentPostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val postUserNameTv: TextView = binding.postUserNameTv
        val postCreateDateTv: TextView = binding.postCreateDateTv
        val postMessageTv: TextView = binding.postMessageTv

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }

}