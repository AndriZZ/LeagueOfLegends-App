package com.mentormate.andriivanov.retrofit101.ui

import android.content.Intent
import android.net.Uri
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity

import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.v4.app.*
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.mentormate.andriivanov.retrofit101.R
import com.mentormate.andriivanov.retrofit101.db.LegendsRoomDatabase

class TabbedActivity : FragmentActivity(), ProfileFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, MasteriesFragment.OnFragmentInteractionListener, MatchesFragment.OnFragmentInteractionListener {
    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */


    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    internal var url: String? = null
    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null

    public override fun onDestroy() {

        super.onDestroy()

    }

    override fun onBackPressed() {
        val intent = NavUtils.getParentActivityIntent(this@TabbedActivity)


        startActivity(intent)

        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)


        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById<View>(R.id.container) as ViewPager?
        mViewPager!!.adapter = mSectionsPagerAdapter


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_tabbed, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


            when (position) {
                0 -> {
                    val profileFragment = ProfileFragment()

                    return profileFragment
                }
                1 -> {
                    val masteriesFragment = MasteriesFragment()
                    return masteriesFragment
                }
                2 -> {
                    val matchesFragment = MatchesFragment()

                    return matchesFragment
                }


                else -> return null
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }
}
