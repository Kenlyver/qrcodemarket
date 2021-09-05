package com.example.qrcodemarket.data.network

import com.example.qrcodemarket.data.model.Access
import com.example.qrcodemarket.data.model.InsertUser
import com.example.qrcodemarket.data.network.response.AuthResponse
import com.example.qrcodemarket.data.network.response.NetworkConnectionInterceptor
import io.reactivex.Observable
import retrofit2.Call
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*


interface MyApi {
    @FormUrlEncoded
    @POST("userlogin")
    suspend fun userLogin(
        @Field("loginName") loginName:String,
        @Field("password") password:String
    ): Response<AuthResponse>

//    @FormUrlEncoded
//    @POST("createuser")
//    suspend fun userSignUp(
//        @Field("loginName") loginName:String,
//        @Field("password") password:String,
//        @Field("fullName") fullName:String,
//        @Field("dateOfBirth") dateOfBirth:String,
//        @Field("numberPhone") numberPhone:String
//    ):Response<AuthResponse>

    @PUT("updateaddress/{id}")
    fun updateAddress(
        @Path("id") userId:Int,
        @Body updateAddress: InsertUser
    ): Call<InsertUser>


    companion object{
        operator  fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ):MyApi{

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("http://192.168.1.4:80/myapi/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}
