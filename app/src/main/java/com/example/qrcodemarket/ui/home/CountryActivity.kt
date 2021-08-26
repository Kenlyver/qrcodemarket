package com.example.qrcodemarket.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.network.ApiProvince
import com.example.qrcodemarket.data.network.response.ProvinceItem
import kotlinx.android.synthetic.main.activity_country.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CountryActivity : AppCompatActivity() {

    lateinit var optionProvince:Spinner
    lateinit var optionDistrict:Spinner
    lateinit var optionWard:Spinner

    var optionProvinces = arrayOf("Option1","Option2","Option3")




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        optionProvince = findViewById(R.id.province_spinner) as Spinner
        optionDistrict = findViewById(R.id.district_spinner) as Spinner
        optionWard = findViewById(R.id.ward_spinner) as Spinner

        getProvince()

        val imageAvoidContact: ImageView =findViewById(R.id.imgAvoidContact)

        optionProvince.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        Glide.with(this)
            .load(R.drawable.avoidcontact)
            .into(imageAvoidContact);

    }

    private fun getProvince() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://sheltered-anchorage-60344.herokuapp.com/")
            .build()

        val apiProvince = retrofit.create(ApiProvince::class.java)

        val callDataProvince:Call<List<ProvinceItem>> = apiProvince.getData()

        callDataProvince.enqueue(object : Callback<List<ProvinceItem>?> {
            override fun onResponse(call: Call<List<ProvinceItem>?>, response: Response<List<ProvinceItem>?>) {
                val Provinces:List<ProvinceItem> = response.body()!!
                val stringBuilder = StringBuilder()
                for(province in Provinces){
                    stringBuilder.append(province.idProvince)
                    stringBuilder.append("\n")
                    stringBuilder.append(province.name)

                }
//                txtTest.text = stringBuilder
            }

            override fun onFailure(call: Call<List<ProvinceItem>?>, t: Throwable) {
                Log.e("ERROR",t.message.toString())
            }
        })
    }
}