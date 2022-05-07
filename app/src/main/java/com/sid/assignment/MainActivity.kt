package com.sid.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gfg.article.materialtabs.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing the ViewPagerAdapter
        val adapter = ViewPagerAdapter(supportFragmentManager)

        //add fragment to the list
        adapter.addFragment(NowPlayingFragment(), "Now Playing")
        adapter.addFragment(PopularFragment(), "Popular")
        adapter.addFragment(TopRatedFragment(), "Top Rated")
        adapter.addFragment(UpcomingFragment(), "Upcoming")

        // Adding the Adapter to the ViewPager
        viewPager.adapter = adapter
        //bind the viewPager with the TabLayout.
        tabs.setupWithViewPager(viewPager)
    }
}