package com.example.qrcodemarket.data.model

import com.google.gson.annotations.SerializedName

object WardItem {
    data class Response(
        @SerializedName("data") val data: List<Data>
    )
    data class Data(
        @SerializedName("idDistrict") val idDistrict: Int = 0,
        @SerializedName("idCommune") val idWard: Int = 0,
        @SerializedName("name") val name: String = ""
    )
}