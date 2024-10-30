package com.example.submissionfundamental.ui.finished

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
import com.example.submissionfundamental.adapter.Event
import com.example.submissionfundamental.adapter.EventAdapter
import com.example.submissionfundamental.ui.FinishedViewModel

class FinishedFragment : Fragment() {

    private val viewModel: FinishedViewModel by viewModels()
    private lateinit var eventAdapter: EventAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_finished, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_finished)
        recyclerView.layoutManager = LinearLayoutManager(context)

        eventAdapter = EventAdapter { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EXTRA_EVENT", event)
            startActivity(intent)
        }

        recyclerView.adapter = eventAdapter

        viewModel.events.observe(viewLifecycleOwner) { events ->
            progressBar.visibility = View.GONE

            Log.d("FinishedFragment", "Finished Event List: $events")

            if (events.isNotEmpty()) {
                recyclerView.visibility = View.VISIBLE
                eventAdapter.submitList(events.map { Event.fromListEventsItem(it) })
            } else {
                Log.d("FinishedFragment", "No finished events available")
                recyclerView.visibility = View.GONE
            }
        }

        progressBar.visibility = View.VISIBLE
        viewModel.fetchFinishedEvents()
    }
}

