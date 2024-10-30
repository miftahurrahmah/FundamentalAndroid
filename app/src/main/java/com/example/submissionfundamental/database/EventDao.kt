package com.example.submissionfundamental.database

import com.example.submissionfundamental.adapter.Event
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: Event)

   @Query("SELECT * FROM events")
   fun loadAll() : LiveData<MutableList<Event>>

   @Query("SELECT * FROM events WHERE id LIKE :id LIMIT 1")
   fun findById(id : Int) : Event

   @Delete
   fun delete(event: Event)

}