package com.example.submissionfundamental.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.submissionfundamental.adapter.Event
import com.example.submissionfundamental.database.EventModule
import kotlinx.coroutines.launch

class DetailViewModel(private val db: EventModule) : ViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event
    private var isFavorite = false
    val resultSuksesFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()

    fun setEvent(event: Event) {
        _event.value = event
    }

    fun getRemainingQuota(): Int {
        return _event.value?.let {
            it.quota - it.registrants
        } ?: 0
    }

    fun stripHtml(html: String): String {
        return android.text.Html.fromHtml(html).toString()
    }

    fun favoriteEvent(event: Event?) {
        viewModelScope.launch {
            event?.let {
                isFavorite = !isFavorite

                if (isFavorite) {
                    db.eventDao.insert(event)
                    resultSuksesFavorite.value = true
                } else {
                    db.eventDao.delete(event)
                    resultDeleteFavorite.value = true
                }

                _event.postValue(it)
            }
        }
    }



    // Cek apakah sudah pernah favorite atau belum
    fun findFavorite(id: Int, listenFavorite: () -> Unit) {
        viewModelScope.launch {
            val event = db.eventDao.findById(id)
            if (event != null) {
                listenFavorite()
                isFavorite = true
            }
        }
    }

    class Factory(private val db: EventModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }
}


