package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ContactItemClicked {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainModel: MainViewModel
    private var allContacts: List<Contact> = emptyList()
    private var canCall = false


    private val activityLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val intent: Intent = it.data as Intent
        val name = intent.getStringExtra("name").toString()
        val phoneNumber = intent.getStringExtra("phone")?.toLong()!!
        val gender = intent.getStringExtra("gender").toString()

        val contact = Contact(name, phoneNumber, gender)
        mainModel.insertContact(contact)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PermissionChecker.PERMISSION_GRANTED){
            canCall = true
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 0);
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ContactAdapter(allContacts, this)

        mainModel = ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)
        mainModel.allContacts.observe(this, Observer {
            allContacts = it
            (binding.recyclerView.adapter as ContactAdapter).updateList(allContacts)
        })

        binding.addContactButton.setOnClickListener {
            onAddButtonClicked()
        }


    }

    override fun onCallButtonClicked(contact: Contact) {
        val phoneNumber = contact.phoneNumber.toString()
        val intent = Intent(Intent.ACTION_CALL)
        intent.setData(Uri.parse("tel:"+phoneNumber))
        startActivity(intent)
    }

    override fun onDeleteButtonClicked(contact: Contact) {
        mainModel.deleteContact(contact)
    }

    private fun onAddButtonClicked() {
        val intent = Intent(this, AddContactActivity::class.java)
        activityLauncher.launch(intent)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults.contains(PackageManager.PERMISSION_GRANTED)){
            canCall = true
        }
        else{
            Toast.makeText(this, "App requires call permission", Toast.LENGTH_SHORT).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}