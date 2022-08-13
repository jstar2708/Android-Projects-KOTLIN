package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.myapplication.databinding.ActivityAddContactBinding
import java.lang.NumberFormatException

class AddContactActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityAddContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter
        }

        binding.spinner.onItemSelectedListener = this

        binding.addContactButton.setOnClickListener {
            if(binding.nameEditText.text.isNullOrEmpty() || binding.phoneEditText.text.isNullOrEmpty() || binding.phoneEditText.text.length != 10){
                Toast.makeText(this, "Enter details", Toast.LENGTH_SHORT).show()
            }
            else{
                try {
                    (binding.phoneEditText.text.toString()).toLong()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("name", binding.nameEditText.text.toString())
                    intent.putExtra("phone", binding.phoneEditText.text.toString())
                    intent.putExtra("gender", binding.spinner.selectedItem as String)
                    setResult(78, intent)
                    finish()
                }
                catch (e: NumberFormatException){
                    Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val str: String = p0?.getItemAtPosition(p2) as String
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}