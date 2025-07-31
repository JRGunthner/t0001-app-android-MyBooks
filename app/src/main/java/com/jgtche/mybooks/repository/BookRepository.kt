package com.jgtche.mybooks.repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.jgtche.mybooks.entity.BookEntity
import com.jgtche.mybooks.helper.DatabaseConstants

class BookRepository private constructor(context: Context) {
    private var database = BookDatabaseHelper(context)

    companion object {
        private lateinit var instance: BookRepository

        fun getInstance(context: Context): BookRepository {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = BookRepository(context)
                }
            }

            return instance
        }
    }

    fun getAllBooks(): List<BookEntity> {
        val db = database.readableDatabase
        val books = mutableListOf<BookEntity>()

        val cursor = db.query(DatabaseConstants.BOOK.TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.ID))
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.TITLE))
                val author =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.AUTHOR))
                val genre =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.GENRE))
                val favorite: Boolean =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.FAVORITE)) == 1

                books.add(BookEntity(id, title, author, favorite, genre))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return books
    }

    fun getFavoriteBooks(): List<BookEntity> {
        val db = database.readableDatabase
        val books = mutableListOf<BookEntity>()

        val cursor = db.query(
            DatabaseConstants.BOOK.TABLE_NAME,
            null,
            "${DatabaseConstants.BOOK.COLUMNS.FAVORITE} = ?",
            arrayOf("1"),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.ID))
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.TITLE))
                val author =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.AUTHOR))
                val genre =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.GENRE))
                val favorite: Boolean =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.FAVORITE)) == 1

                books.add(BookEntity(id, title, author, favorite, genre))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return books
    }

    fun getBookById(id: Int): BookEntity? {
        val db = database.readableDatabase

        val cursor = db.query(
            DatabaseConstants.BOOK.TABLE_NAME,
            null,
            "${DatabaseConstants.BOOK.COLUMNS.ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var book: BookEntity? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.ID))
            val title =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.TITLE))
            val author =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.AUTHOR))
            val genre =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.GENRE))
            val favorite: Boolean =
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOK.COLUMNS.FAVORITE)) == 1

            book = BookEntity(id, title, author, favorite, genre)
        }

        cursor.close()
        db.close()

        return book
    }

    fun deleteBook(id: Int): Boolean {
        Log.d("TCHE BookRepository", "Deletando o id: $id")

        val db = database.writableDatabase
        val rowsDeleted = db.delete(
            DatabaseConstants.BOOK.TABLE_NAME,
            "${DatabaseConstants.BOOK.COLUMNS.ID} = ?",
            arrayOf(id.toString())
        )

        return rowsDeleted > 0
    }

    fun toggleFavoriteStatus(id: Int) {
        Log.d("TCHE BookRepository", "Favoritando o id: $id")
        val book = getBookById(id)
        val newFavoriteStatus = if (book?.favorite == true) 0 else 1
        val db = database.writableDatabase

        val value = ContentValues().apply {
            put(DatabaseConstants.BOOK.COLUMNS.FAVORITE, newFavoriteStatus)
        }

        db.update(
            DatabaseConstants.BOOK.TABLE_NAME,
            value,
            "${DatabaseConstants.BOOK.COLUMNS.ID} = ?",
            arrayOf(id.toString())
        )

        db.close()
    }
}
