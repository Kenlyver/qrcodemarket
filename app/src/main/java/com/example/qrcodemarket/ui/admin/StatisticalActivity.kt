package com.example.qrcodemarket.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.model.AccessAllUserAdapter
import com.example.qrcodemarket.data.model.MarketAdapter
import com.example.qrcodemarket.data.model.getAccessAllUser
import com.example.qrcodemarket.data.model.getMarket
import com.example.qrcodemarket.data.network.InsertApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_market_manager.*
import kotlinx.android.synthetic.main.activity_statistical.*
import kotlinx.android.synthetic.main.activity_statistical.imgBackArrow

class StatisticalActivity : AppCompatActivity() {


    var disposable : Disposable? = null
    private lateinit var tempArrayList: List<getAccessAllUser.Data>
    val insertApi by lazy {
        InsertApi.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistical)
        imgBackArrow.setOnClickListener {
            Intent(this, DashboardActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
        setSupportActionBar(toolbar)
        getAllAccess()


    }

    private fun showAll(dataAllAccess:List<getAccessAllUser.Data>){

        recycleAllAccess.layoutManager = LinearLayoutManager(this)
        recycleAllAccess.adapter = AccessAllUserAdapter(dataAllAccess)
    }

    private fun getAllAccess(){
        disposable = insertApi.accessAllUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    showAll(result.data)
                    tempArrayList = result.data
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_statistical, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == R.id.All) {

            return true;
        } else if (item.getItemId() == R.id.Ward) {

            return true;
        }else if (item.getItemId() == R.id.Market) {

            return true;
        }else if (item.getItemId() == R.id.Name) {
            val searchView:SearchView = item.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    return false
                }
            })
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}