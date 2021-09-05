package com.example.qrcodemarket.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qrcodemarket.R



class ChangePasswordFragment : Fragment() {
    companion object{
        fun newInstance():ChangePasswordFragment{
            return ChangePasswordFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_change_password, container, false)

        return mView.rootView
    }

}