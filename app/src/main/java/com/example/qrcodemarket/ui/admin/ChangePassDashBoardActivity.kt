package com.example.qrcodemarket.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.model.UpdatePassword
import com.example.qrcodemarket.data.network.InsertApi
import com.example.qrcodemarket.ui.auth.AppPreferences
import com.example.qrcodemarket.ui.auth.LoginActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_change_pass_dash_board.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_change_password.imgBackArrow
import kotlinx.android.synthetic.main.fragment_change_password.view.*

class ChangePassDashBoardActivity : AppCompatActivity() {

    var disposable: Disposable? = null
    var loginName: String? = null
    var currentPassword: String? = null
    var newPassword: String? = null
    var confirmPassword: String? = null
    val insertApi by lazy {
        InsertApi.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass_dash_board)

        val imageHand: ImageView =findViewById(R.id.imgHand)

        Glide.with(this)
            .load(R.drawable.hand)
            .into(imageHand)

        imgBackArrow.setOnClickListener {
            Intent(this, EditDashboardActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        btnChangePassword.setOnClickListener {
            currentPassword = edtOldPass.text.toString()
            newPassword = edtNewPass.text.toString()
            confirmPassword = edtConfirmPass.text.toString()
            if(currentPassword.isNullOrEmpty()){
                edtOldPass.requestFocus()
                edtOldPass.setError("Please enter your old password")
            }
            else if(newPassword.isNullOrEmpty()){
                edtNewPass.requestFocus()
                edtNewPass.setError("New password is request")
            }
            else if(confirmPassword.isNullOrEmpty()){
                edtConfirmPass.requestFocus()
                edtConfirmPass.setError("Number phone is request")
            }else updatePass()
        }
    }

    private fun updatePass() {
        if (!newPassword.equals(confirmPassword)) {
            edtConfirmPassword.requestFocus()
            edtConfirmPassword.setError("Password not match")
        } else {
            loginName = AppPreferences.username
            val dataPassword: UpdatePassword.Data
            dataPassword = UpdatePassword.Data(currentPassword!!,newPassword!!)
            disposable = insertApi.updatePassword(loginName!!, dataPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        if(result.error == false){
                            edtOldPass.setText("")
                            edtNewPass.setText("")
                            edtConfirmPass.setText("")
                            val builder = this?.let { AlertDialog.Builder(it) }
                            builder?.setTitle("Success!!")
                            builder?.setMessage("Password is change, logout ?")

                            builder?.setPositiveButton("Yes") { dialog, which ->
                                onClicksLogout()
                            }

                            builder?.setNegativeButton("No") { dialog, which ->

                            }
                            builder?.show()
                            Log.i("abc", "success: " + result.message)
                        }
                        else{
                            val builder = this?.let { AlertDialog.Builder(it) }
                            builder?.setTitle("Fail!!")
                            builder?.setMessage("Try again?")

                            builder?.setPositiveButton("Yes") { dialog, which ->

                            }

                            builder?.setNegativeButton("No") { dialog, which ->

                            }
                            builder?.show()
                        }

                    },
                    { error ->
                        Log.i("abc", "fail: " + error.localizedMessage + error.message + error)
                        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    }
                )
        }
    }

    private fun onClicksLogout() {
        AppPreferences.isLogin = false
        AppPreferences.username = ""
        AppPreferences.password = ""
        AppPreferences.role = ""
        AppPreferences.fullname = ""
        Intent(this, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

}