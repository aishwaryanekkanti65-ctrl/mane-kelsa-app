package com.example.manekelsa

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class WorkerAdapter(private val list: List<Worker>) :
    RecyclerView.Adapter<WorkerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.txtName)
        val skill = view.findViewById<TextView>(R.id.txtSkill)
        val wage = view.findViewById<TextView>(R.id.txtWage)
        val rating = view.findViewById<TextView>(R.id.txtRating)
        val call = view.findViewById<Button>(R.id.btnCall)
        val like = view.findViewById<Button>(R.id.btnLike)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_worker, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val worker = list[position]

        holder.name.text = worker.name
        holder.skill.text = worker.skill
        holder.wage.text = "₹${worker.wage}"
        holder.rating.text = "Rating: ${worker.rating}"

        holder.call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${worker.phone}")
            holder.itemView.context.startActivity(intent)
        }

        holder.like.setOnClickListener {
            worker.rating++
            holder.rating.text = "Rating: ${worker.rating}"
        }
    }

    override fun getItemCount() = list.size
}