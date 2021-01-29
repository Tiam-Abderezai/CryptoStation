package com.example.cryptostation.activities

import android.os.Bundle
import android.text.SpannableString
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptostation.R
import com.example.cryptostation.adapters.MainViewPagerAdapter
import com.example.cryptostation.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var mViewPager: ViewPager2
    private var mPageTitle = intArrayOf(1, 2)
    private lateinit var mTabLayout: TabLayout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewPager2
        mViewPager = binding.activityMainVp
        mTabLayout = binding.activityMainTl

        mViewPager.adapter = MainViewPagerAdapter(this)
        TabLayoutMediator(mTabLayout, mViewPager) { tab, position ->
            when(position){
                0 -> tab.text = "Crypto"
                1 -> tab.text = "News"
            }
            mViewPager.setCurrentItem(tab.position,true)
        }.attach()
        // Changes tab color when switched
        // See https://stackoverflow.com/questions/55372259/how-to-use-tablayout-with-viewpager2-in-android
        mTabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.red))
        mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.green),
            ContextCompat.getColor(this, R.color.green))
        mTabLayout
    }
}