package com.jgtche.mybooks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jgtche.mybooks.entity.BookEntity
import com.jgtche.mybooks.repository.BookRepository

class HomeViewModel : ViewModel() {
    private val _books = MutableLiveData<List<BookEntity>>()
    val books: LiveData<List<BookEntity>> = _books

    private val repository = BookRepository.getInstance()

    fun getAllBooks() {
        _books.value = repository.getAllBooks()
    }

    fun favorite(id: Int) {
        repository.toggleFavoriteStatus(id)
    }
}
