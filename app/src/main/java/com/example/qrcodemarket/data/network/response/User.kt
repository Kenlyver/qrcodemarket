package com.example.qrcodemarket.data.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity
data class User (
    var citizenid: Int? = null,
    var loginName: String? = null,
    var fullName:String? = null,
    var dateOfBirth:String? = null,
    var numberPhone:String? = null,
    var residence:String? = null,
    var role:String? = null
){
    @PrimaryKey(autoGenerate = false)
    var uid:Int = CURRENT_USER_ID
}