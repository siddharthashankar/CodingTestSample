package com.sid.assignment.ui


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sid.assignment.R
import com.sid.assignment.adapter.WatchListAdapter
import com.sid.assignment.appComponent
import com.sid.assignment.data.WatchList
import com.sid.assignment.viewmodel.WatchListViewModel
import javax.inject.Inject

class WatchListFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: WatchListViewModel

    private lateinit var watchList: RecyclerView
    private lateinit var watchListAdapter: WatchListAdapter

    private lateinit var filter: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(WatchListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_watch_list, container, false)

        watchList = view.findViewById(R.id.watchlist)
        watchList.layoutManager = GridLayoutManager(context, 3)
        watchListAdapter = WatchListAdapter(listOf()) {
           showMovieDetails(it)
        }
        watchList.adapter = watchListAdapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.all.observe(viewLifecycleOwner, Observer { watchlist ->
            watchListAdapter.updateItems(watchlist)
        })

        viewModel.movies.observe(viewLifecycleOwner, Observer { watchlist ->
            watchListAdapter.updateItems(watchlist)
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getWatchList()
        viewModel.getMovies()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            viewModel.getWatchList()
            viewModel.getMovies()

        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMovies()
    }

    private fun showMovieDetails(item: WatchList) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_ID, item.id)
        intent.putExtra(MOVIE_BACKDROP, item.backdropPath)
        intent.putExtra(MOVIE_POSTER, item.posterPath)
        intent.putExtra(MOVIE_TITLE, item.title)
        intent.putExtra(MOVIE_RATING, item.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, item.releaseDate)
        intent.putExtra(MOVIE_VOTECOUNT, item.voteCount)
        intent.putExtra(MOVIE_OVERVIEW, item.overview)
        startActivity(intent)
    }

}
