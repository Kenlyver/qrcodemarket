package com.example.qrcodemarket.data.network


import com.example.qrcodemarket.data.network.response.ProvinceItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface ApiProvince {
    @GET("province")
    fun getData():Call<List<ProvinceItem>>
}