package com.example.waterjarmanagement.customer.models

class Jars{

    private var monthlyJars: Int = 0
    private lateinit var sellerId: String
    private var isMonthlyJarDelivered: Boolean = false
    private var jarPrice: Int = 0
    private lateinit var sellerName: String

    constructor(){}
    constructor(monthlyJars: Int, sellerId: String, isMonthlyJarDelivered: Boolean, jarPrice: Int, sellerName: String) {
        this.monthlyJars = monthlyJars
        this.sellerId = sellerId
        this.isMonthlyJarDelivered = isMonthlyJarDelivered
        this.jarPrice = jarPrice
        this.sellerName = sellerName
    }

    fun getSellerName(): String{
        return this.sellerName
    }

    fun setSellerName(value: String){
        this.sellerName = value
    }

    fun getJarPrice(): Int{
        return this.jarPrice
    }

    fun setJarPrice(value: Int){
        this.jarPrice = value
    }

    fun getMonthlyJars(): Int{
        return this.monthlyJars
    }

    fun setMonthlyJars(value: Int){
        this.monthlyJars = value
    }

    fun getSellerId(): String{
        return this.sellerId
    }
    fun setSellerId(value: String){
        this.sellerId = value
    }

    fun getIsMonthlyJarDelievered(): Boolean{
        return this.isMonthlyJarDelivered
    }

    fun setIsMonthlyJarDelievered(value: Boolean){
        this.isMonthlyJarDelivered = value
    }




}