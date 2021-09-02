package com.example.qrcodemarket.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import com.bumptech.glide.Glide
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.model.InsertUser
import com.example.qrcodemarket.data.network.ApiProvince
import com.example.qrcodemarket.data.network.InsertApi
import com.example.qrcodemarket.ui.auth.LoginActivity
import com.example.qrcodemarket.util.snackbar
import com.example.qrcodemarket.util.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_country.*
import kotlinx.android.synthetic.main.activity_register.*


class CountryActivity() : AppCompatActivity() {

    lateinit var optionProvince:Spinner
    var provinces : MutableList<String> = mutableListOf<String>()
    var provincesId : MutableList<Int> = mutableListOf<Int>()

    lateinit var optionDistrict:Spinner
    var districts : MutableList<String> = mutableListOf<String>()
    var districtId : MutableList<Int> = mutableListOf<Int>()
    var provincesIdInDistrict : MutableList<Int> = mutableListOf<Int>()

    lateinit var optionWard:Spinner
    var wards : MutableList<String> = mutableListOf<String>()
    var wardId : MutableList<Int> = mutableListOf<Int>()
    var districtIdInward : MutableList<Int> = mutableListOf<Int>()

    lateinit var progressBar:ProgressBar

    var disposable : Disposable? = null
    var getProvince:String? = null
    var getDistrict:String? = null
    var getWard:String? = null
    var getAddress:String? = null
    var fullName:String? = null
    var dateOfBirth:String? = null
    var numberPhone:String? = null
    var loginName:String? = null
    var password:String? = null

    val province by lazy {
        ApiProvince.create()
    }
    val insertApi by lazy {
        InsertApi.create()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        val intent = intent
        fullName = intent.getStringExtra("fullName")
        dateOfBirth = intent.getStringExtra("dateOfBirth")
        numberPhone = intent.getStringExtra("numberPhone")
        loginName = intent.getStringExtra("loginName")
        password = intent.getStringExtra("password")



        optionProvince = findViewById(R.id.province_spinner) as Spinner
        optionDistrict = findViewById(R.id.district_spinner) as Spinner
        optionWard = findViewById(R.id.ward_spinner) as Spinner
        progressBar = findViewById(R.id.progress)

        var btnLogin:Button = findViewById(R.id.btnLogin)
        var edtAddress:EditText = findViewById(R.id.edtAddress)

        getProvince()
        setSpinnerItem()

        btnLogin.setOnClickListener {
            getAddress = edtAddress.text.toString()
            if(getAddress.isNullOrEmpty() || getProvince.isNullOrEmpty() || getDistrict.isNullOrEmpty()  || getWard.isNullOrEmpty()){
                country_layout.snackbar("Please add the address!")
            }else{
                submitData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        val imageAvoidContact: ImageView =findViewById(R.id.imgAvoidContact)

        Glide.with(this)
            .load(R.drawable.avoidcontact)
            .into(imageAvoidContact);

    }


    private fun submitData(){
        val insertUser: InsertUser.Data
        insertUser=InsertUser.Data(loginName!!,password!!,fullName!!,dateOfBirth!!,numberPhone!!,getProvince!!,getDistrict!!,getWard!!,getAddress!!)
        disposable = insertApi.userSignUp(insertUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.i("abc", "abc: " + result.data.toString())
                    Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
                },
                { error ->
                    toast("Register fail, please try again!!")
                    Log.i("abc", "abc: " + error.localizedMessage + error.message + error)
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
            )
    }

    private fun setSpinnerItem() {
        val adapterProvinsi = ArrayAdapter(this,
            R.layout.spinner_single_simple, resources.getStringArray(R.array.Province))
        adapterProvinsi.setDropDownViewResource(R.layout.spinner_dropdown_simple)
        optionProvince.adapter = adapterProvinsi

        val adapterDistrict= ArrayAdapter(this,
            R.layout.spinner_single_simple, resources.getStringArray(R.array.District))
        adapterProvinsi.setDropDownViewResource(R.layout.spinner_dropdown_simple)
        optionDistrict.adapter = adapterDistrict

        val adapterWard= ArrayAdapter(this,
            R.layout.spinner_single_simple, resources.getStringArray(R.array.Ward))
        adapterWard.setDropDownViewResource(R.layout.spinner_dropdown_simple)
        optionWard.adapter = adapterWard

    }

    private fun getProvince() {
        disposable = province.getDataProvince()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    provinces.clear()
                    provincesId.clear()

                    provinces.add("Province")
                    provincesId.add(0)


                    result.data.forEach {
                        provincesId.add(it.id)
                        provinces.add(it.name)
                    }

                    val adapterProvinsi = ArrayAdapter(this,
                        R.layout.spinner_single_simple, provinces)
                    adapterProvinsi.setDropDownViewResource(R.layout.spinner_dropdown_simple)
                    optionProvince.adapter = adapterProvinsi

                    provinceLoad(false)

                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    provinceLoad(false)
                }
            )
        optionProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (provincesId.count() > 0 && position > 0) {
                    getProvince = parent?.getItemAtPosition(position).toString()

                    getDistrict(provincesId[position])
                }
            }
        }
    }

    private fun provinceLoad(status: Boolean) {
        progressBar.visibility = if (status) View.VISIBLE else View.GONE
        optionProvince.isEnabled = !status
        optionDistrict.isEnabled = false
        optionWard.isEnabled = false
        btnLogin.isEnabled = false
    }

    private fun getDistrict(id:Int) {
        disposable = province.getDataDistrict()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
//                    Toast.makeText(this, "${result.data}", Toast.LENGTH_SHORT).show()

                    districts.clear()
                    districtId.clear()
                    provincesIdInDistrict.clear()

                    districts.add("District")
                    districtId.add(0)
                    provincesIdInDistrict.add(0)


                    result.data.forEach {
                        if(it.idProvince == id){
                            provincesIdInDistrict.add(it.idProvince)
                            districtId.add(it.idDistrict)
                            districts.add(it.name)
                        }
//                        provincesIdInDistrict.add(it.idProvince)
//                        districtId.add(it.idDistrict)
//                        districts.add(it.name)
                    }

                    val adapterDistrict = ArrayAdapter(this,
                        R.layout.spinner_single_simple, districts)
                    adapterDistrict.setDropDownViewResource(R.layout.spinner_dropdown_simple)
                    optionDistrict.adapter = adapterDistrict

                    districtLoad(false)

                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    districtLoad(false)
                }
            )
        optionDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (districtId.count() > 0 && position > 0) {
                    getDistrict = parent?.getItemAtPosition(position).toString()
                    getWard(districtId[position])
                }
            }
        }
    }


    private fun districtLoad(status: Boolean) {
        progressBar.visibility = if (status) View.VISIBLE else View.GONE
        optionProvince.isEnabled = !status
        optionDistrict.isEnabled = !status
        optionWard.isEnabled = false
        btnLogin.isEnabled = false
    }

    private fun getWard(id:Int) {
        disposable = province.getDataWard()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    wards.clear()
                    wardId.clear()
                    districtIdInward.clear()

                    wards.add("Ward")
                    wardId.add(0)
                    districtIdInward.add(0)


                    result.data.forEach {
                        if(it.idDistrict == id){
                            districtIdInward.add(it.idDistrict)
                            wardId.add(it.idWard)
                            wards.add(it.name)
                        }
                    }

                    val adapterWard = ArrayAdapter(this,
                        R.layout.spinner_single_simple, wards)
                    adapterWard.setDropDownViewResource(R.layout.spinner_dropdown_simple)
                    optionWard.adapter = adapterWard

                    wardLoad(false)

                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                        wardLoad(false)
                }
            )
        optionWard.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (wardId.count() > 0 && position > 0) {
                    getWard = parent?.getItemAtPosition(position).toString()
                }
            }
        }
    }

    private fun wardLoad(status: Boolean) {
        progressBar.visibility = if (status) View.VISIBLE else View.GONE
        optionProvince.isEnabled = !status
        optionDistrict.isEnabled = !status
        optionWard.isEnabled = !status
        btnLogin.isEnabled = !status
    }

}