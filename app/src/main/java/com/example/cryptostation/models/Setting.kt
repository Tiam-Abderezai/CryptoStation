package com.example.cryptostation.models

import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "settings_table")
data class Setting(
    @PrimaryKey(autoGenerate = true)
    val settingId: Int,
    val icon: Int,
    val name: String,
    val action: Boolean
)
