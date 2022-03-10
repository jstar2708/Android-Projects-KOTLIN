package com.example.streetlight

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.streetlight.databinding.ActivitySendDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.jar.Manifest

class SendDataActivity : AppCompatActivity() {

    private lateinit var longitude: String
    private lateinit var latitude: String
    private val CAMERA_PERMISSION_REQUEST = 201
    private val CAMERA_START_REQUEST = 301
    private lateinit var image: Bitmap
    private lateinit var imageView: ImageView
    private lateinit var binding: ActivitySendDataBinding
    private lateinit var database: FirebaseDatabase
    private var currentUser: Complaint? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Prakash"

        latitude = intent.getStringExtra("latitude").toString()
        longitude = intent.getStringExtra("longitude").toString()

        binding.captureImage.setOnClickListener {
            getCameraPermission()
        }

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://street-light-cbd73-default-rtdb.asia-southeast1.firebasedatabase.app/")

        database.reference.child("Users").child(auth.currentUser?.uid.toString()).addValueEventListener(object: ValueEventListener{
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser = snapshot.getValue(Complaint::class.java)
                binding.name.text = "Name: " + currentUser?.getName()
                binding.phoneNumber.text = "Phone Number: ${currentUser?.getPhoneNumber()?.substring(4)}"
                currentUser?.setLatitude(latitude)
                Log.e(currentUser?.getName(), "")
                currentUser?.setLongitude(longitude)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Some error", error.details)
            }

        })

        binding.complaint.setOnClickListener {
            currentUser?.setAddress(binding.address.text.toString())
            currentUser?.setHSNCode(binding.hsnCode.text.toString())
            database.reference.child("Users").child(auth.currentUser?.uid.toString()).setValue(currentUser)
            Toast.makeText(this, "Complaint generated", Toast.LENGTH_LONG).show()
        }

    }

    private fun getCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            startCamera()
        }
        else{
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == CAMERA_PERMISSION_REQUEST){
            if(grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                startCamera()
            }
            else{
                Toast.makeText(this, "Enable camera permission to capture a photo of street light", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun startCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_START_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CAMERA_START_REQUEST){
            image = data?.extras?.get("data") as Bitmap
            binding.captureImage.setImageBitmap(image)
        }
    }
}