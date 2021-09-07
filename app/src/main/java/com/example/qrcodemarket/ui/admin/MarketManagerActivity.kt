package com.example.qrcodemarket.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.model.AccessAdapter
import com.example.qrcodemarket.data.model.MarketAdapter
import com.example.qrcodemarket.data.model.getDataAccess
import com.example.qrcodemarket.data.model.getMarket
import com.example.qrcodemarket.data.network.InsertApi
import com.example.qrcodemarket.ui.auth.AppPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_market_manager.*
import kotlinx.android.synthetic.main.fragment_history.view.*

class MarketManagerActivity : AppCompatActivity() {

    var disposable : Disposable? = null

    val insertApi by lazy {
        InsertApi.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_manager)
        getMarketData()

        imgBackArrow.setOnClickListener {
            Intent(this, DashboardActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        btnAddMarket.setOnClickListener {
            Intent(this, AddMarketActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

    }

    private fun showMarket(dataMarket:List<getMarket.Data>){

        recycleMarket.layoutManager = LinearLayoutManager(this)
        recycleMarket.adapter = MarketAdapter(dataMarket)
    }

    private fun getMarketData(){
        disposable = insertApi.dataMarket()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    showMarket(result.data)
                    Log.i("abc", "abc: " + result.data.toString())
//                    Toast.makeText(context!!, "${result.message}", Toast.LENGTH_SHORT).show()
                },
                { error ->
//                    Toast.makeText(context!!, "fail get", Toast.LENGTH_SHORT).show()
                    Log.i("abc", "abc: " + error.localizedMessage + error.message + error)
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
            )
    }
}