package com.example.cryptostation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
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
            tab.text = "OBJECT ${position + 1}"
        }.attach()

    }
}