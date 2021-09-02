package com.example.qrcodemarket.data.model

import com.google.gson.annotations.SerializedName

object InsertUser {
    data class Response(
        @SerializedName("error") val error: Boolean,
        @SerializedName("message") val message: String = "",
        @SerializedName("user") val data: Data
    )

    data class Data(
        @SerializedName("loginName") val loginName:String = "",
        @SerializedName("password") val password:String = "",
        @SerializedName("fullName") val fullName:String = "",
        @SerializedName("dateOfBirth") val dateOfBirth:String = "",
        @SerializedName("numberPhone") val numberPhone:String = "",
        @SerializedName("province") val province:String = "",
        @SerializedName("district") val district:String = "",
        @SerializedName("ward") val ward:String = "",
        @SerializedName("address") val address:String = ""
    )
}