package com.example.bookipedia

class Book(private val title : String,private val author : String) {
    public fun getTitle() : String{
        return this.title
    }

    public fun getAuthor() : String{
        return this.author
    }
}