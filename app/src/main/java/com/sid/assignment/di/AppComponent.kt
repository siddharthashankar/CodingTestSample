package com.sid.assignment.di

import android.app.Application
import com.sid.assignment.db.DatabaseModule
import com.sid.assignment.ui.MovieDetailsActivity
import com.sid.assignment.ui.NowPlayingFragment
import com.sid.assignment.ui.WatchListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(fragment: NowPlayingFragment)


    fun inject(fragment: WatchListFragment)

    fun inject(activity: MovieDetailsActivity)

}