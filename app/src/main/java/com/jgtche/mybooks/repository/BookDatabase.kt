package com.jgtche.mybooks.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jgtche.mybooks.entity.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDAO(): BookDAO

    companion object {
        private lateinit var instance: BookDatabase
        private const val DATABASE_NAME = "books_db"

        fun getDatabase(context: Context): BookDatabase {
            if (!::instance.isInitialized) {
                synchronized(this) {
                    instance = Room.databaseBuilder(context, BookDatabase::class.java, DATABASE_NAME)
                        .addMigrations(Migrations.migrationsFronV1ToV2)
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return instance
        }
    }

    private object Migrations {
        val migrationsFronV1ToV2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DELETE FROM Books")
                // logica do contexto
            }
        }
    }
}
