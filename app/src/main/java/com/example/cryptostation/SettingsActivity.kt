package com.example.cryptostation

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptostation.data.SharedPref
import com.example.cryptostation.fragments.Callback
import com.example.cryptostation.fragments.settings.*


class SettingsActivity : AppCompatActivity(), Callback {
    val settingsFragment = SettingsFragment()
    val nightmodeFragment = NightmodeFragment()
    val securityFragment = SecurityFragment()
    val languageFragment = LanguageFragment()
    val notificationsFragment = NotificationsFragment()
//    lateinit var mSharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.activity_settings_fl, settingsFragment)
            commit()
        }


        val fragment: String? = intent?.getSerializableExtra("key_frag") as String?

        fragment?.let { openFragment(it) }


    }

    override fun openFragment(fragment: String) {
        supportFragmentManager.beginTransaction().apply {
            when (fragment) {
                "Night Mode" -> replace(R.id.activity_settings_fl, nightmodeFragment)
                "Security" -> replace(R.id.activity_settings_fl, securityFragment)
                "Language" -> replace(R.id.activity_settings_fl, languageFragment)
                "Notifications" -> replace(
                    R.id.activity_settings_fl,
                    notificationsFragment
                )
            }
            commit()
        }
    }


    // Used for language settings to change system language
//    override fun attachBaseContext(newBase: Context?) {
//        val sharedPref = SharedPref(newBase!!)
//        println("SAVED PREF: " + sharedPref.getLanguage(newBase!!).toString())
//        val selectedLanguage = sharedPref.getLanguage(newBase!!).toString()
//        super.attachBaseContext(LanguageContextWrapper.wrap(newBase!!, "Deutsch"))
//    }
}