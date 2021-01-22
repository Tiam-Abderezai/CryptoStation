package com.example.cryptostation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptostation.fragments.CoinFragment
import com.example.cryptostation.fragments.NewsFragment

class MainViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

//    override fun getCount(): Int {
//        return 2
//    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CoinFragment()
            else -> return NewsFragment()
        }


//        if (position == 0) {
//            return fragMarket
//        } else {
//            return fragNews
//        }
    }

//    override fun getItem(position: Int): Fragment {
//

//    }
//    override fun getPageTitle(position: Int): CharSequence? {
//        // Spannable used to change color of page title
//        var spannableCoins = SpannableString("Coins")
//        var spannableNews = SpannableString("News")
//
//
//
//        when(position){
//            0 -> {
//                spannableCoins.setSpan(
//                    ForegroundColorSpan(Color.BLUE), 0, 5,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//                return spannableCoins
//            }
//            1 -> {
//                spannableNews.setSpan(
//                    ForegroundColorSpan(Color.BLUE), 0, 4,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//                return spannableNews}
//        }
//        return super.getPageTitle(position)
//    }


}