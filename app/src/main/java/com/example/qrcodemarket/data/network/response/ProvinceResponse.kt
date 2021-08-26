package com.example.qrcodemarket.data.network.response

data class ProvinceResponse(
    val data: List<ProvinceItem>,
    val isSuccessful:Boolean?,
    val message:String?
)