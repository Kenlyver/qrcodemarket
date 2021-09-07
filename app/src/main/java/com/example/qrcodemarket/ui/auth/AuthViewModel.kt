package com.example.qrcodemarket.ui.auth

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import com.example.qrcodemarket.data.respositories.UserRepository
import com.example.qrcodemarket.util.ApiExceptions
import com.example.qrcodemarket.util.Coroutines
import com.example.qrcodemarket.util.NoInternetException


class AuthViewModel(
    private val repository:UserRepository
):ViewModel() {


    var loginName:String? = null
    var password:String?= null
    var fullName:String? = null
    var dateOfBirth:String? = null
    var numberPhone:String? = null

    var authListener:AuthListener? =null


    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if(loginName.isNullOrEmpty() || password.isNullOrEmpty() ){
            authListener?.onFailure("Invalid login name or password ")
            return
        }
        Coroutines.main {
        try {
            val authResponse = repository.userLogin(loginName!!,password!!)

            authResponse.user?.let {
                authListener?.onSuccess(it)
                AppPreferences.isLogin = true
                AppPreferences.username = loginName as String
                AppPreferences.password = password as String
                AppPreferences.role = authResponse.user.role as String
                return@main
            }
            authListener?.onFailure(authResponse.message!!)
        }catch (e:ApiExceptions){
            authListener?.onFailure(e.message!!)
        }catch (e:NoInternetException){
            authListener?.onFailure(e.message!!)
        }
        }
    }

    fun onSignUp(view: View){
        Intent(view.context,RegisterActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onLogin(view: View){
        Intent(view.context,LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignUpButtonClick(view: View){
        authListener?.onStarted()
        if(fullName.isNullOrEmpty()){
            authListener?.onFailure("Full name is request")
            return
        }
        if(dateOfBirth.isNullOrEmpty()){
            authListener?.onFailure("Date of Birth is request")
            return
        }
        if(numberPhone.isNullOrEmpty()){
            authListener?.onFailure("Number phone is request")
            return
        }
        if(loginName.isNullOrEmpty()){
            authListener?.onFailure("Login name is request")
            return
        }
        if(password.isNullOrEmpty()){
            authListener?.onFailure("Please enter your password")
            return
        }
    }
}
