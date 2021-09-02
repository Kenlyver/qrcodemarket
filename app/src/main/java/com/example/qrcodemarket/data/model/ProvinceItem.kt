package com.example.qrcodemarket.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

object ProvinceItem{
    data class Response(
        @SerializedName("data") val data: List<Data>
    )
    data class Data(
        @SerializedName("idProvince") val id: Int = 0,
        @SerializedName("name") val name: String = ""
    )

}
