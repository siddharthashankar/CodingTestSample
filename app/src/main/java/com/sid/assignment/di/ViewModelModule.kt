package com.sid.assignment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arthlimchiu.mymovies.ViewModelFactory
import com.arthlimchiu.mymovies.ViewModelKey
import com.sid.assignment.ui.MoviesViewModel
import com.sid.assignment.viewmodel.WatchListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindsMoviesViewModel(viewModel: MoviesViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(WatchListViewModel::class)
    abstract fun bindsWatchListViewModel(viewModel: WatchListViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}