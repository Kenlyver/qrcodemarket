package com.example.qrcodemarket.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.model.InsertAccessMarket
import com.example.qrcodemarket.data.model.InsertUser
import com.example.qrcodemarket.data.network.InsertApi
import com.example.qrcodemarket.ui.auth.AppPreferences
import com.example.qrcodemarket.util.toast
import com.google.zxing.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_scan.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.text.SimpleDateFormat
import java.util.*


class ScanFragment : Fragment(),ZXingScannerView.ResultHandler {

    companion object{
        fun newInstance():ScanFragment{
            return ScanFragment()
        }
    }
    private var checkInMarket:Boolean? = false
    private lateinit var mView:View
    private lateinit var scannerView : ZXingScannerView
    var disposable : Disposable? = null

    val insertApi by lazy {
        InsertApi.create()
    }

    var citizenId:String? = null
    var marketId:String? = null
    var timeIn:String? = null
    var timeOut:String? = null
    var date:String? = null
    var idMarketIn:String?=null

    override fun onCreateView(
        inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        mView = inflater.inflate(R.layout.fragment_scan,container,false)
        initializeQRScanner()
        onClicks()

        return mView.rootView
    }

    private fun onClicks() {
        mView.imgFlash.setOnClickListener {
            if(it.isSelected){
               offFlashLight()
            }else{
                onFlashLight()
            }
        }
    }

    private fun offFlashLight() {
        mView.imgFlash.isSelected = false
        scannerView.flash = false
    }

    private fun onFlashLight() {
        mView.imgFlash.isSelected = true
        scannerView.flash = true
    }

    private fun initializeQRScanner() {
        scannerView = ZXingScannerView(context!!)
        scannerView.setBackgroundColor(ContextCompat.getColor(context!!,R.color.colorGrey))
        scannerView.setBorderColor(ContextCompat.getColor(context!!,R.color.blue))
        scannerView.setLaserColor(ContextCompat.getColor(context!!,R.color.blue))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setAutoFocus(true)
        scannerView.setResultHandler(this)
        scannerView.setSquareViewFinder(true)
        mView.containerScan.addView(scannerView)
        startQrCamera()
    }

    private fun startQrCamera(){
        scannerView.startCamera()
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this) // Register ourselves as a handler for scan results.
        scannerView.startCamera() // Start camera on resume
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera() // Stop camera on pause
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        var result:String = rawResult?.text.toString()
        val inString:String = "In"
        val outString:String = "Out"
        val checkIn = result.endsWith(inString)
        val checkOut = result.endsWith(outString)
        if(checkIn){
            timeIn = getCurrentTime()
            idMarketIn = result.get(0).toString()
            var dialog = CustomDialog()
            dialog.show(childFragmentManager,"custom")
            checkInMarket = true
        }
        else if(checkInMarket == true && checkOut){
            timeOut = getCurrentTime()
            date = getCurrentDate()
            citizenId =AppPreferences.citizenId
            marketId = result.get(0).toString()
            if(idMarketIn.equals(marketId)){
                insertAccessData()
                Toast.makeText(context!!,"See you again later",Toast.LENGTH_SHORT).show()
                checkInMarket = false
            }
            else{
                Toast.makeText(context!!,"You scan wrong QRcode in this market",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(context!!,"You scan wrong QRcode in this market",Toast.LENGTH_SHORT).show()
        }

        Handler().postDelayed({
            scannerView.resumeCameraPreview(this)
        }, 10000)


    }

    fun getCurrentTime():String{
        val sdf = SimpleDateFormat("HH:mm:ss")
        return sdf.format(Date())
    }

    fun getCurrentDate():String{
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        return sdf.format(Date())
    }

    private fun insertAccessData(){
        val insertAccess: InsertAccessMarket.Data
        insertAccess= InsertAccessMarket.Data(citizenId!!,marketId!!,timeIn!!,timeOut!!,date!!)
        disposable = insertApi.accessMarket(insertAccess)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i("abc", "abc: " + result.data.toString())
                    Toast.makeText(context!!, "${result.message}", Toast.LENGTH_SHORT).show()
                },
                { error ->
                    Toast.makeText(context!!, "fail insert", Toast.LENGTH_SHORT).show()
                    Log.i("abc", "abc: " + error.localizedMessage + error.message + error)
                    Toast.makeText(context!!, error.message, Toast.LENGTH_SHORT).show()
                }
            )
    }


}