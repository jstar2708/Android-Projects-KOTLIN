package com.example.waterjarmanagement.seller.model

import com.example.waterjarmanagement.customer.models.Customer

class Seller {
    private lateinit var companyName: String
    private lateinit var email: String
    private lateinit var userId: String
    private lateinit var cin: String
    private lateinit var password: String
    private lateinit var address: String
    private lateinit var ownerName: String
    private var jarPrice: Int = 0
    private var rating: Float = 0f
    private var customerCareNumber: Long = 0
    private var totalJars: Int = 0
    private var jarsUsed: Int = 0
    private var customerCount: Int = 0
    private var customers: List<Customer> = emptyList()
    private var pinCode: Int = 0
    private lateinit var state: String
    private lateinit var city: String

    constructor(){}
    constructor(
        city: String,
        state: String,
        pinCode: Int,
        userId: String,
        companyName: String,
        email: String,
        cin: String,
        password: String,
        address: String,
        ownerName: String,
        jarPrice: Int,
        rating: Float,
        customerCareNumber: Long,
        totalJars: Int,
        jarsUsed: Int,
        customerCount: Int,
        customers: List<Customer>
    ) {
        this.state = state
        this.pinCode = pinCode
        this.userId = userId
        this.companyName = companyName
        this.email = email
        this.cin = cin
        this.password = password
        this.address = address
        this.ownerName = ownerName
        this.jarPrice = jarPrice
        this.rating = rating
        this.customerCareNumber = customerCareNumber
        this.totalJars = totalJars
        this.jarsUsed = jarsUsed
        this.customerCount = customerCount
        this.customers = customers
        this.city = city
    }

    fun getCity(): String{
        return this.city
    }

    fun setCity(value: String){
        this.city = value
    }

    fun getState(): String{
        return this.state
    }

    fun setState(state: String){
        this.state = state
    }

    fun getPinCode(): Int{
        return this.pinCode
    }

    fun setPinCode(pinCode: Int){
        this.pinCode = pinCode
    }

    fun getUserId(): String{
        return this.userId
    }

    fun setUserId(id: String){
        this.userId = id
    }


    public fun setCompanyName(companyName: String){
        this.companyName = companyName
    }

    public fun getCompanyName(): String{
        return this.companyName
    }

    public fun setEmail(email: String){
        this.email = email
    }

    public fun getEmail(): String{
        return this.email
    }

    public fun setCin(cin: String){
        this.cin = cin
    }

    public fun getCin(): String{
        return this.cin
    }

    public fun setPassword(password: String){
        this.password = password
    }

    public fun getPassword(): String{
        return this.password
    }

    fun setCustomerCareNumber(number: Long){
        this.customerCareNumber = number
    }

    fun getCustomerCareNumber(): Long{
        return this.customerCareNumber
    }

    fun setAddress(address: String){
        this.address = address
    }

    fun getAddress(): String{
        return this.address
    }

    fun setOwnerName(ownerName: String){
        this.ownerName = ownerName
    }

    fun getOwnerName(): String{
        return this.ownerName
    }

    fun getTotalJars(): Int{
        return this.totalJars
    }

    fun setTotalJars(count: Int){
        this.totalJars = count
    }

    fun setJarUsed(count: Int){
        this.jarsUsed = count
    }
    fun getJarUsed(): Int{
        return this.jarsUsed
    }

    fun getJarPrice(): Int{
        return this.jarPrice
    }

    fun setJarPrice(price: Int){
        this.jarPrice = price
    }

    fun setRating(rating: Float){
        this.rating = rating
    }

    fun getRating(): Float{
        return this.rating
    }

    fun setCustomerCount(count: Int) {
        this.customerCount = count
    }

    fun getCustomerCount(): Int{
        return this.customerCount
    }

    fun setCustomers(list: List<Customer>){
        this.customers = list
    }

    fun getCustomers(): List<Customer>{
        return this.customers
    }

}