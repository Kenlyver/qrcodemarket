package com.example.qrcodemarket.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.qrcodemarket.R
import com.example.qrcodemarket.ui.auth.AppPreferences
import com.example.qrcodemarket.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.fragment_setting.view.*


class SettingFragment : Fragment() {
    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }

    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View? {
        mView = inflater.inflate(R.layout.fragment_setting, container, false)

        onClicksLogout()
        checkedSound()
        checkedVibrate()
        mView.txtAccountSetting.setOnClickListener {
            childFragmentManager.beginTransaction().add(R.id.settingFragment, AccountSettingFragment.newInstance())
                .addToBackStack(null).commit()

        }

        return mView.rootView
    }

    private fun checkedVibrate() {
        val switchButtonVibrate: com.suke.widget.SwitchButton = mView.findViewById(R.id.switch_button_vibrate)
        if (AppPreferences.checkvibrate == true) {
            switchButtonVibrate.setChecked(true)
        } else switchButtonVibrate.setChecked(false)
        switchButtonVibrate.isChecked()
        switchButtonVibrate.toggle() //switch state
        switchButtonVibrate.toggle(true) //switch without animation
        switchButtonVibrate.setShadowEffect(true) //disable shadow effect
        switchButtonVibrate.setEnableEffect(true) //disable the switch animation
        switchButtonVibrate.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                AppPreferences.checkvibrate = true
                switchButtonVibrate.setChecked(true)
            } else {
                AppPreferences.checkvibrate = false
                switchButtonVibrate.setChecked(false)
            }
        }
    }

    private fun checkedSound() {
        val switchButtonSound: com.suke.widget.SwitchButton = mView.findViewById(R.id.switch_button_sound)
        if (AppPreferences.checksound == true) {
            switchButtonSound.setChecked(true)
        } else switchButtonSound.setChecked(false)
        switchButtonSound.isChecked()
        switchButtonSound.toggle() //switch state
        switchButtonSound.toggle(true) //switch without animation
        switchButtonSound.setShadowEffect(true) //disable shadow effect
        switchButtonSound.setEnableEffect(true) //disable the switch animation
        switchButtonSound.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                AppPreferences.checksound = true
                switchButtonSound.setChecked(true)
            } else {
                AppPreferences.checksound = false
                switchButtonSound.setChecked(false)
            }
        }
    }


    private fun onClicksLogout() {
        mView.btnLogout.setOnClickListener {
            AppPreferences.isLogin = false
            AppPreferences.username = ""
            AppPreferences.password = ""
            AppPreferences.role = ""
            AppPreferences.fullname = ""
            Intent(activity, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }

}