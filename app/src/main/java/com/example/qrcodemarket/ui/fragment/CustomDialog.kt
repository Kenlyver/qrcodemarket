package com.example.qrcodemarket.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.qrcodemarket.R

class CustomDialog:DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView:View = inflater.inflate(R.layout.layout_popup,container,false)
        val imageCheck: ImageView =rootView.findViewById(R.id.checkgif)
        Glide.with(this)
            .load(R.drawable.checkgif)
            .into(imageCheck);
        return rootView
    }
}