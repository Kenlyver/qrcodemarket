package com.example.qrcodemarket.data.model

import com.google.gson.annotations.SerializedName

object UpdateUser {
    data class Response(
        @SerializedName("error") val error: Boolean,
        @SerializedName("message") val message: String = "",
        @SerializedName("user") val data: InsertUser.Data
    )
    data class Data(
        @SerializedName("fullName") val fullName:String = "",
        @SerializedName("dateOfBirth") val dateOfBirth:String = "",
        @SerializedName("address") val address:String = "",
        @SerializedName("numberPhone") val numberPhone:String = ""

    )
}