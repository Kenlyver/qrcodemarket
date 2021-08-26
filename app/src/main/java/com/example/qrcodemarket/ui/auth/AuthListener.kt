package com.example.qrcodemarket.ui.auth

import androidx.lifecycle.LiveData
import com.example.qrcodemarket.data.network.response.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message:String)
}