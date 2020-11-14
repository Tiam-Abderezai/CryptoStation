package com.example.cryptostation.adapters

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.cryptostation.R
import com.example.cryptostation.fragments.MarketFragment
import com.example.cryptostation.fragments.NewsFragment

class MainViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {

        when (position) {
            0 -> return MarketFragment()
            else -> return NewsFragment()
        }


//        if (position == 0) {
//            return fragMarket
//        } else {
//            return fragNews
//        }
    }
    override fun getPageTitle(position: Int): CharSequence? {
        // Spannable used to change color of page title
        var spannableCoins = SpannableString("Coins")
        var spannableNews = SpannableString("News")



        when(position){
            0 -> {
                spannableCoins.setSpan(
                    ForegroundColorSpan(Color.BLUE), 0, 5,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                return spannableCoins
            }
            1 -> {
                spannableNews.setSpan(
                    ForegroundColorSpan(Color.BLUE), 0, 4,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                return spannableNews}
        }
        return super.getPageTitle(position)
    }
}