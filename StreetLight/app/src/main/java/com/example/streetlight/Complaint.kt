package com.example.streetlight

class Complaint{

    private lateinit var uid: String
    private lateinit var name: String
    private lateinit var phoneNumber: String
    private var HSNCode: String? = null
    private var imageUrl: String? = null
    private var address: String? = null
    private var latitude: String? = null
    private var longitude: String? = null

    constructor()

    constructor(
        uid: String,
        name: String,
        phoneNumber: String,
        HSNCode: String?,
        imageUrl: String?,
        address: String?,
        latitude: String?,
        longitude: String?
    ) {
        this.uid = uid
        this.name = name
        this.phoneNumber = phoneNumber
        this.HSNCode = HSNCode
        this.imageUrl = imageUrl
        this.address = address
        this.latitude = latitude
        this.longitude = longitude
    }

    constructor(name: String, phoneNumber: String) {
        this.name = name
        this.phoneNumber = phoneNumber
    }

    fun getName(): String{
        return this.name
    }
    fun setName(name: String){
        this.name = name
    }
    fun getHSNCode(): String{
        return this.HSNCode.toString()
    }
    fun setHSNCode(HSNCode: String){
        this.HSNCode = HSNCode
    }
    fun getImageUrl(): String{
        return this.imageUrl.toString()
    }
    fun setImageUrl(imageUrl: String){
        this.imageUrl = imageUrl
    }
    fun getPhoneNumber(): String{
        return this.phoneNumber.toString()
    }
    fun setPhoneNumber(phoneNumber: String){
        this.phoneNumber = phoneNumber
    }
    fun getAddress(): String{
        return this.address.toString()
    }
    fun setAddress(address: String){
        this.address = address
    }
    fun getLatitude(): String{
        return this.latitude.toString()
    }
    fun setLatitude(latitude: String){
        this.latitude = latitude
    }
    fun getLongitude(): String{
        return this.longitude.toString()
    }
    fun setLongitude(longitude: String){
        this.longitude = longitude
    }
    fun getUid(): String{
        return this.uid
    }
    fun setUid(uid: String){
        this.uid = uid
    }
}