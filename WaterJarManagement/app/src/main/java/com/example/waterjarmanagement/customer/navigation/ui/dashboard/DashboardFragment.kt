package com.example.waterjarmanagement.customer.navigation.ui.dashboard

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waterjarmanagement.customer.adapters.OnOrderViewClicked
import com.example.waterjarmanagement.customer.adapters.OnPayGoItemClick
import com.example.waterjarmanagement.customer.adapters.OrderAdapter
import com.example.waterjarmanagement.customer.adapters.PayGoAdapter
import com.example.waterjarmanagement.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(), OnOrderViewClicked, OnPayGoItemClick {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var progressDialog: ProgressDialog
    private lateinit var dashboardViewModel: DashboardViewModel
    private var type = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        activity?.actionBar?.hide()
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Fetching list of orders")
        progressDialog.show()

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = OrderAdapter(this)
        val payGoAdapter = PayGoAdapter(this)
        binding.recyclerView.adapter = adapter

        dashboardViewModel.orderList.observe(requireActivity(), Observer {
            ifOrderListEmpty()
            dashboardViewModel.orderList.value?.let { it1 -> adapter.updateList(it1) }
            progressDialog.dismiss()
        })

        dashboardViewModel.payGoOrderList.observe(requireActivity(), Observer {
            dashboardViewModel.payGoOrderList.value?.let { it -> payGoAdapter.updateList(it) }
            progressDialog.dismiss()
        })

        dashboardViewModel.getOrdersList()


        dashboardViewModel.getPayGoOrdersList()

        binding.orderType.setOnClickListener {
            type = if(type == 0){
                binding.orderType.text = "Get Monthly Orders"
                binding.recyclerView.adapter = payGoAdapter
                ifPayGoOrderListIsEmpty()
                1
            }
            else{
                binding.orderType.text = "Get Pay As You Go Orders"
                binding.recyclerView.adapter = adapter
                ifOrderListEmpty()
                0
            }
        }
        return binding.root
    }


    private fun ifOrderListEmpty(){
        if(dashboardViewModel.orderList.value?.size == 0){
            binding.emptyTextView.visibility = View.VISIBLE
        }
        else{
            binding.emptyTextView.visibility = View.GONE
        }
    }

    private fun ifPayGoOrderListIsEmpty(){
        if(dashboardViewModel.payGoOrderList.value?.size == 0){
            binding.emptyTextView.visibility = View.VISIBLE
        }
        else{
            binding.emptyTextView.visibility = View.GONE
        }
    }

    override fun onClickPayGoOrder(sellerId: String) {

    }
    override fun onClickOrder(sellerId: String) {

    }
}