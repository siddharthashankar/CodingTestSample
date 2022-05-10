package com.sid.assignment.di

import android.app.Application
import androidx.room.Room
import com.sid.assignment.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "mymovies.db"
        ).allowMainThreadQueries().build()
    }
}