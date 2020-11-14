package com.example.cryptostation.activities

import android.os.Bundle
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.cryptostation.R
import com.example.cryptostation.adapters.MainViewPagerAdapter
import com.example.cryptostation.fragments.MarketFragment
import com.example.cryptostation.fragments.NewsFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var mViewPager: ViewPager
    private var mPageTitle = intArrayOf(1,2)
    private lateinit var mTabLayout: TabLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mPageTitle = (1,2)//
        mViewPager = findViewById(R.id.homepage_vp)
        mTabLayout = findViewById(R.id.homepage_tl)



        mViewPager.adapter = MainViewPagerAdapter(supportFragmentManager)
        mTabLayout.setupWithViewPager(mViewPager)

//        for (i:Int in 1..2){
//            mTabLayout.addTab(mTabLayout.newTab())
//        }
//
//        mTabLayout.tabGravity
//
//        val pageAdapter = MainViewPagerAdapter(supportFragmentManager)
//        mViewPager.adapter = pageAdapter
//        mViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mTabLayout))
//
//        mViewPager.offscreenPageLimit = 2
//        mTabLayout.addOnTabSelectedListener(object :
//            TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                mViewPager.currentItem = tab.position
//                when (tab.position) {
//                    1 ->
//                        mViewPager
//                    2 ->
//                        mViewPager
//                }
//
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab) {
//
//            }
//
//        })
    }
}