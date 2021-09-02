package com.example.qrcodemarket.data.model

import com.google.gson.annotations.SerializedName

object DistrictItem {
    data class Response(
        @SerializedName("data") val data: List<Data>
    )
    data class Data(
        @SerializedName("idProvince") val idProvince: Int = 0,
        @SerializedName("idDistrict") val idDistrict: Int = 0,
        @SerializedName("name") val name: String = ""
    )
}