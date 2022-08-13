package com.example.waterjarmanagement.customer

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.waterjarmanagement.LoginActivity
import com.example.waterjarmanagement.OtpActivity
import com.example.waterjarmanagement.R
import com.example.waterjarmanagement.customer.models.Customer
import com.example.waterjarmanagement.customer.navigation.CustomerNavigationActivity
import com.example.waterjarmanagement.databinding.ActivityRegisterCustomerBinding
import com.example.waterjarmanagement.customer.view_models.RegisterCustomerViewModel

class RegisterCustomerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    private lateinit var binding: ActivityRegisterCustomerBinding
    private lateinit var registerCustomerModel: RegisterCustomerViewModel
    private lateinit var isPhoneNumberVerified: String
    private val customer = Customer()
    private lateinit var progressDialog: ProgressDialog
    private var cityArray: Int = R.array.none
    private val contract: ActivityResultLauncher<Intent> = (this).registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

        isPhoneNumberVerified = it.data?.getStringExtra("result").toString()

        if(isPhoneNumberVerified == "T"){
            registerCustomerModel.registerCustomer(customer, true)
        }
        else{
            registerCustomerModel.registerCustomer(customer, false)
            progressDialog.dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Registering you...")

        registerCustomerModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[RegisterCustomerViewModel::class.java]
        registerCustomerModel.action.observe(this, Observer {
            if(it != null){
                handleAction()
            }
        })

        registerCustomerModel.toast.observe(this, Observer{
            if(it != null){
                handleToast(it)
            }
        })

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.india_states,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter
        }

        binding.spinner.onItemSelectedListener = this

        binding.registerButton.setOnClickListener {
            progressDialog.show()
            onRegisterButtonClicked()
        }

        binding.alreadyHaveAcc.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        cityArray = when(binding.spinner.selectedItemId){
            1L -> R.array.andaman_nicobar_cities_array
            2L -> R.array.andhra_pradesh_cities_array
            3L -> R.array.arunachal_pradesh_cities_array
            4L -> R.array.assam_cities_array
            5L -> R.array.bihar_cities_array
            6L -> R.array.chandigarh_cities_array
            7L -> R.array.chhattisgarh_cities_array
            8L -> R.array.dadar_and_nagar_haveli_cities_array
            9L -> R.array.daman_and_diu_cities_array
            10L -> R.array.delhi_cities_array
            11L -> R.array.goa_cities_array
            12L -> R.array.gujarat_cities_array
            13L -> R.array.haryana_cities_array
            14L -> R.array.himachal_pradesh_cities_array
            15L -> R.array.jammu_kashmir_cities_array
            16L -> R.array.jharkhand_cities_array
            17L -> R.array.karnataka_cities_array
            18L -> R.array.kerala_cities_array
            19L -> R.array.lakshadweep_cities_array
            20L -> R.array.madhay_pradesh_array
            21L -> R.array.maharashtra_cities_array
            22L -> R.array.manipur_cities_array
            23L -> R.array.meghalaya_cities_array
            24L -> R.array.mizoram_cities_array
            25L -> R.array.nagaland_cities_array
            26L -> R.array.orissa_cities_array
            27L -> R.array.pondicherry_cities_array
            28L -> R.array.punjab_cities_array
            29L -> R.array.rajasthan_cities_array
            30L -> R.array.sikkim_cities_array
            31L -> R.array.tamil_nadu_cities_arrays
            32L -> R.array.telangana_cities_array
            33L -> R.array.uttaranchal_cities_array
            34L -> R.array.uttar_pradesh_cities_array
            35L -> R.array.west_bengal_cities_array
            else-> R.array.none
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            cityArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerCity.adapter = adapter
        }

        binding.spinnerCity.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun onRegisterButtonClicked(){
        if(binding.addressEditText.editText?.text.isNullOrEmpty() || binding.emailEditText.editText?.text.isNullOrEmpty()
            || binding.nameEditText.editText?.text.isNullOrEmpty() || binding.passwordEditText.editText?.text.isNullOrEmpty()
            || binding.phoneEditText.editText?.text.isNullOrEmpty() || binding.pinCodeEditText.editText?.text.isNullOrEmpty()
            || binding.phoneEditText.editText?.text?.length != 10 || binding.spinner.selectedItem.toString() == "Select state"
            || binding.pinCodeEditText.editText?.text?.length != 6 || binding.passwordEditText.editText?.text?.length != 8
            || binding.spinnerCity.selectedItem == "Select your city"){

            Toast.makeText(this, "Fill complete details", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
        else{
            customer.setAddress(binding.addressEditText.editText?.text.toString())
            customer.setEmail(binding.emailEditText.editText?.text.toString())
            customer.setName(binding.nameEditText.editText?.text.toString())
            customer.setPassword(binding.passwordEditText.editText?.text.toString())
            customer.setPhoneNumber(binding.phoneEditText.editText?.text.toString().toLong())
            customer.setPinCode(binding.pinCodeEditText.editText?.text.toString().toInt())
            customer.setState(binding.spinner.selectedItem.toString())
            customer.setCity(binding.spinnerCity.selectedItem.toString())
            val intent = Intent(this, OtpActivity::class.java)
            intent.putExtra("phone", customer.getPhoneNumber().toString())
            contract.launch(intent)
        }
    }

    private fun handleToast(value: Int){
        val str = when(value){
            1-> "Phone verification failed"
            2-> "Registered successfully"
            else-> "Error while registering"
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    private fun handleAction(){
        progressDialog.dismiss()
        val intent = Intent(this, CustomerNavigationActivity::class.java)
        startActivity(intent)
        finish()
    }
}