package com.example.waterjarmanagement.customer.navigation.ui.dashboard

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waterjarmanagement.customer.PaymentActivity
import com.example.waterjarmanagement.customer.adapters.OnOrderViewClicked
import com.example.waterjarmanagement.customer.adapters.OnPayGoItemClick
import com.example.waterjarmanagement.customer.adapters.OrderAdapter
import com.example.waterjarmanagement.customer.adapters.PayGoAdapter
import com.example.waterjarmanagement.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(), OnOrderViewClicked, OnPayGoItemClick {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var progressDialog: ProgressDialog
    private lateinit var dashboardViewModel: DashboardViewModel
    private var index = 0
    private var type = 0
    private val activityLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        when (it.resultCode) {
            1 -> {
                val order = dashboardViewModel.orderList.value?.removeAt(index)
                dashboardViewModel.updateMonthlyOrder(order?.getOrderId().toString(), order?.getSellerId().toString())
            }
            2 -> {
                val order = dashboardViewModel.payGoOrderList.value?.removeAt(index)
                dashboardViewModel.updatePayGoOrder(order?.getOrderId().toString(), order?.getSellerId().toString())
            }
            else -> {
                Toast.makeText(context, "Payment Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
            ifPayGoOrderListIsEmpty()
            dashboardViewModel.payGoOrderList.value?.let { it2 -> payGoAdapter.updateList(it2) }
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

    override fun onClickPayGoOrder(sellerId: String, quantity: Int, jarPrice: Int, value: Int) {
        val intent = Intent(requireActivity(), PaymentActivity::class.java)
        intent.putExtra("quantity", quantity)
        intent.putExtra("priceForJar", jarPrice)
        intent.putExtra("sellerId", sellerId)
        intent.putExtra("type", type)
        index = value
        activityLauncher.launch(intent)
    }
    override fun onClickOrder(sellerId: String, jarPrice: Int, value: Int) {
        val intent = Intent(requireActivity(), PaymentActivity::class.java)
        intent.putExtra("quantity", 30)
        intent.putExtra("priceForJar", jarPrice)
        intent.putExtra("sellerId", sellerId)
        intent.putExtra("type", type)
        index = value
        activityLauncher.launch(intent)
    }
}