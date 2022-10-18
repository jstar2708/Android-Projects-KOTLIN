package com.example.waterjarmanagement.seller.navigation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.waterjarmanagement.databinding.FragmentHome2Binding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

private var _binding: FragmentHome2Binding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

      _binding = FragmentHome2Binding.inflate(inflater, container, false)
      val root: View = binding.root

    homeViewModel =
        ViewModelProvider(this)[HomeViewModel::class.java]

      homeViewModel.seller.observe(requireActivity(), Observer {
          updateUI()
      })

      homeViewModel.toast.observe(requireActivity(), Observer {
          //Snackbar.make(binding.root, "Error while retrieving details", Snackbar.LENGTH_LONG).show()
      })

    homeViewModel.getSeller()

      if(homeViewModel.seller.value != null){
          updateUI()
      }
    return root
  }

    private fun updateUI() {
        binding.totalJars.text = homeViewModel.seller.value?.getTotalJars().toString()
        binding.totalOrders.text = homeViewModel.seller.value?.getCustomerCount().toString()
        binding.jarsLeft.text = (homeViewModel.seller.value?.getTotalJars()!!- homeViewModel.seller.value?.getJarUsed()?.toInt()!!).toString()
        binding.monthlyOrders.text = homeViewModel.getMonthlyOrders().toString()
        binding.payGoOrders.text = homeViewModel.getPayGoOrders().toString()
        binding.priceForOneJar.text = homeViewModel.seller.value!!.getJarPrice().toString()
        binding.progressBar.progress =  ((homeViewModel.seller.value?.getTotalJars()!!- homeViewModel.seller.value?.getJarUsed()?.toInt()!!) / homeViewModel.seller.value?.getTotalJars()!!.toFloat() * 100f).roundToInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}