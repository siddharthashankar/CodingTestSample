package com.sid.assignment.di

import android.app.Application
import com.sid.assignment.MainActivity
import com.sid.assignment.data.NetworkModule
import com.sid.assignment.db.DatabaseModule
import com.sid.assignment.ui.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
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

    fun inject(fragment: PopularFragment)

    fun inject(fragment: TopRatedFragment)

    fun inject(fragment: UpcomingFragment)

    fun inject(fragment: WatchListFragment)

    fun inject(activity: MovieDetailsActivity)

    fun inject(activity: MainActivity)

}