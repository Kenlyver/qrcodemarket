package com.example.qrcodemarket.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.qrcodemarket.R
import com.example.qrcodemarket.ui.auth.AppPreferences
import com.example.qrcodemarket.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_scan.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    companion object{
        fun newInstance():SettingFragment{
            return SettingFragment()
        }
    }
    private lateinit var mView:View
    override fun onCreateView(
        inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?)
            : View? {
        mView = inflater.inflate(R.layout.fragment_setting,container,false)
        onClicks()
        return mView.rootView
    }

    private fun onClicks() {
        mView.btnLogout.setOnClickListener {
            AppPreferences.isLogin = false
            AppPreferences.username = ""
            AppPreferences.password = ""
        }
    }
}