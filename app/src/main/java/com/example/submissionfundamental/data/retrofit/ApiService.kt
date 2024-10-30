package com.example.submissionfundamental.data.retrofit

import com.example.submissionfundamental.data.response.DicodingEventResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("events?active=1")
    fun getUpcomingEvents(): Call<DicodingEventResponse>

    @GET("events?active=0")
    fun getFinished(): Call<DicodingEventResponse>


}

