package com.example.cryptostation.activities

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptostation.DetailActivity
import com.example.cryptostation.R
import com.example.cryptostation.SearchActivity
import com.example.cryptostation.SettingsActivity
import com.example.cryptostation.adapters.MainViewPagerAdapter
import com.example.cryptostation.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mViewPager: ViewPager2
    private var mPageTitle = intArrayOf(1, 2)
    private lateinit var mTabLayoutTop: TabLayout
    private lateinit var mBtnAddNew: ImageView

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewPager2
        mViewPager = binding.activityMainVp
        mTabLayoutTop = binding.activityMainTlTop
        mBtnAddNew = binding.activityMainBtnAddNew
//        mTabLayoutBottom = binding.activityMainTlBottom

        mViewPager.adapter = MainViewPagerAdapter(this)
        TabLayoutMediator(mTabLayoutTop, mViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Crypto"
                1 -> tab.text = "News"
            }
            mViewPager.setCurrentItem(tab.position, true)
        }.attach()
        // Changes tab color when switched
        // See https://stackoverflow.com/questions/55372259/how-to-use-tablayout-with-viewpager2-in-android
        mTabLayoutTop.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mTabLayoutTop.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.gold))
        mTabLayoutTop.setTabTextColors(
            ContextCompat.getColor(this, R.color.colorAccent),
            ContextCompat.getColor(this, R.color.colorAccent)
        )
        mTabLayoutTop

        mBtnAddNew.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        // Used for navigation drawer function
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        displayScreen(-1)

    }

    // Used to display navigation drawer
    fun displayScreen(id: Int) {

        // val fragment =  when (id){
        println("ID SLUT " + id)
        when (id) {

            R.id.nav_settings -> {
                val intent = Intent(applicationContext, SettingsActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Clicked Settings", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_support -> {
                Toast.makeText(this, "Clicked Support", Toast.LENGTH_SHORT).show()
            }

        }
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        displayScreen(item.itemId)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}