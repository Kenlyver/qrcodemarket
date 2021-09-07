package com.example.qrcodemarket.data.model

import com.google.gson.annotations.SerializedName

object NextMarketId {
    data class Response(
        @SerializedName("error") val error: Boolean,
        @SerializedName("message") val message: String = "",
        @SerializedName("market") val nextId: String = ""
    )
}