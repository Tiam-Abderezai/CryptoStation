package com.example.cryptostation.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.R
import com.example.cryptostation.adapters.settings.LanguageAdapter
import com.example.cryptostation.data.SharedPref
import com.example.cryptostation.models.settings.Language
import kotlinx.android.synthetic.main.list_language.*

class LanguageFragment : Fragment() {
    var mLanguageResult = ArrayList<Language>()
    var mLanguageList = ArrayList<Language>()
    var mCheckedList = ArrayList<Language>()
    private var mLanguageAdapter = LanguageAdapter(context, mLanguageResult)
    private var mRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("LANG CALLED")
        val view = inflater.inflate(R.layout.fragment_language, container, false)
        mRecyclerView = view.findViewById(R.id.frag_language_rv)
        val layoutManager = LinearLayoutManager(context)
        mRecyclerView?.layoutManager = layoutManager
        mRecyclerView?.adapter = mLanguageAdapter
        mLanguageAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        mRecyclerView?.itemAnimator = null
        val english = Language("English", false)
        val deutsch = Language("Deutsch", false)
        val espanol = Language("Español", false)
        val francais = Language("Français", false)
        val portugues = Language("Português", false)
        mLanguageList = arrayListOf(english, deutsch, espanol, francais, portugues)


        for (i: Int in 0..4) {
            mLanguageResult.add(mLanguageList[i])
            println("language: " + mLanguageList[i])
            mLanguageAdapter.notifyItemRangeChanged(0, mLanguageAdapter.itemCount)
//            mLanguageAdapter.notifyDataSetChanged()
        }


        return view
    }

}