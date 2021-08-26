package com.example.qrcodemarket.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProvinceItem(
    @SerializedName("idProvince")
    @Expose
    val idProvince: String,
    @SerializedName("name")
    @Expose
    val name: String


)
