package com.example.submissionfundamental.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamental.R
import com.example.submissionfundamental.adapter.EventAdapter
import com.example.submissionfundamental.database.EventModule
import com.example.submissionfundamental.databinding.ActivityFavoriteBinding
import com.example.submissionfundamental.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val adapter by lazy{
        EventAdapter { event ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("EXTRA_EVENT", event)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(EventModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter

        viewModel.getEventFavorite().observe(this) { events ->
            if (events.isNullOrEmpty()) {
                binding.tvNoItemsAvailable.text = "No items available right now"
                binding.tvNoItemsAvailable.visibility = android.view.View.VISIBLE
                binding.rvFavorite.visibility = android.view.View.GONE
            } else {
                binding.tvNoItemsAvailable.visibility = android.view.View.GONE
                binding.rvFavorite.visibility = android.view.View.VISIBLE
                adapter.submitList(events)
            }
        }

        binding.rvFavorite.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getEventFavorite().observe(this) { events ->
            adapter.submitList(events)
        }
    }

}
