package com.example.qrcodemarket.data.respositories

import com.example.qrcodemarket.data.network.MyApi
import com.example.qrcodemarket.data.network.response.AuthResponse
import com.example.qrcodemarket.data.network.response.SafeApiRequest
import retrofit2.Response

class UserRepository(
    private val api:MyApi
):SafeApiRequest() {

    suspend fun userLogin(loginName:String,password:String):AuthResponse{
        return apiRequest { api.userLogin(loginName,password)}
    }

    suspend fun userSignUp(loginName: String,password: String,fullName: String,dateOfBirth: String,numberPhone: String):AuthResponse{
        return apiRequest { api.userSignUp(loginName, password, fullName, dateOfBirth, numberPhone) }
    }
}