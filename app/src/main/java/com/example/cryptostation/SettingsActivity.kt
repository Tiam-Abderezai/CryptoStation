package com.example.cryptostation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptostation.adapters.SettingsAdapter
import com.example.cryptostation.models.Setting
import com.example.cryptostation.viewmodel.CoinViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.ArrayList

class SettingsActivity : AppCompatActivity() {
    var mSettingsResult = ArrayList<Setting>()
    var mSettingsList = ArrayList<Setting>()

    var mSettingsAdapter = SettingsAdapter(this, mSettingsResult)
    private var mRecyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        mRecyclerView = settings_rv
        val layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView?.layoutManager = layoutManager
        mRecyclerView?.adapter = mSettingsAdapter
        mSettingsAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        mRecyclerView?.itemAnimator = null
        val nightModeRes = ContextCompat.getDrawable(applicationContext, R.drawable.ic_star_empty)

//        detail_star.setImageDrawable(starred)
        var nightMode = Setting(0,R.drawable.ic_settings_nightmode , "night-mode", true)
        var security = Setting(1, R.drawable.ic_settings_security, "security", false)
        var language = Setting(2, R.drawable.ic_settings_language, "language", false)
        var notifications = Setting(3, R.drawable.ic_settings_notifications, "notifications", false)
        mSettingsList = arrayListOf(nightMode, security, language, notifications)
        for (i: Int in 0..3) {
            mSettingsResult.add(mSettingsList[i])
            mSettingsAdapter.notifyItemRangeChanged(0, mSettingsAdapter.itemCount)

        }


    }
}