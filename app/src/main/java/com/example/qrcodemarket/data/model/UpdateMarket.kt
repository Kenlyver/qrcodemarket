package com.example.qrcodemarket.data.model

import com.google.gson.annotations.SerializedName

object UpdateMarket {
    data class Response(
        @SerializedName("error") val error: Boolean,
        @SerializedName("message") val message: String = ""
    )

    data class Data(
        @SerializedName("marketName") val marketName:String = "",
        @SerializedName("marketLocation") val marketLocation:String = "",
        @SerializedName("imageQRCodeIn") val imageQRCodeIn:String = "",
        @SerializedName("imageQRCodeOut") val imageQRCodeOut:String = "",
        @SerializedName("qrCodeManagementId") val qrCodeManagementId:String = ""
    )
}