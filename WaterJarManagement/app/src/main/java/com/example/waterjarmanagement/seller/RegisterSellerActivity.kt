package com.example.waterjarmanagement.seller

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
import com.example.waterjarmanagement.customer.navigation.CustomerNavigationActivity
import com.example.waterjarmanagement.databinding.ActivityRegisterSellerBinding
import com.example.waterjarmanagement.seller.model.Seller
import com.example.waterjarmanagement.seller.navigation.SellerNavigationActivity
import com.example.waterjarmanagement.seller.view_models.RegisterSellerViewModel

class RegisterSellerActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityRegisterSellerBinding
    private lateinit var registerSellerModel: RegisterSellerViewModel
    private lateinit var isPhoneNumberVerified: String
    private val seller = Seller()
    private var cityArray: Int = 0
    private lateinit var progressDialog: ProgressDialog
    private val contract: ActivityResultLauncher<Intent> = (this).registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        isPhoneNumberVerified = it.data?.getStringExtra("result").toString()
        if(isPhoneNumberVerified == "T"){
            registerSellerModel.registerSeller(seller, true)
        }
        else{
            registerSellerModel.registerSeller(seller, false)
            progressDialog.dismiss()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterSellerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Registering you")

        registerSellerModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[RegisterSellerViewModel::class.java]
        registerSellerModel.toast.observe(this, Observer{
            if(it != null){
                handleToast(it)
            }
        })

        registerSellerModel.action.observe(this, Observer {
            if(it != null){
                handleAction()
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

    private fun onRegisterButtonClicked() {
        if (binding.addressEditText.editText?.text.isNullOrEmpty() || binding.emailEditText.editText?.text.isNullOrEmpty()
            || binding.ownerNameEditText.editText?.text.isNullOrEmpty() || binding.passwordEditText.editText?.text.isNullOrEmpty()
            || binding.phoneEditText.editText?.text.isNullOrEmpty() || binding.pinCodeEditText.editText?.text.isNullOrEmpty()
            || binding.phoneEditText.editText?.text?.length != 10 || binding.spinner.selectedItem.toString() == "Select state"
            || binding.pinCodeEditText.editText?.text?.length != 6 || binding.companyNameEditText.editText?.text.isNullOrEmpty()
            || binding.jarCountEditText.editText?.text.toString().toInt() == 0 || binding.jarPriceEditText.editText?.text.toString().toInt() == 0
            || binding.cinEditText.editText?.text?.length != 21 || binding.spinnerCity.selectedItem == "Select your city"
        ) {

            Toast.makeText(this, "Fill complete details", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        } else {
            seller.setAddress(binding.addressEditText.editText?.text.toString())
            seller.setEmail(binding.emailEditText.editText?.text.toString())
            seller.setOwnerName(binding.ownerNameEditText.editText?.text.toString())
            seller.setPassword(binding.passwordEditText.editText?.text.toString())
            seller.setCustomerCareNumber(binding.phoneEditText.editText?.text.toString().toLong())
            seller.setPinCode(binding.pinCodeEditText.editText?.text.toString().toInt())
            seller.setState(binding.spinner.selectedItem.toString())
            seller.setCin(binding.cinEditText.editText?.text.toString())
            seller.setCompanyName(binding.companyNameEditText.editText?.text.toString())
            seller.setTotalJars(binding.jarCountEditText.editText?.text.toString().toInt())
            seller.setJarPrice(binding.jarPriceEditText.editText?.text.toString().toInt())
            seller.setCity(binding.spinnerCity.selectedItem.toString())
            val intent = Intent(this, OtpActivity::class.java)
            intent.putExtra("phone", seller.getCustomerCareNumber().toString())
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
        val intent = Intent(this, SellerNavigationActivity::class.java)
        startActivity(intent)
        finish()
    }
}