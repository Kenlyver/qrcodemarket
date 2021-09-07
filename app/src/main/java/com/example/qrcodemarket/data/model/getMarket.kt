package com.example.qrcodemarket.data.model

import com.google.gson.annotations.SerializedName

object getMarket {
    data class Response(
        @SerializedName("error") val error: Boolean,
        @SerializedName("markets") val data: List<Data> = listOf()
    )

    data class Data(
        @SerializedName("marketName") val marketName:String = "",
        @SerializedName("marketLocation") val marketLocation:String = ""

    )
}