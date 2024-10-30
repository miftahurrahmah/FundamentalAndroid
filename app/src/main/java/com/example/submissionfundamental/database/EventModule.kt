package com.example.submissionfundamental.database

import android.content.Context
import androidx.room.Room

class EventModule(private val context: Context) {
    private val db = Room.databaseBuilder(context, EventRoomDatabase::class.java, "event.db")
        .allowMainThreadQueries()
        .build()

    val eventDao = db.eventDao()
}