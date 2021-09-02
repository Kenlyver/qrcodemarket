package com.example.qrcodemarket.data.model

import com.google.gson.annotations.SerializedName

object getDataAccess {
    data class Response(
        @SerializedName("error") val error: Boolean,
        @SerializedName("message") val message: String = "",
        @SerializedName("access") val data: List<Data> = listOf()
    )

    data class Data(
        @SerializedName("citizenId") val citizenId:String = "",
        @SerializedName("marketName") val marketName:String = "",
        @SerializedName("timeIn") val timeIn:String = "",
        @SerializedName("timeOut") val timeOut:String = "",
        @SerializedName("date") val date:String = "",
        @SerializedName("location") val location:String = ""
    )
}