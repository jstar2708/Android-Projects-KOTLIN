package com.example.waterjarmanagement.customer.navigation.ui.home

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waterjarmanagement.R
import com.example.waterjarmanagement.customer.OrderActivity
import com.example.waterjarmanagement.customer.adapters.OnSellerItemClick
import com.example.waterjarmanagement.customer.adapters.SellersListAdapter
import com.example.waterjarmanagement.databinding.FragmentHomeBinding
import com.example.waterjarmanagement.seller.model.Seller
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(), OnSellerItemClick {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var progressDialog: ProgressDialog
    private var drawable: Int = 0
    private lateinit var homeViewModel: HomeViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.actionBar?.hide()
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Fetching list of sellers")
        progressDialog.show()

        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val adapter = SellersListAdapter(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        homeViewModel.list.observe(requireActivity(), Observer {
            ifListEmpty()
            homeViewModel.list.value?.let { it1 -> adapter.updateItems(it1) }
            progressDialog.dismiss()
        })

        homeViewModel.toast.observe(requireActivity(), Observer {
            progressDialog.dismiss()
            handleToast()
        })

        binding.sortButton.setOnClickListener {
            // Initializing the popup menu and giving the reference as current context
            val popupMenu = PopupMenu(requireContext(), binding.sortButton)
            // Inflating popup menu
            popupMenu.menuInflater.inflate(R.menu.sort_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.ratingAscending-> homeViewModel.sortByRating(0)
                    R.id.ratingDescending-> homeViewModel.sortByRating(1)
                    R.id.maxCustomers-> homeViewModel.sortByCustomers()
                }
                true
            }
            popupMenu.show()
        }

        binding.priceButton.setOnClickListener {
            progressDialog.show()
            homeViewModel.sortBy(drawable)
            drawable = if(drawable == 0){
                binding.arrow.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
                1
            } else{
                binding.arrow.setImageResource(R.drawable.ic_baseline_arrow_downward_24)
                0
            }
        }

        homeViewModel.getSellers()

        return binding.root
    }

    private fun handleToast() {
        Snackbar.make(binding.root, "Error while retrieving sellers list", Snackbar.LENGTH_LONG).show()
    }


    override fun onClick(seller: Seller) {
        val intent = Intent(requireActivity(), OrderActivity::class.java)
        intent.putExtra("sellerId", seller.getUserId())
        intent.putExtra("jarPrice", seller.getJarPrice())
        startActivity(intent)
    }

    private fun ifListEmpty(){
        if(homeViewModel.list.value?.size == 0){
            binding.emptyTextView.visibility = View.VISIBLE
        }
        else{
            binding.emptyTextView.visibility = View.GONE
        }
    }
}