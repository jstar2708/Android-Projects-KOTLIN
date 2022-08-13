package com.example.camera


import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.camera.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageUri: Uri
    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()){
        binding.image.setImageURI(null)
        binding.image.setImageURI(imageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.captureView.setOnClickListener {
            imageUri = createImageUri()!!
            contract.launch(imageUri)
        }

//        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
//            startCamera()
//        }
//        else{
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 0)
//        }
//
//        binding.captureView.setOnClickListener {
//            takePhoto()
//        }
    }

    private fun createImageUri(): Uri?{
        val image = File(applicationContext.filesDir, "camera_photos.png")
        return FileProvider.getUriForFile(applicationContext,
        "com.example.camera.fileProvider",
        image)
    }


//    private fun startCamera(){
//        //Start Camera
//    }
//
//    private fun takePhoto(){
//        //Take Photo
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
//            startCamera()
//        }
//        else{
//            Toast.makeText(this, "Please grant the permissions", Toast.LENGTH_SHORT).show()
//        }
//    }
}