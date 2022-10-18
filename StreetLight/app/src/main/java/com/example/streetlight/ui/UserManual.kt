package com.example.streetlight.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.streetlight.R

class UserManual : Fragment() {
    private val LOCATION_PERMISSION_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        getLocationAccess()

        return inflater.inflate(R.layout.fragment_user_manual, container, false)
    }

    companion object {

        fun newInstance() = UserManual()

    }

    private fun getLocationAccess(){
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

        }
        else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
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

            }
            else{
                Toast.makeText(requireContext(), "User has not granted permission access", Toast.LENGTH_LONG).show()
            }
        }
    }
}