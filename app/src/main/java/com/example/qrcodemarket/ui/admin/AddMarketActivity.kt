package com.example.qrcodemarket.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.model.InsertAccessMarket
import com.example.qrcodemarket.data.model.InsertMarket
import com.example.qrcodemarket.data.network.InsertApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_market.*
import kotlinx.android.synthetic.main.activity_add_market.imgBackArrow
import kotlinx.android.synthetic.main.fragment_change_password.*

class AddMarketActivity : AppCompatActivity() {

    var marketName:String? = null
    var marketLocation:String? = null
    var qrCodeIn:String? = null
    var qrCodeOut:String? = null

    var disposable: Disposable? = null

    val insertApi by lazy {
        InsertApi.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_market)

        imgBackArrow.setOnClickListener {
            Intent(this, MarketManagerActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
        nextMarketId()
        onAddMarketClick()

    }

    private fun onAddMarketClick() {
        btnSaveChange.setOnClickListener {
            marketName = edtNameMarket.text.toString()
            marketLocation = edtLocation.text.toString()
            qrCodeIn = edtUrlQRIn.text.toString()
            qrCodeOut = edtUrlQROut.text.toString()
            if (marketName.isNullOrEmpty()) {
                edtNameMarket.requestFocus()
                edtNameMarket.setError("Please enter name's new market ")
            } else if (marketLocation.isNullOrEmpty()) {
                edtLocation.requestFocus()
                edtLocation.setError("Location is request")
            } else if (qrCodeIn.isNullOrEmpty()) {
                edtUrlQRIn.requestFocus()
                edtUrlQRIn.setError("Url image qrcode market is request")
            } else if (qrCodeOut.isNullOrEmpty()) {
                edtUrlQROut.requestFocus()
                edtUrlQROut.setError("Url image qrcode market is request")
            } else insertMarket()
        }
    }

    private fun insertMarket() {
        val insertMarket: InsertMarket.Data
        insertMarket = InsertMarket.Data(marketName!!,marketLocation!!,qrCodeIn!!,qrCodeOut!!)
        disposable = insertApi.addMarket(insertMarket)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Intent(this, MarketManagerActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                    Log.i("abc", "abc: " + result.data.toString())
                    Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
                },
                { error ->
                    Toast.makeText(this, "fail insert", Toast.LENGTH_SHORT).show()
                    Log.i("abc", "abc: " + error.localizedMessage + error.message + error)
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
            )
    }

    private fun nextMarketId() {
        disposable = insertApi.nextMarketId()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val idNext:String = result.nextId
                    var nextId:Int = idNext.toInt()
                    nextId = nextId+1
                    txtIdMarketNext.setText(nextId.toString())
                    Log.i("abc", "abc: " + result.toString())
//                    Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
                },
                { error ->
                    Toast.makeText(this, "fail insert", Toast.LENGTH_SHORT).show()
                    Log.i("abc", "abc: " + error.localizedMessage + error.message + error)
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
            )
    }



}