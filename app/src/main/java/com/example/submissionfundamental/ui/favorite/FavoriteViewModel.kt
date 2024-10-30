package com.example.submissionfundamental.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionfundamental.database.EventModule
import com.example.submissionfundamental.detail.DetailViewModel

class FavoriteViewModel(private val eventModule: EventModule) : ViewModel() {

    fun getEventFavorite() = eventModule.eventDao.loadAll()

    class Factory(private val db: EventModule) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}