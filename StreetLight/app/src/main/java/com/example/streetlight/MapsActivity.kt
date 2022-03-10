package com.example.streetlight

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.streetlight.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var mMap: GoogleMap
    private lateinit var client: FusedLocationProviderClient
    private lateinit var binding: ActivityMapsBinding
    private lateinit var currentLocation: Location
    private lateinit var mapFragment: SupportMapFragment
    private val LOCATION_PERMISSION_REQUEST = 1
    private var isGpsEnabled: Boolean = false
    private var isNetworkEnabled = false;
    private lateinit var locationManager: LocationManager

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Prakash"

        client = LocationServices.getFusedLocationProviderClient(this)


        mapFragment =  supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager

        isLocationAvailable()

        binding.location.setOnClickListener {
            try {
                isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            }catch (e: Exception){}

            if(isGpsEnabled){
                Toast.makeText(this, currentLocation.latitude.toString() + " " + currentLocation.longitude.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SendDataActivity::class.java)
                intent.putExtra("latitude", currentLocation.latitude.toString())
                intent.putExtra("longitude", currentLocation.longitude.toString())
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "User has not granted location access", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun isLocationAvailable(){
        try {
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }catch (e: Exception){}

        try {
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }catch (e: Exception){}

        if(!isGpsEnabled){
            val dialog = android.app.AlertDialog.Builder(this)
            dialog.setTitle("GPS is not enabled")
            dialog.setMessage("Please turn on the location services")
            dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { Dialog, which ->
                Toast.makeText(this, "Location access denied", Toast.LENGTH_LONG).show()
                finish()
            })
            dialog.setPositiveButton("OK", DialogInterface.OnClickListener { Dialog, which ->
                //This will navigate user to location page in settings
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            })
            val alertDialog = dialog.create()
            alertDialog.show()
        }
    }

    private fun getLocationAccess(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == LOCATION_PERMISSION_REQUEST){
            if(grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
            }
            else{
                Toast.makeText(this, "User has not granted permission access", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun getLocationUpdates(){
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0f, this)
    }


    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        getLocationAccess()

        getLocationUpdates()

        val task = client.lastLocation

        task.addOnSuccessListener {
            if(it == null){
                Log.e("Heloo", "")
            }
            else{
                currentLocation = it
                Log.e("", currentLocation.latitude.toString() + " " + currentLocation.longitude.toString())
            }
        }

    }

    override fun onLocationChanged(location: Location) {
        currentLocation = location
    }


}