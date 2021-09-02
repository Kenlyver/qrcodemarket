package com.example.qrcodemarket.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.qrcodemarket.ui.fragment.HistoryFragment
import com.example.qrcodemarket.ui.fragment.ScanFragment
import com.example.qrcodemarket.ui.fragment.SettingFragment

class MainPagerAdapter(var fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position:Int):Fragment {
        return when(position){
            0 -> ScanFragment.newInstance()
            1 -> HistoryFragment.newInstance()
            2 -> SettingFragment.newInstance()
            else -> ScanFragment.newInstance()
        }
    }
}