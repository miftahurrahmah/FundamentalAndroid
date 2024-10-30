package com.example.submissionfundamental.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.submissionfundamental.R
import com.example.submissionfundamental.adapter.Event
import com.example.submissionfundamental.database.EventModule
import com.example.submissionfundamental.databinding.ActivityDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Toast

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels() {
        DetailViewModel.Factory(EventModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari intent dan set ke ViewModel
        val event = intent.getParcelableExtra<Event>("EXTRA_EVENT")
        event?.let {
            detailViewModel.setEvent(it)
        }

        detailViewModel.event.observe(this) { event ->
            binding.tvDetailJudul.text = event.name
            binding.tvDetailOwner.text = event.ownerName
            binding.tvLinkEvent.text = event.link
            binding.tvSummary.text = event.summary
            binding.tvCategory.text = event.category
            binding.tvCityName.text = event.cityName
            binding.tvRegistrant.text = event.registrants.toString()
            binding.tvKuota.text = event.quota.toString()
            binding.tvSisaKuota.text = detailViewModel.getRemainingQuota().toString()
            binding.tvBeginTime.text = event.beginTime
            binding.tvEndTime.text = event.endTime
            binding.tvDescription.text = detailViewModel.stripHtml(event.description)

            // Load image dengan Glide
            Glide.with(this)
                .load(event.imageUrl)
                .into(binding.imgDetailEvent)
        }

        // Handle button klik untuk membuka link
        binding.btnLink.setOnClickListener {
            val eventLink = event?.link
            if (eventLink != null) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = android.net.Uri.parse(eventLink)
                }
                startActivity(intent)
            }
        }

        binding.btnFavorite.setOnClickListener {
            // Aksi tombol favorite
            detailViewModel.favoriteEvent(event)
        }

        detailViewModel.findFavorite(event?.id ?: 0) {
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        detailViewModel.resultSuksesFavorite.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.red)
            Toast.makeText(this, "Event berhasil ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
        }

        detailViewModel.resultDeleteFavorite.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.white)
            Toast.makeText(this, "Event berhasil dihapus dari favorit", Toast.LENGTH_SHORT).show()
        }
    }
}

fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}
