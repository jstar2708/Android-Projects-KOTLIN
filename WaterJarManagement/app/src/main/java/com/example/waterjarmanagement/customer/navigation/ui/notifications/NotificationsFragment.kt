package com.example.waterjarmanagement.customer.navigation.ui.notifications

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.state.ToggleableState
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.waterjarmanagement.LoginActivity
import com.example.waterjarmanagement.databinding.FragmentNotificationsBinding


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var notificationsViewModel: NotificationsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.actionBar?.hide()
        notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        notificationsViewModel.customer.observe(requireActivity(), Observer {
            updateUI()
        })

        notificationsViewModel.toast.observe(requireActivity(), Observer {
            handleToast(it)
        })

        notificationsViewModel.getCustomer()

        binding.updateProfile.setOnClickListener {
            setUI()
        }

        val dialog = android.app.AlertDialog.Builder(requireContext())
        dialog.setTitle("Log Out")
        dialog.setMessage("Are you sure you want to log out?")
        dialog.setNegativeButton("No", DialogInterface.OnClickListener { Dialog, which ->

        })
        dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { Dialog, which ->
            notificationsViewModel.signOut()
            Toast.makeText(requireContext(), "User logged out", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        })

        binding.logOut.setOnClickListener {

            val alertDialog = dialog.create()
            alertDialog.show()

        }


        return binding.root
    }

    private fun handleToast(value: Int) {
        when(value){
            1-> Toast.makeText(requireContext(), "Profile Updated", Toast.LENGTH_SHORT).show()
            2-> Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            else-> Unit
        }
    }

    private fun updateUI() {
        binding.nameTextInput.editText?.setText(notificationsViewModel.customer.value?.getName())
        binding.addressTextInput.editText?.setText(notificationsViewModel.customer.value?.getAddress())
        binding.cityTextInput.editText?.setText(notificationsViewModel.customer.value?.getCity())
        binding.emailTextInput.editText?.setText(notificationsViewModel.customer.value?.getEmail())
        binding.passwordTextInput.editText?.setText(notificationsViewModel.customer.value?.getPassword())
        binding.phoneTextInput.editText?.setText(notificationsViewModel.customer.value?.getPhoneNumber().toString())
        binding.pinCodeTextInput.editText?.setText(notificationsViewModel.customer.value?.getPinCode().toString())
        binding.stateTextInput.editText?.setText(notificationsViewModel.customer.value?.getState())
    }

    private fun setUI() {
        val customer = notificationsViewModel.customer.value
        customer?.setCity(binding.cityTextInput.editText?.text.toString())
        customer?.setState(binding.stateTextInput.editText?.text.toString())
        customer?.setEmail(binding.emailTextInput.editText?.text.toString())
        customer?.setName(binding.nameTextInput.editText?.text.toString())
        customer?.setPassword(binding.passwordTextInput.editText?.text.toString())
        customer?.setPinCode(binding.pinCodeTextInput.editText?.text.toString().toInt())
        customer?.setAddress(binding.addressTextInput.editText?.text.toString())
        notificationsViewModel.customer.value = customer

        notificationsViewModel.setCustomer()
    }
}