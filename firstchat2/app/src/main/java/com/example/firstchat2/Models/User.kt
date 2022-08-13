package com.example.firstchat2.Models

class User {

    private lateinit var profilePic: String
    private lateinit var userName: String
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var userId: String
    private lateinit var lastMessage: String

    constructor(){}

    constructor(profilePic: String, userName: String, password: String, email: String, lastMessage: String, userId: String){
        this.email = email
        this.profilePic = profilePic
        this.userId = userId
        this.userName = userName
        this.lastMessage = lastMessage
        this.password = password
    }

    constructor(email: String, userName: String, password: String){
        this.userName = userName
        this.email = email
        this.password = password
    }

    fun getEmail(): String{
        return this.email
    }

    fun setEmail(email: String){
        this.email = email
    }

    fun getProfilePic(): String{
        return this.profilePic
    }

    fun setProfilePic(profilePic: String){
        this.profilePic = profilePic
    }

    fun getPassword(): String{
        return this.password
    }

    fun setPassword(password: String){
        this.password = password
    }

    fun getLastMessage(): String {
        return this.lastMessage
    }

    fun setLastMessage(lastMessage: String){
        this.lastMessage = lastMessage
    }

    fun getUserId(): String{
        return this.userId
    }

    fun setUserId(userId: String){
        this.userId = userId
    }

    fun getUserName(): String{
        return this.userName
    }

    fun setUserName(userName: String){
        this.userName = userName
    }

}