package com.example.qrcodemarket.data.network

import com.example.qrcodemarket.data.model.InsertAccessMarket
import com.example.qrcodemarket.data.model.InsertUser
import com.example.qrcodemarket.data.model.UpdateUser
import com.example.qrcodemarket.data.model.getDataAccess
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface InsertApi {
    @POST("createuser")
    @Headers("Content-Type: application/json")
    fun userSignUp(@Body data: InsertUser.Data) : Observable<InsertUser.Response>

    @POST("addaccessmarket")
    @Headers("Content-Type: application/json")
    fun accessMarket(@Body data: InsertAccessMarket.Data) : Observable<InsertAccessMarket.Response>


    @GET("allaccess/{id}")
    @Headers("Content-Type: application/json")
    fun dataAccessMarket(@Path("id") citizenId:Int): Observable<getDataAccess.Response>

    @GET("allaccessmarket/{id}")
    @Headers("Content-Type: application/json")
    fun dataAccessMarketFlow(@Path("id") citizenId:Int): Observable<getDataAccess.Response>

    @GET("infouser/{loginName}")
    @Headers("Content-Type: application/json")
    fun getInfoUser(@Path("loginName") loginName:String): Observable<InsertUser.Response>

    @PUT("updateuser/{id}")
    @Headers("Content-Type: application/json")
    fun updateUser(
        @Path("id") id:Int,
        @Body data:UpdateUser.Data): Observable<UpdateUser.Response>

    companion object {
        fun create(): InsertApi {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.1.4:80/myapi/public/")
                .build()

            return retrofit.create(InsertApi::class.java)
        }
    }
}
