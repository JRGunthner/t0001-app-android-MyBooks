package com.jgtche.mybooks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgtche.mybooks.databinding.ItemBookBinding
import com.jgtche.mybooks.entity.BookEntity
import com.jgtche.mybooks.ui.listener.BookListener
import com.jgtche.mybooks.ui.viewholder.BookViewHolder

class BookAdapter : RecyclerView.Adapter<BookViewHolder>() {
    private var bookList : List<BookEntity> = listOf()
    private lateinit var bookListener: BookListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(view, bookListener)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    fun updateBooks(list: List<BookEntity>) {
        bookList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: BookListener) {
        bookListener = listener
    }
}