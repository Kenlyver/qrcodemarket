package com.example.qrcodemarket.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.network.response.User
import com.example.qrcodemarket.databinding.ActivityRegisterBinding
import com.example.qrcodemarket.ui.home.CountryActivity
import com.example.qrcodemarket.util.snackbar
import kotlinx.android.synthetic.main.activity_register.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class RegisterActivity : AppCompatActivity(),AuthListener,KodeinAware {

    override val kodein by kodein()
    private val factory:AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        val viewModel = ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)

        binding.viewModel =viewModel
        viewModel.authListener = this


    }

    override fun onStarted() {
    }

    override fun onSuccess(user: User) {
        Intent(this, CountryActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    override fun onFailure(message:String) {
        register_layout.snackbar(message)
    }
}