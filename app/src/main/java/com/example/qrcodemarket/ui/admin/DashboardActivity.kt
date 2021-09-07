package com.example.qrcodemarket.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.qrcodemarket.R
import com.example.qrcodemarket.ui.auth.AppPreferences
import com.example.qrcodemarket.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_setting.view.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        onClicksLogout()

        dashboard_adminName.setText(AppPreferences.fullname)

        marketManager.setOnClickListener {
            Intent(this, MarketManagerActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        statistical.setOnClickListener{
            Intent(this, StatisticalActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        profile.setOnClickListener {
            Intent(this, EditDashboardActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

    }

    private fun onClicksLogout() {
        imgAccount.setOnClickListener {
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
}