package com.example.cryptostation.models.settings

import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize



data class Setting(
    val icon: Int,
    val name: String,
    val action: Boolean
)
