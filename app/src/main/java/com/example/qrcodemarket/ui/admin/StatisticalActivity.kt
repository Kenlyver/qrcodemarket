package com.example.qrcodemarket.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.model.AccessAllUserAdapter
import com.example.qrcodemarket.data.model.getAccessAllUser
import com.example.qrcodemarket.data.network.InsertApi
import com.example.qrcodemarket.ui.auth.AppPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_statistical.*
import kotlinx.android.synthetic.main.activity_statistical.imgBackArrow
import java.util.*
import kotlin.collections.ArrayList

class StatisticalActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    var tempData:ArrayList<getAccessAllUser.Data>? = null
    private lateinit var newData:List<getAccessAllUser.Data>
    var disposable : Disposable? = null
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
                    newData = result.data
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

    private fun getAccessData(fullName:String){
        disposable = insertApi.allaccessbyfullname(fullName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    showAll(result.data)
                    Log.i("abc", "abc: " + result.data.toString())
                },
                { error ->
                    Log.i("abc", "abc: " + error.localizedMessage + error.message + error)
                }
            )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_statistical, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == R.id.All) {
            getAllAccess()
            return true
        }
        else if (item.getItemId() == R.id.Search) {
            searchView = item.actionView as SearchView
            searchView.setQueryHint("Search...")
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { getAccessData(it) }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val searchText = newText!!.toLowerCase(Locale.getDefault())
                    if (searchText.isNotEmpty()) {

                    } else {

                    }
                    return false
                }
            })
            return true
        }

        return super.onOptionsItemSelected(item);
    }




}