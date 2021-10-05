package br.edu.ifsp.scl.sdm.tripxp.presentation.event.participants

import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.edu.ifsp.scl.sdm.tripxp.R
import br.edu.ifsp.scl.sdm.tripxp.databinding.FragmentParticipantBinding
import br.edu.ifsp.scl.sdm.tripxp.entities.User
import br.edu.ifsp.scl.sdm.tripxp.presentation.mytrips.EventListItemAdapter
import br.edu.ifsp.scl.sdm.tripxp.util.CircleTransform
import com.squareup.picasso.Picasso

class ParticipantsRecyclerViewAdapter(
    private val values: List<User>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ParticipantsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentParticipantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        if (item.profileImageUri.isNotEmpty()) {
            Picasso.get().load(item.profileImageUri)
                .resize(120,120).transform(CircleTransform())
                .into(holder.userAvatarIv)
        }
        holder.userName.text = item.fullname
        if (position == 0) {
            holder.travelerType.text = "organizador"
            holder.travelerType.setTextColor(Color.RED)
        } else {
            holder.travelerType.text = "passageiro"
        }
    }

    override fun getItemCount(): Int = values.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(binding: FragmentParticipantBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        val userAvatarIv: ImageView = binding.userAvatarIv
        val userName: TextView = binding.userNameTv
        val travelerType: TextView = binding.travelerTypeTv

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

}