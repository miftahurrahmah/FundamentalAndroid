package com.example.submissionfundamental.ui.upcomming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionfundamental.detail.DetailActivity
import com.example.submissionfundamental.R
import com.example.submissionfundamental.adapter.EventAdapter
import com.example.submissionfundamental.ui.UpcommingViewModel

class UpCommingFragment : Fragment() {

    private val viewModel: UpcommingViewModel by viewModels()
    private lateinit var eventAdapter: EventAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_upcooming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.rv_event_UpComming)
        recyclerView.layoutManager = LinearLayoutManager(context)

        eventAdapter = EventAdapter { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EXTRA_EVENT", event)
            startActivity(intent)
        }

        recyclerView.adapter = eventAdapter

        progressBar.visibility = View.VISIBLE
        viewModel.fetchUpcomingEvents()

        viewModel.events.observe(viewLifecycleOwner) { events ->
            progressBar.visibility = View.GONE

            Log.d("UpCommingFragment", "Event List: $events")

            if (!events.isNullOrEmpty()) {
                recyclerView.visibility = View.VISIBLE
                eventAdapter.submitList(events)
            } else {
                // Jika data kosong
                Log.d("UpCommingFragment", "No events available")
                recyclerView.visibility = View.GONE
            }
        }
    }
}
