package com.example.waterjarmanagement.customer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waterjarmanagement.R
import com.example.waterjarmanagement.seller.model.Seller

class SellersListAdapter(private val listener: OnSellerItemClick): RecyclerView.Adapter<SellerViewHolder>() {

    private var sellerList: ArrayList<Seller> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.seller_layout, parent, false)
        val holder = SellerViewHolder(view)
        holder.itemView.setOnClickListener {
            listener.onClick(sellerList[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: SellerViewHolder, position: Int) {
        holder.address.text = sellerList[position].getAddress()
        holder.companyName.text = sellerList[position].getCompanyName()
        holder.price.text = sellerList[position].getJarPrice().toString()
        holder.ownerName.text = sellerList[position].getOwnerName()
        holder.rating.rating = sellerList[position].getRating()
    }

    override fun getItemCount(): Int {
        return sellerList.size
    }

    fun updateItems(arrayList: ArrayList<Seller>){
        sellerList.clear()
        sellerList.addAll(arrayList)

        notifyDataSetChanged()
    }

}

class SellerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val companyName: TextView = itemView.findViewById(R.id.companyName)
    val ownerName: TextView = itemView.findViewById(R.id.ownerName)
    val address: TextView = itemView.findViewById(R.id.address)
    val rating: RatingBar = itemView.findViewById(R.id.rating)
    val price: TextView = itemView.findViewById(R.id.price)
}
interface OnSellerItemClick{
    fun onClick(seller: Seller)
}