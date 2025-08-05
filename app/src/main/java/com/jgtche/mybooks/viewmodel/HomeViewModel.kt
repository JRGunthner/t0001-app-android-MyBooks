package com.jgtche.mybooks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jgtche.mybooks.entity.BookEntity
import com.jgtche.mybooks.repository.BookRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BookRepository.getInstance(application.applicationContext)

    val books: LiveData<List<BookEntity>> = repository.getAllBooks().asLiveData()

    fun favorite(id: Int) {
        viewModelScope.launch {
            repository.toggleFavoriteStatus(id)
        }
    }
}
