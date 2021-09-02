package com.example.qrcodemarket.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.model.*
import com.example.qrcodemarket.data.network.InsertApi
import com.example.qrcodemarket.data.network.MyApi
import com.example.qrcodemarket.data.network.response.NetworkConnectionInterceptor
import com.example.qrcodemarket.ui.auth.AppPreferences
import com.example.qrcodemarket.ui.home.HomeActivity
import com.example.qrcodemarket.util.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_history.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HistoryFragment : Fragment() {

    companion object{
        fun newInstance():HistoryFragment{
            return HistoryFragment()
        }
    }

    private lateinit var mView:View

    var citizenId:String? = null
    var date:String?=null
    var disposable : Disposable? = null

    val insertApi by lazy {
        InsertApi.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?)
            : View? {
        mView = inflater.inflate(R.layout.fragment_history,container,false)

        getAccessData()
        return mView.rootView
    }

    private fun showAccess(dataAccess:List<getDataAccess.Data>){

        mView.recycleHistory.layoutManager = LinearLayoutManager(context)
        mView.recycleHistory.adapter = AccessAdapter(dataAccess)
    }

    private fun getAccessData(){
        citizenId = AppPreferences.citizenId
        val intCitizenId = citizenId!!.toInt()
        disposable = insertApi.dataAccessMarket(intCitizenId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    showAccess(result.data)
                    Log.i("abc", "abc: " + result.data.toString())
                    Toast.makeText(context!!, "${result.message}", Toast.LENGTH_SHORT).show()
                },
                { error ->
                    Toast.makeText(context!!, "fail get", Toast.LENGTH_SHORT).show()
                    Log.i("abc", "abc: " + error.localizedMessage + error.message + error)
                    Toast.makeText(context!!, error.message, Toast.LENGTH_SHORT).show()
                }
            )
    }


    override fun onDetach() {
        Toast.makeText(context, "SS", Toast.LENGTH_SHORT).show()
        super.onDetach()
    }
    override fun onStart() {
        getAccessData()
        super.onStart()
    }
}