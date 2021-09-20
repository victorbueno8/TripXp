package br.edu.ifsp.scl.sdm.tripxp.presentation.event.participants

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import br.edu.ifsp.scl.sdm.tripxp.databinding.FragmentParticipantBinding
import br.edu.ifsp.scl.sdm.tripxp.entities.User

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ParticipantsRecyclerViewAdapter(
    private val values: List<User>
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
        holder.userName.text = item.fullname
        if (position == 0) {
            holder.travelerType.text = "organizador"
            holder.travelerType.setTextColor(Color.RED)
        } else {
            holder.travelerType.text = "passageiro"
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val userName: TextView = binding.userNameTv
        val travelerType: TextView = binding.travelerTypeTv
    }

}