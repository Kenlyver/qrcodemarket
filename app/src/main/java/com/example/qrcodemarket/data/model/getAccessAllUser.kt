package com.example.qrcodemarket.data.model

import com.google.gson.annotations.SerializedName

object getAccessAllUser {
    data class Response(
        @SerializedName("error") val error: Boolean,
        @SerializedName("message") val message: String = "",
        @SerializedName("access") val data: List<Data> = listOf()
    )

    data class Data(
        @SerializedName("fullName") val fullName:String = "",
        @SerializedName("marketName") val marketName:String = "",
        @SerializedName("timeIn") val timeIn:String = "",
        @SerializedName("timeOut") val timeOut:String = "",
        @SerializedName("date") val date:String = ""
    )
}