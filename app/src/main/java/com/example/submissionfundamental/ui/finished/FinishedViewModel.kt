package com.example.submissionfundamental.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionfundamental.data.response.DicodingEventResponse
import com.example.submissionfundamental.data.response.ListEventsItem
import com.example.submissionfundamental.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {
    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchFinishedEvents() {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getFinished().enqueue(object : Callback<DicodingEventResponse> {
            override fun onResponse(call: Call<DicodingEventResponse>, response: Response<DicodingEventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { dicodingEventResponse ->
                        _events.postValue(dicodingEventResponse.listEvents)
                        _errorMessage.postValue(null)
                    } ?: run {
                        _events.postValue(emptyList())
                        _errorMessage.postValue("No events found.")
                    }
                } else {
                    _events.postValue(emptyList())
                    _errorMessage.postValue("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DicodingEventResponse>, t: Throwable) {
                _isLoading.value = false

                _events.postValue(emptyList())
                _errorMessage.postValue("Failed to fetch data: ${t.message}")
            }
        })
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
