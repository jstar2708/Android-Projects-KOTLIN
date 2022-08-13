package com.example.waterjarmanagement.customer.models

class Customer {

    private lateinit var password: String
    private lateinit var email: String
    private lateinit var userId: String
    private lateinit var address: String
    private lateinit var name: String
    var sellers: ArrayList<Jars> = ArrayList()
    var payGoOrders: ArrayList<PayGo> = ArrayList()
    private var phoneNumber: Long = 0
    private var pinCode: Int = 0
    private lateinit var state: String
    private lateinit var city: String

    constructor(){}
    constructor(
        city: String,
        payGoOrders: ArrayList<PayGo>,
        password: String,
        email: String,
        userId: String,
        address: String,
        name: String,
        sellers: ArrayList<Jars>,
        phoneNumber: Long,
        pinCode: Int,
        state: String,
    ) {
        this.city = city
        this.password = password
        this.email = email
        this.userId = userId
        this.address = address
        this.name = name
        this.sellers = sellers
        this.phoneNumber = phoneNumber
        this.pinCode = pinCode
        this.state = state
        this.payGoOrders = payGoOrders
    }

    fun getCity(): String{
        return this.city
    }

    fun setCity(value: String){
        this.city = value
    }

    fun getPinCode(): Int{
        return this.pinCode
    }

    fun setPinCode(pinCode: Int){
        this.pinCode = pinCode
    }

    fun setState(state: String){
        this.state = state
    }

    fun getState(): String{
        return this.state
    }

    fun getEmail(): String{
        return this.email
    }

    fun setEmail(email: String){
        this.email = email
    }

    fun getPassword(): String{
        return this.password
    }

    fun setPassword(password: String){
        this.password = password
    }

    fun getUserId(): String{
        return this.userId
    }

    fun setUserId(userId: String){
        this.userId = userId
    }

    fun setName(name: String){
        this.name = name
    }

    fun getName(): String{
        return this.name
    }

    fun setPhoneNumber(number: Long){
        this.phoneNumber = number
    }

    fun getPhoneNumber(): Long{
        return this.phoneNumber
    }


    fun setAddress(address: String){
        this.address = address
    }

    fun getAddress(): String{
        return this.address
    }

}