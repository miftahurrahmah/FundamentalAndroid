package com.example.submissionfundamental.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionfundamental.R

class EventAdapter(private val onClick: (Event) -> Unit) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var eventList: List<Event> = emptyList()
    private val data: MutableList<Event> = mutableListOf()

    fun submitList(events: List<Event>) {
        this.data.clear()
        this.data.addAll(data)
        eventList = events
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.bind(event)
        holder.itemView.setOnClickListener { onClick(event) }
    }

    override fun getItemCount(): Int = eventList.size

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgEvent: ImageView = itemView.findViewById(R.id.img_event)
        private val tvJudul: TextView = itemView.findViewById(R.id.tv_judul)
        private val tvOwnerName: TextView = itemView.findViewById(R.id.tv_ownerName)
        private val tvQuota: TextView = itemView.findViewById(R.id.tv_quota)
        private val tvBeginTime: TextView = itemView.findViewById(R.id.tv_beginTime)

        fun bind(event: Event) {
            tvJudul.text = event.title
            tvOwnerName.text = event.ownerName
            tvQuota.text = "Sisa Kuota: ${event.quota - event.registrants}"
            tvBeginTime.text = "Tanggal : ${event.beginTime}"

            Glide.with(itemView.context)
                .load(event.imageUrl)
                .into(imgEvent)
        }
    }
}