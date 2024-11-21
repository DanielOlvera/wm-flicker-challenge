package com.example.wmflickerchallenge.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media (

  @SerializedName("m" ) var m : String? = null

): Parcelable