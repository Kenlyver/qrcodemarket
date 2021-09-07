package com.example.qrcodemarket.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.model.AccessAdapter
import com.example.qrcodemarket.data.model.getDataAccess
import com.example.qrcodemarket.data.network.InsertApi
import com.example.qrcodemarket.ui.auth.AppPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_history.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
        setHasOptionsMenu(true)
        val toolbar: Toolbar = mView.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.show()
        loop()
        return mView.rootView
    }

    private fun showAccess(dataAccess:List<getDataAccess.Data>){

        mView.recycleHistory.layoutManager = LinearLayoutManager(context)
        mView.recycleHistory.adapter = AccessAdapter(dataAccess)

    }

    private fun getAccessDataFlowMarket(){
        citizenId = AppPreferences.citizenId
        val intCitizenId = citizenId!!.toInt()
        disposable = insertApi.dataAccessMarketFlow(intCitizenId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    showAccess(result.data)
                    Log.i("abc", "abc: " + result.data.toString())
//                    Toast.makeText(context!!, "${result.message}", Toast.LENGTH_SHORT).show()
                },
                { error ->
//                    Toast.makeText(context!!, "fail get", Toast.LENGTH_SHORT).show()
                    Log.i("abc", "abc: " + error.localizedMessage + error.message + error)
                    Toast.makeText(context!!, error.message, Toast.LENGTH_SHORT).show()
                }
            )
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
//                    Toast.makeText(context!!, "${result.message}", Toast.LENGTH_SHORT).show()
                },
                { error ->
//                    Toast.makeText(context!!, "fail get", Toast.LENGTH_SHORT).show()
                    Log.i("abc", "abc: " + error.localizedMessage + error.message + error)
                    Toast.makeText(context!!, error.message, Toast.LENGTH_SHORT).show()
                }
            )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == R.id.Date) {
            getAccessData()
            return true;
        } else if (item.getItemId() == R.id.Market) {
            getAccessDataFlowMarket()
            return true;
        }

        return super.onOptionsItemSelected(item);
        }

    override fun onStart() {
        getAccessData()
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getAccessData()
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        getAccessData()
        super.onResume()

    }
    private fun loop() {
        CoroutineScope(IO).launch {
            delay(5000)
            CoroutineScope(Main).launch {
                getAccessData()
                loop()
            }
        }
    }
}