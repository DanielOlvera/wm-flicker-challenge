package com.example.wmflickerchallenge.model.data

import com.google.gson.annotations.SerializedName


data class FlickerResponse (

  @SerializedName("title"       ) var title       : String?          = null,
  @SerializedName("link"        ) var link        : String?          = null,
  @SerializedName("description" ) var description : String?          = null,
  @SerializedName("modified"    ) var modified    : String?          = null,
  @SerializedName("generator"   ) var generator   : String?          = null,
  @SerializedName("items"       ) var items       : ArrayList<Items> = arrayListOf()

)