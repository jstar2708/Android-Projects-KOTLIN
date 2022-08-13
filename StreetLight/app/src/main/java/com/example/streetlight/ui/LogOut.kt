package com.example.streetlight.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.streetlight.R
import com.example.streetlight.authentication.OtpSignUp
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogOut.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogOut : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        val dialog = android.app.AlertDialog.Builder(requireContext())
        dialog.setTitle("Alert")
        dialog.setMessage("Do you want to log out")
        dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { Dialog, which ->
        })
        dialog.setPositiveButton("OK", DialogInterface.OnClickListener { Dialog, which ->
            auth.signOut()
            val intent = Intent(requireContext(), OtpSignUp::class.java)
            startActivity(intent)
            requireActivity().finish()
        })
        val alertDialog = dialog.create()
        alertDialog.show()
        return inflater.inflate(R.layout.fragment_log_out, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LogOut.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LogOut().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}