package com.example.qrcodemarket.data.network


import com.example.qrcodemarket.data.model.DistrictItem
import com.example.qrcodemarket.data.model.ProvinceItem
import com.example.qrcodemarket.data.model.WardItem
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiProvince {
    @GET("4b531356-3ddf-4c82-90c0-3556105fafef") // province
    @Headers("Content-Type: application/json")
    fun getDataProvince(): Observable<ProvinceItem.Response>

    @GET("6267c4b7-a620-4590-a48a-05ebf625e09a")// district
    @Headers("Content-Type: application/json")
    fun getDataDistrict(): Observable<DistrictItem.Response>

    @GET("ea8e3f62-957c-4040-a7a5-051e115b3392")// district
    @Headers("Content-Type: application/json")
    fun getDataWard(): Observable<WardItem.Response>

//    @GET("67e490d1-0865-46cd-a367-f774909c63d0")// ward
//    @Headers("Content-Type: application/json")
//    fun getDataWard(): Observable<WardItem.Response>

    companion object {
        fun create(): ApiProvince {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://mocki.io/v1/")
                .build()

            return retrofit.create(ApiProvince::class.java)
        }
}
}