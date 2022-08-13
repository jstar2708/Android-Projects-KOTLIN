package com.example.waterjarmanagement.customer.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waterjarmanagement.customer.models.Jars
import com.example.waterjarmanagement.R

class OrderAdapter(private val listener: OnOrderViewClicked): RecyclerView.Adapter<OrderViewHolder>() {

    private var orderList: ArrayList<Jars> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_layout, parent, false)
        val holder = OrderViewHolder(view)
        holder.itemView.setOnClickListener {
            listener.onClickOrder(orderList[holder.adapterPosition].getSellerId())
        }
        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val jar = orderList[position]
        holder.price.text = "₹ " + (jar.getJarPrice() * 30).toString()
        val str = if(jar.getIsMonthlyJarDelievered()){
            "Today's Jar: Delievered"
        }
        else{
            "Today's Jar: Not Delievered"
        }
        val float: Float = (jar.getMonthlyJars() / 30.0f) * 100f
        holder.jarStatus.text = str
        holder.jarsLeft.text = " " + jar.getMonthlyJars().toString() + "\n Left"
        holder.progressBar.progress = (float).toInt()
        holder.priceForOneJar.text = "Price For 1 Jar: " + jar.getJarPrice()
        holder.shopName.text = jar.getSellerName()
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun updateList(list: ArrayList<Jars>){
        orderList.clear()
        orderList.addAll(list)
        notifyDataSetChanged()
    }
}

class OrderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val shopName: TextView = itemView.findViewById(R.id.sellerName)
    val jarStatus: TextView = itemView.findViewById(R.id.isJarDelivered)
    val priceForOneJar: TextView = itemView.findViewById(R.id.priceForOneJar)
    val price: TextView = itemView.findViewById(R.id.payment)
    val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    val jarsLeft: TextView = itemView.findViewById(R.id.jarsLeft)
}

interface OnOrderViewClicked{
    fun onClickOrder(sellerId: String)
}