package com.example.submissionfundamental.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamental.detail.DetailActivity
import com.example.submissionfundamental.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var finishedEventAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setupRecyclerViews()
        observeData()
    }

    private fun setupRecyclerViews() {

        homeAdapter = HomeAdapter({ event ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("EXTRA_EVENT", event)
            }
            startActivity(intent)
        }, isVertical = false)

        binding.rvActiveHorizontal.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvActiveHorizontal.adapter = homeAdapter

        finishedEventAdapter = HomeAdapter({ event ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("EXTRA_EVENT", event)
            }
            startActivity(intent)
        }, isVertical = true)

        binding.rvActiveVertikal.layoutManager = LinearLayoutManager(requireContext())
        binding.rvActiveVertikal.adapter = finishedEventAdapter
    }

    private fun observeData() {
        homeViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            Log.d("HomeFragment", "Upcoming events: ${events.size} items loaded")
            if (events != null && events.isNotEmpty()) {
                homeAdapter.submitList(events)
            } else {
                Log.e("HomeFragment", "No upcoming events found")
                Toast.makeText(requireContext(), "No upcoming events", Toast.LENGTH_SHORT).show()
            }
        }

        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            Log.d("HomeFragment", "Finished events: ${events.size} items loaded")
            if (events != null && events.isNotEmpty()) {
                finishedEventAdapter.submitList(events)
            } else {
                Log.e("HomeFragment", "No finished events found")
                Toast.makeText(requireContext(), "No finished events", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}