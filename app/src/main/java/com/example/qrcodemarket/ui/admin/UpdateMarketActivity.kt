package com.example.qrcodemarket.ui.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.model.UpdateMarket
import com.example.qrcodemarket.data.model.UpdateUser
import com.example.qrcodemarket.data.network.InsertApi
import com.example.qrcodemarket.ui.auth.AppPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_update_market.*
import kotlinx.android.synthetic.main.activity_update_market.btnSaveChange
import kotlinx.android.synthetic.main.activity_update_market.imgBackArrow
import kotlinx.android.synthetic.main.fragment_account_setting.*


class UpdateMarketActivity : AppCompatActivity() {

    var marketId: String? = null
    var qrCodeManagementId:String? = null

    var disposable : Disposable? = null
    val insertApi by lazy {
        InsertApi.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_market)

        val imageQRIn: ImageView = findViewById(R.id.imgQRIn)
        val imageQROut: ImageView = findViewById(R.id.imgQROut)



        imgBackArrow.setOnClickListener {
            Intent(this, MarketManagerActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        val bundle: Bundle? = intent.extras
        marketId = bundle!!.getString("marketId")
        val marketName = bundle!!.getString("marketName")
        qrCodeManagementId = bundle!!.getString("qrCodeManagementId")
        val marketLocation = bundle!!.getString("marketLocation")
        val urlImageQRIn = bundle!!.getString("imageQRCodeIn")
        val urlImageQROut = bundle!!.getString("imageQRCodeOut")

        Glide.with(this)
            .load(urlImageQRIn)
            .into(imageQRIn)
        Glide.with(this)
            .load(urlImageQROut)
            .into(imageQROut)

        edtNameMarket.setText(marketName)
        edtMarketLocation.setText(marketLocation)
        edtImageQRIn.setText(urlImageQRIn)
        edtImageQROut.setText(urlImageQROut)

        btnSaveChange.setOnClickListener {
            updateMarket()
        }
    }

    private fun updateMarket(){
        val updateData: UpdateMarket.Data
        val marketName = edtNameMarket.text.toString()
        val marketLocation = edtMarketLocation.text.toString()
        val imageQRCodeIn = edtImageQRIn.text.toString()
        val imageQRCodeOut = edtImageQROut.text.toString()
        updateData= UpdateMarket.Data(marketName!!,marketLocation!!,imageQRCodeIn!!,imageQRCodeOut!!,qrCodeManagementId!!)
        val marketIdInt = marketId!!.toInt()
        disposable = insertApi.updateMarket(marketIdInt,updateData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    if(result.error==false){
                        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                        Intent(this, DashboardActivity::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(it)
                        }
                    }else Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    Log.i("abc", "success: " + result.message)
//                    Toast.makeText(context!!, "${result.message}", Toast.LENGTH_SHORT).show()
                },
                { error ->
//                    Toast.makeText(context!!, "fail get", Toast.LENGTH_SHORT).show()
                    Log.i("abc", "fail: " + error.localizedMessage + error.message + error)
                    Toast.makeText(this!!, error.message, Toast.LENGTH_SHORT).show()
                }
            )
    }
}