package com.example.submissionfundamental.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.example.submissionfundamental.adapter.Event
import com.example.submissionfundamental.data.response.DicodingEventResponse
import com.example.submissionfundamental.data.retrofit.ApiConfig

class UpcommingViewModel : ViewModel() {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchUpcomingEvents() {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getUpcomingEvents().enqueue(object : Callback<DicodingEventResponse> {
            override fun onResponse(
                call: Call<DicodingEventResponse>,
                response: Response<DicodingEventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val events = body.listEvents.map { listEventItem ->
                            Event.fromListEventsItem(listEventItem)
                        }
                        _events.postValue(events)
                        Log.d("UpcommingViewModel", "Fetched ${events.size} upcoming events")
                        _errorMessage.postValue(null) // Reset error message jika berhasil
                    } else {
                        _events.postValue(emptyList())
                        Log.e("UpcommingViewModel", "Response body is null")
                        _errorMessage.postValue("Response body is null")
                    }
                } else {
                    _events.postValue(emptyList())
                    Log.e("UpcommingViewModel", "Response not successful: ${response.code()}")
                    _errorMessage.postValue("Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<DicodingEventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("UpcommingViewModel", "Failed to fetch upcoming events: ${t.message}")
                _errorMessage.postValue("Failed to fetch upcoming events: ${t.message}")
            }
        })
    }
}
