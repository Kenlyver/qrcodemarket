package com.example.qrcodemarket.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.qrcodemarket.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setViewPagerAdapter()
        setBottomNavigation()
        setViewPagerListener()

    }

    private fun setViewPagerListener() {
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageSelected(position: Int) {
                bottomNavigation.selectedItemId = when(position){
                    0 -> R.id.itemScan
                    1 -> R.id.itemHistory
                    2 -> R.id.itemSetting
                    else->R.id.itemScan
                }
            }
        })
    }

    private fun setBottomNavigation() {
        bottomNavigation.setOnNavigationItemReselectedListener {
            viewPager.currentItem= when(it.itemId){
                R.id.itemScan ->0
                R.id.itemHistory -> 1
                R.id.itemSetting -> 2
                else -> 0
            }
        }
    }

    private fun setViewPagerAdapter() {
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
    }

}