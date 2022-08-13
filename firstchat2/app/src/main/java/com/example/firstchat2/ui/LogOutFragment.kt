package com.example.firstchat2.ui


import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firstchat2.R
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass.
 * Use the [LogOutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogOutFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        logOut()
        return inflater.inflate(R.layout.fragment_log_out, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment LogOutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(): LogOutFragment{
            return LogOutFragment()
        }
    }

    private fun logOut(){
        auth = FirebaseAuth.getInstance()
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Logging Out")
        progressDialog.setMessage("Please wait while we log you out")
        progressDialog.show()
        auth.signOut()
        progressDialog.dismiss()
        requireActivity().finish()
    }


}