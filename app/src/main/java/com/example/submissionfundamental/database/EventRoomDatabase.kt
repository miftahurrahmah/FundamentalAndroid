package com.example.submissionfundamental.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.submissionfundamental.adapter.Event

@Database(entities = [Event::class], version = 1, exportSchema = false)
abstract class EventRoomDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

}