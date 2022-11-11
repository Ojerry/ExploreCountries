package com.ojinc.explorecountries.data

import com.google.gson.annotations.SerializedName

data class Flags(
    @SerializedName("png")
    val png: String,
    val svg: String
)