package com.example.cryptostation.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.R
import com.example.cryptostation.adapters.settings.SettingsAdapter
import com.example.cryptostation.models.settings.Setting

class SettingsFragment : Fragment() {
    var mSettingsResult = ArrayList<Setting>()
    var mSettingsList = ArrayList<Setting>()
    var mSettingsAdapter = SettingsAdapter(context, mSettingsResult)
    private var mRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        mRecyclerView = view.findViewById(R.id.frag_settings_rv)
        val layoutManager = LinearLayoutManager(context)
        mRecyclerView?.layoutManager = layoutManager
        mRecyclerView?.adapter = mSettingsAdapter
        mSettingsAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        mRecyclerView?.itemAnimator = null

        val nightMode =
            Setting(R.drawable.ic_settings_nightmode, getString(R.string.nightmode), true)
        val security = Setting(R.drawable.ic_settings_security, getString(R.string.security), false)
        val language = Setting(R.drawable.ic_settings_language, getString(R.string.language), false)
        val notifications =
            Setting(R.drawable.ic_settings_notifications, getString(R.string.notifications), false)
        mSettingsList = arrayListOf(nightMode, security, language, notifications)
        for (i: Int in 0..3) {
            mSettingsResult.add(mSettingsList[i])
            println("looping" + mSettingsList[i])
            mSettingsAdapter.notifyItemRangeChanged(0, mSettingsAdapter.itemCount)

        }
        return view
    }

}