package com.example.qrcodemarket.data.model

import com.google.gson.annotations.SerializedName

object UpdatePassword {
    data class Response(
        @SerializedName("error") val error: Boolean,
        @SerializedName("message") val message: String = ""
    )
    data class Data(
        @SerializedName("currentPassword") val currentPassword:String = "",
        @SerializedName("newPassword") val newPassword:String = ""
    )
}