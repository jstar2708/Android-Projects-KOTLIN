package models

class Messages {
    private lateinit var id: String
    private lateinit var message: String
    private var time: Long = 0

    constructor(id: String, message: String, time: Long) {
        this.id = id
        this.message = message
        this.time = time
    }

    constructor()

    constructor(id: String, message: String) {
        this.id = id
        this.message = message
    }

    fun setId(id: String){
        this.id = id
    }

    fun setMessage(message: String){
        this.message = message
    }

    fun getId(): String{
        return id;
    }

    fun getMessage(): String{
        return message;
    }

    fun setTime(time: Long){
        this.time = time
    }

    fun getTime(): Long{
        return time
    }
}