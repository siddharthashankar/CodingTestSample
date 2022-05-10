package com.sid.assignment

import android.app.Application
import com.sid.assignment.di.AppComponent
import com.sid.assignment.di.DaggerAppComponent

class MyMoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}

lateinit var appComponent: AppComponent