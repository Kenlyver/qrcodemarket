package com.example.qrcodemarket.data.network.response

data class AuthResponse(
    val isSuccessful:Boolean?,
    val message:String?,
    val user: User?
)