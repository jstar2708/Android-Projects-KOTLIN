package com.example.streetlight.authentication

class User {


    private lateinit var userId: String
    private lateinit var name: String
    private lateinit var phoneNumber: String
    private lateinit var gender: String
    private lateinit var password: String

    constructor(){}

    constructor(
        name: String,
        phoneNumber: String,
        gender: String,
        password: String
    ) {
        this.name = name
        this.phoneNumber = phoneNumber
        this.gender = gender
        this.password = password
    }

    fun setUserId(userId: String){
        this.userId = userId
    }

    fun getUserId(): String{
        return this.userId.toString()
    }

    fun getPhoneNumber(): String{
        return this.phoneNumber
    }

    fun getGender(): String{
        return this.gender
    }

    fun getName(): String{
        return this.name
    }

    fun getPassword(): String{
        return this.password
    }


}