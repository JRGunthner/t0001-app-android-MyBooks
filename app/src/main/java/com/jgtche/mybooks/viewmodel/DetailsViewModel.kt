package com.jgtche.mybooks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jgtche.mybooks.entity.BookEntity
import com.jgtche.mybooks.repository.BookRepository
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BookRepository =
        BookRepository.getInstance(application.applicationContext)

    private val _book = MutableLiveData<BookEntity>()
    val book: LiveData<BookEntity> = _book

    private val _bookRemoval = MutableLiveData<Boolean>()
    val bookRemoval: LiveData<Boolean> = _bookRemoval

    fun getBookById(id: Int) {
        viewModelScope.launch {
            _book.value = repository.getBookById(id)
        }
    }

    fun deleteBook(id: Int) {
        viewModelScope.launch {
            _bookRemoval.value = repository.deleteBook(id)
        }
    }

    fun favorite(id: Int) {
        viewModelScope.launch {
            repository.toggleFavoriteStatus(id)
        }
    }
}
