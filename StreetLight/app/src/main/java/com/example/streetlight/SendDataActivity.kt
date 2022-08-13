package com.example.streetlight

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.streetlight.databinding.ActivitySendDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import java.util.concurrent.Executor
import java.util.concurrent.Executors
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
    private val TAG = "TFLite - ODT"
    private val MAX_FONT_SIZE = 96F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Prakash"

        latitude = intent.getStringExtra("latitude").toString()
        longitude = intent.getStringExtra("longitude").toString()

        imageView = findViewById(R.id.captureImage)

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
            if(data?.extras?.get("data") != null){
                image = data.extras?.get("data") as Bitmap
                Executors.newFixedThreadPool(4).execute {
                    runObjectDetection(image)
                }
                runOnUiThread {
                    binding.captureImage.setImageBitmap(image)
                }
            }
        }
    }

    /**
     * TFLite Object Detection Function
     */
    private fun runObjectDetection(bitmap: Bitmap) {
        // Step 1: create TFLite's TensorImage object
        val image = TensorImage.fromBitmap(bitmap)

        // Step 2: Initialize the detector object
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(5)
            .setScoreThreshold(0.5f)
            .build()
        val detector = ObjectDetector.createFromFileAndOptions(
            this, // the application context
            "model_new.tflite", // must be same as the filename in assets folder
            options
        )

        // Step 3: feed given image to the model and print the detection result
        val results = detector.detect(image)

        // Step 4: Parse the detection result and show it
        debugPrint(results)
//        val resultToDisplay = results.map {
//            // Get the top-1 category and craft the display text
//            val category = it.categories.first()
//            val text = "${category.label}, ${category.score.times(100).toInt()}%"
//
//            // Create a data object to display the detection result
//            DetectionResult(it.boundingBox, text)
//        }
//// Draw the detection result on the bitmap and show it.
//        val imgWithResult = drawDetectionResult(bitmap, resultToDisplay)
//        runOnUiThread {
//            imageView.setImageBitmap(imgWithResult)
//        }
    }

    private fun debugPrint(results : List<Detection>) {
        Log.e("Objects", results.size.toString())
        for ((i, obj) in results.withIndex()) {
            val box = obj.boundingBox

            Log.d(TAG, "Detected object: ${i} ")
            Log.d(TAG, "  boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})")

            for ((j, category) in obj.categories.withIndex()) {
                Log.d(TAG, "    Label $j: ${category.label}")
                val confidence: Int = category.score.times(100).toInt()
                Log.d(TAG, "    Confidence: ${confidence}%")
            }
        }
    }

    /**
     * drawDetectionResult(bitmap: Bitmap, detectionResults: List<DetectionResult>
     *      Draw a box around each objects and show the object's name.
     */
    private fun drawDetectionResult(
        bitmap: Bitmap,
        detectionResults: List<DetectionResult>
    ): Bitmap {
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()
        pen.textAlign = Paint.Align.LEFT

        detectionResults.forEach {
            // draw bounding box
            pen.color = Color.RED
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)


            val tagSize = Rect(0, 0, 0, 0)

            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.YELLOW
            pen.strokeWidth = 2F

            pen.textSize = MAX_FONT_SIZE
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            // adjust the font size so texts are inside the bounding box
            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
        }
        return outputBitmap
    }
    /**
     * setViewAndDetect(bitmap: Bitmap)
     *      Set image to view and call object detection
     */
    private fun setViewAndDetect(bitmap: Bitmap) {
        // Display capture image
        imageView.setImageBitmap(bitmap)

        // Run ODT and display result
        // Note that we run this in the background thread to avoid blocking the app UI because
        // TFLite object detection is a synchronised process.
        lifecycleScope.launch(Dispatchers.Default) { runObjectDetection(bitmap) }
    }
}

/**
 * DetectionResult
 *      A class to store the visualization info of a detected object.
 */
data class DetectionResult(val boundingBox: RectF, val text: String)