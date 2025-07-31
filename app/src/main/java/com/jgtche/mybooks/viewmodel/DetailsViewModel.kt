package com.jgtche.mybooks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jgtche.mybooks.entity.BookEntity
import com.jgtche.mybooks.repository.BookRepository

class DetailsViewModel : ViewModel() {
    private val repository: BookRepository = BookRepository.getInstance()

    private val _book = MutableLiveData<BookEntity>()
    val book: LiveData<BookEntity> = _book

    private val _bookRemoval = MutableLiveData<Boolean>()
    val bookRemoval: LiveData<Boolean> = _bookRemoval

    fun getBookById(id: Int) {
        _book.value = repository.getBookById(id)
    }

    fun deleteBook(id: Int) {
        _bookRemoval.value = repository.deleteBook(id)
    }

    fun favorite(id: Int) {
        repository.toggleFavoriteStatus(id)
    }
}
