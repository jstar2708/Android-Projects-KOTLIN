package Models

class Users {

    private var userName: String? = null
    private var profilePic: String? = null
    private var email: String? = null
    private var password: String? = null
    private lateinit var userId: String
    private var lastMessage: String? = null


    constructor(userName: String,
                profilePic: String,
                email: String,
                password: String,
                userId: String,
                lastMessage: String){
        this.lastMessage = lastMessage
        this.userId = userId
        this.userName = userName
        this.email = email
        this.password = password
        this.profilePic = profilePic
    }

    constructor(){

    }

    //for signup
    constructor(userName: String,
                email: String,
                password: String, ){
        this.userName = userName
        this.email = email
        this.password = password
    }


    fun setUserName(userName: String){
        this.userName = userName
    }
    fun getUserName(): String?{
        return userName
    }

    fun setProfilePic(profilePic: String){
        this.profilePic = profilePic
    }

    fun getProfilePic(): String?{
        return profilePic
    }

    fun setEmail(email: String){
        this.email = email
    }

    fun getEmail(): String?{
        return email
    }

    fun setPassword(password: String){
        this.password = password
    }

    fun getPassword(): String?{
        return password
    }

    fun setUserId(userId: String){
        this.userId = userId
    }

    fun getUserId(): String{
        return userId
    }

    fun setLastMessage(lastMessage: String){
        this.lastMessage = lastMessage
    }

    fun getLastMessage(): String?{
        return lastMessage
    }
}