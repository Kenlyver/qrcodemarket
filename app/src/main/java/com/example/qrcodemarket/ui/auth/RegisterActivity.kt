package com.example.qrcodemarket.ui.auth

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.qrcodemarket.R
import com.example.qrcodemarket.data.network.response.User
import com.example.qrcodemarket.databinding.ActivityRegisterBinding
import com.example.qrcodemarket.ui.home.CountryActivity
import com.example.qrcodemarket.util.snackbar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_register.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity(),AuthListener,KodeinAware {

    override val kodein by kodein()
    private val factory:AuthViewModelFactory by instance()

    var fullName:String? = null
    var dateOfBirth:String? = null
    var numberPhone:String?= null
    var loginName:String? = null
    var password:String? = null

    private var mDatePickerDialog: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        val viewModel = ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)

        binding.viewModel =viewModel
        viewModel.authListener = this
        setDateTimeField();

        edtDateOfBirth.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                mDatePickerDialog!!.show()
                return false
            }
        })


        binding.btnContinue.setOnClickListener {
            fullName = binding.edtFullName.text.toString()
            dateOfBirth = binding.edtDateOfBirth.text.toString()
            numberPhone = binding.edtNumberPhone.text.toString()
            loginName = binding.edtLoginName.text.toString()
            password = binding.edtPassword.text.toString()
            if(fullName.isNullOrEmpty()){
                edtFullName.requestFocus()
                edtFullName.setError("Full name is request")
            }
            else if(dateOfBirth.isNullOrEmpty()){
                edtDateOfBirth.setError("Date of Birth is request")
            }
            else if(numberPhone.isNullOrEmpty()){
                edtNumberPhone.requestFocus()
                edtNumberPhone.setError("Number phone is request")
            }
            else if(loginName.isNullOrEmpty() || loginName!!.length<8){
                edtLoginName.requestFocus()
                edtLoginName.setError("Login name cannot be blank or less than 8 character")
            }
            else if(password.isNullOrEmpty() || password!!.length<6 ){
                edtPassword.requestFocus()
                edtPassword.setError("Password cannot be blank or less than 6 characters")
            }
            else {
                val intent = Intent(this, CountryActivity::class.java)
                intent.putExtra("fullName", fullName)
                intent.putExtra("dateOfBirth", dateOfBirth)
                intent.putExtra("numberPhone", numberPhone)
                intent.putExtra("loginName", loginName)
                intent.putExtra("password", password)
                startActivity(intent)
            }
        }

    }

    override fun onStarted() {
    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    private fun setDateTimeField() {
        val newCalendar: Calendar = Calendar.getInstance()
        mDatePickerDialog = DatePickerDialog(this, R.style.DialogTheme,
            { view, year, monthOfYear, dayOfMonth ->
                val newDate: Calendar = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)
                val sd = SimpleDateFormat("dd-MM-yyyy")
                val startDate: Date = newDate.getTime()
                val fdate: String = sd.format(startDate)
                edtDateOfBirth.setText(fdate)
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)
        )
        mDatePickerDialog!!.getDatePicker().setMaxDate(System.currentTimeMillis())
    }

    override fun onSuccess(user: User) {
        Intent(this, CountryActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("fullName",fullName)
            intent.putExtra("dateOfBirth",dateOfBirth)
            intent.putExtra("numberPhone",numberPhone)
            intent.putExtra("loginName",loginName)
            intent.putExtra("password",password)
            startActivity(it)
        }
    }

    override fun onFailure(message:String) {
        register_layout.snackbar(message)
    }
}