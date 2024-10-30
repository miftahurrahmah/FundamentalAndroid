package com.example.submissionfundamental.ui.home

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionfundamental.R
import com.example.submissionfundamental.adapter.Event

class HomeAdapter(
    private val onEventClick: (Event) -> Unit,
    private val isVertical: Boolean
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var events: List<Event> = emptyList()

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgUrl: ImageView = itemView.findViewById(R.id.imageLogo)
        private val tvJudul: TextView? = itemView.findViewById(R.id.tv_judul)
        private val tvOwnerName: TextView? = itemView.findViewById(R.id.tv_ownerName)
        private val tvDescription: TextView? = itemView.findViewById(R.id.tv_description)

        fun bind(event: Event) {
            // Load image
            Glide.with(itemView.context)
                .load(event.imageUrl)
                .into(imgUrl)

            if (isVertical) {
                tvJudul?.text = event.title
                tvOwnerName?.text = event.ownerName
                tvDescription?.text = getShortenedDescription(event.description)
            } else {
                tvJudul?.text = event.title
            }

            itemView.setOnClickListener {
                onEventClick(event)
            }
        }

        private fun getShortenedDescription(description: String): String {
            val plainDescription = Html.fromHtml(getFilteredDescription(description)).toString() //konversi ke html
            val words = plainDescription.split(" ")
            val maxWords = 10
            return if (words.size > maxWords) {
                words.take(maxWords).joinToString(" ") + "..."
            } else {
                plainDescription
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutId = if (isVertical) {
            R.layout.item_vertical
        } else {
            R.layout.item_horizontal
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun submitList(eventList: List<Event>) {
        events = eventList
        notifyDataSetChanged()
    }

    private fun getFilteredDescription(description: String): String {
        val descriptionWithoutImages = description.replace(Regex("<img.*?>"), "")
        //konversi teks
        return Html.fromHtml(descriptionWithoutImages).toString().trim()
    }
}
