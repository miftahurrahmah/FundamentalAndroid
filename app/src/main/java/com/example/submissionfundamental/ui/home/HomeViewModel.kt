package com.example.submissionfundamental.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionfundamental.adapter.Event
import com.example.submissionfundamental.data.response.DicodingEventResponse
import com.example.submissionfundamental.data.response.RetrofitClient
import com.example.submissionfundamental.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitClient.apiService

    private val _upcomingEvents = MutableLiveData<List<Event>>()
    val upcomingEvents: LiveData<List<Event>> get() = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<Event>>()
    val finishedEvents: LiveData<List<Event>> get() = _finishedEvents

    init {
        fetchUpcomingEvents()
        fetchFinishedEvents()
    }

    fun fetchUpcomingEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getUpcomingEvents().execute()

                if (response.isSuccessful) {
                    val dicodingEventResponse = response.body() as? DicodingEventResponse

                    val eventsList = dicodingEventResponse?.listEvents?.map { listItem ->
                        Event.fromListEventsItem(listItem)
                    } ?: emptyList()

                    withContext(Dispatchers.Main) {
                        _upcomingEvents.value = eventsList
                    }
                } else {
                    Log.e("HomeViewModel", "Failed to fetch upcoming events: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching upcoming events", e)
            }
        }
    }


    fun fetchFinishedEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getFinished().execute()

                if (response.isSuccessful) {
                    val dicodingEventResponse = response.body() as? DicodingEventResponse

                    val eventsList = dicodingEventResponse?.listEvents?.map { listItem ->
                        Event.fromListEventsItem(listItem)
                    } ?: emptyList()

                    withContext(Dispatchers.Main) {
                        _finishedEvents.value = eventsList
                    }
                } else {
                    Log.e("HomeViewModel", "Failed to fetch finished events: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching finished events", e)
            }
        }
    }
}

