package com.example.streetlight.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.streetlight.R
import com.example.streetlight.SendDataActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapsFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private lateinit var client: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    private lateinit var mapFragment: SupportMapFragment
    private val LOCATION_PERMISSION_REQUEST = 1
    private var isGpsEnabled: Boolean = false
    private var isNetworkEnabled = false
    private lateinit var locationManager: LocationManager
    private lateinit var marker: MarkerOptions

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        mMap = googleMap
        getLocationAccess()
        isLocationAvailable()

        mMap.setOnMyLocationButtonClickListener {
            val task = client.lastLocation
            task.addOnSuccessListener {
                if(it == null){
                    Log.e("Heloo", "")
                }
                else{
                    currentLocation = it
                    Log.e("", currentLocation.latitude.toString() + " " + currentLocation.longitude.toString())
                    val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                    mMap.clear()
                    marker = MarkerOptions().title("Your loaction").position(latLng)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19f))
                }
            }
            true
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_maps, container, false)
        view.findViewById<Button>(R.id.location).setOnClickListener {
            try {
                isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            }catch (e: Exception){}

            if(isGpsEnabled){
                Toast.makeText(requireContext(), currentLocation.latitude.toString() + " " + currentLocation.longitude.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), SendDataActivity::class.java)
                intent.putExtra("latitude", currentLocation.latitude.toString())
                intent.putExtra("longitude", currentLocation.longitude.toString())
                startActivity(intent)
            }
            else{
                Toast.makeText(requireContext(), "User has not granted location access", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        client = LocationServices.getFusedLocationProviderClient(requireContext())


        mapFragment = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
        mapFragment.getMapAsync(callback)
        locationManager = requireContext().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager

    }

    private fun isLocationAvailable(){
        try {
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }catch (e: Exception){}

        try {
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }catch (e: Exception){}

        if(!isGpsEnabled){
            val dialog = android.app.AlertDialog.Builder(requireContext())
            dialog.setTitle("GPS is not enabled")
            dialog.setMessage("Please turn on the location services")
            dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { Dialog, which ->
                Toast.makeText(requireContext(), "Location access denied", Toast.LENGTH_LONG).show()
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
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
        }
        else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
        }
    }

    @Deprecated("Deprecated in Java")
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
                Toast.makeText(requireContext(), "User has not granted permission access", Toast.LENGTH_LONG).show()
            }
        }
    }


}