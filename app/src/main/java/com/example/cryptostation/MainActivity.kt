package com.example.cryptostation.activities

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptostation.DetailActivity
import com.example.cryptostation.R
import com.example.cryptostation.SearchActivity
import com.example.cryptostation.adapters.MainViewPagerAdapter
import com.example.cryptostation.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
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
    }
}