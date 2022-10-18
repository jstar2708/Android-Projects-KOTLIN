package com.example.waterjarmanagement.customer.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waterjarmanagement.R
import com.example.waterjarmanagement.customer.models.PayGo
import org.w3c.dom.Text

class PayGoAdapter(private val listener: OnPayGoItemClick): RecyclerView.Adapter<PayGoViewHolder>() {

    private val orderList = ArrayList<PayGo>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayGoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.payasyougo_layout, parent, false)
        val holder = PayGoViewHolder(view)
        holder.itemView.setOnClickListener {
            listener.onClickPayGoOrder(orderList[holder.adapterPosition].getSellerId(), orderList[holder.adapterPosition].getQuantity()
            , orderList[holder.adapterPosition].getJarPrice(), holder.adapterPosition)
        }
        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PayGoViewHolder, position: Int) {
        val payGo = orderList[position]
        holder.jarStatus.text = if(payGo.getIsMonthlyJarDelievered()){
            "Jar status: Delievered"
        }
        else{
            "Jar status: Not Delievered"
        }
        holder.progressBar.progress = 100
        holder.price.text = "Price For 1 Jar: " + payGo.getJarPrice()
        holder.sellerName.text = payGo.getSellerName()
        holder.payment.text = "₹ " + (payGo.getQuantity() * payGo.getJarPrice()).toString()
        holder.quantity.text = "  " + payGo.getQuantity().toString() + "\nJars"
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun updateList(it: ArrayList<PayGo>) {
        orderList.clear()
        orderList.addAll(it)

        notifyDataSetChanged()
    }
}

class PayGoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val sellerName: TextView = itemView.findViewById(R.id.sellerName)
    val jarStatus: TextView = itemView.findViewById(R.id.jarStatus)
    val price: TextView = itemView.findViewById(R.id.priceForOneJar)
    val payment: TextView = itemView.findViewById(R.id.payment)
    val quantity: TextView = itemView.findViewById(R.id.quantity)
    val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
}

interface OnPayGoItemClick{
    fun onClickPayGoOrder(sellerId: String, quantity: Int, jarPrice: Int, index: Int)
}