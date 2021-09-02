package com.example.qrcodemarket.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.example.qrcodemarket.util.toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


const val RC_SIGN_IN = 123
class LoginActivity : AppCompatActivity(),AuthListener,KodeinAware, GoogleApiClient.OnConnectionFailedListener {

    override val kodein by kodein()

    private val factory:AuthViewModelFactory by instance()

    var mGoogleApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreferences.init(this)

        if(AppPreferences.isLogin){
            Intent(this,HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
        else{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

            mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

            val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
            val viewModel = ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)


            binding.viewModel =viewModel
            viewModel.authListener = this

            binding.btnGoogleAccount.visibility = View.VISIBLE
            btnGoogleAccount.setOnClickListener {
                val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }

//        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
//
//        val api = MyApi(networkConnectionInterceptor)
//        val repository = UserRepository(api)
//        val factory = AuthViewModelFactory(repository)


    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user:User) {
        progress_bar.hide()
        var role:String = user.role.toString()
        var citizenId:String = user.citizenid.toString()
        AppPreferences.citizenId = citizenId
        Intent(this,HomeActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    override fun onFailure(message:String) {
        progress_bar.hide()
        login_layout.snackbar(message)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        Log.d("TAG", "handleSignInResult:" + result.isSuccess)
        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount
            Log.e("TAG", "display name: " + acct.displayName)
            val personName = acct.displayName
            signOut()
            Intent(this,HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        } else {
            Log.e("TAG", "display name: " + result.status)
            // Signed out, show unauthenticated UI.
        }
    }
    private fun signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
            object : ResultCallback<Status?> {
                override fun onResult(status: Status?) {

                }
            })
    }
    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("connect","Fail")
    }
}