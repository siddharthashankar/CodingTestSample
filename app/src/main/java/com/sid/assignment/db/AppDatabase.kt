package com.sid.assignment.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}