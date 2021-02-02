package com.example.cryptostation.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.cryptostation.R

// Handles the SharedPreferences' operations

class SharedPref {
    private var mPref: SharedPreferences
    private var mContext: Context
    private var fiatPosition: Int = 0

    //    private val fiatArray = Constants.fiatArrayName
    private val PREF_DURATION: String = "duration"
    private val PREF_FIAT: String = "fiat"

    constructor(context: Context) {
        this.mContext = context
        // 1st parameter is the file name
        // 2nd parameter allows only the app to read/write the preference
        mPref = context.getSharedPreferences(
            context.getResources().getString(R.string.preference_value),
            Context.MODE_PRIVATE
        )
    }

    // Save the SharedPreferences status (true/false)
    fun writePrefStatus(status: Boolean) {
        val editor = mPref.edit()
        // 1st parameter is the key for the data
        // 2nd parameter is the boolean status
        editor.putBoolean(
            mContext.resources.getString(R.string.preference_status),
            status
        )
        // Saves the changes
        editor.commit();
    }

    fun readPrefStatus(): Boolean {
        // Default value
        var status: Boolean = false
        // 1st parameter is the key for the data
        // 2nd parameter is the default value (false)
        status =
            mPref.getBoolean(mContext.resources.getString(R.string.preference_status), false)
        return status;
    }

    fun getSharedPref(ctx: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }


    fun setDuration(ctx: Context, duration: Int) {
        val editor: SharedPreferences.Editor = getSharedPref(ctx).edit()
        editor.putInt(PREF_DURATION, duration)
        editor.commit()
    }

    fun getDuration(ctx: Context): Int {
        return getSharedPref(ctx).getInt(PREF_DURATION, 0)
    }

    fun setFiat(ctx: Context, fiat: Int) {
        val editor: SharedPreferences.Editor = getSharedPref(ctx).edit()
        editor.putInt(PREF_FIAT, fiat)
        editor.commit()
    }

    fun getFiat(ctx: Context): Int {
        return getSharedPref(ctx).getInt(PREF_FIAT, 0)
    }
}