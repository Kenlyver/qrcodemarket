package com.example.qrcodemarket.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.network.response.User
import com.example.qrcodemarket.databinding.ActivityLoginBinding
import com.example.qrcodemarket.ui.home.HomeActivity
import com.example.qrcodemarket.util.hide
import com.example.qrcodemarket.util.show
import com.example.qrcodemarket.util.snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(),AuthListener,KodeinAware {

    override val kodein by kodein()

    private val factory:AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
//
//        val api = MyApi(networkConnectionInterceptor)
//        val repository = UserRepository(api)
//        val factory = AuthViewModelFactory(repository)



        val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)

        binding.viewModel =viewModel
        viewModel.authListener = this

        val txtSignUp:TextView = findViewById(R.id.txtSingUp)
        txtSignUp.setOnClickListener {

        }
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user:User) {
        progress_bar.hide()
        Intent(this,HomeActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
//        login_layout.snackbar("${user.fullName} is logged in")
//        Toast.makeText(this,"${user.fullName} is logged in",Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message:String) {
        progress_bar.hide()
        login_layout.snackbar(message)
    }
}