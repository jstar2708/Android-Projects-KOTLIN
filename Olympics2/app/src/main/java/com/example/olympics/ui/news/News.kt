package com.example.olympics.ui.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class News(
    @ColumnInfo(name = "title")private val title: String,
    @ColumnInfo(name = "urlImage")private val urlImage: String,
    @ColumnInfo(name = "sourceUrl")private val sourceUrl: String,
    @ColumnInfo(name = "date")private val publishedAt: String,
    @ColumnInfo(name = "author")private val author: String,
    @ColumnInfo(name = "description")private val description: String){
    @PrimaryKey(autoGenerate = true) var id = 0

    fun getTitle(): String{
        return title
    }

    fun getUrlImage(): String{
        return urlImage
    }

    fun getSourceUrl(): String{
        return sourceUrl
    }

    fun getPublishedAt(): String{
        return publishedAt
    }

    fun getDescription(): String{
        return description
    }

    fun getAuthor(): String{
        return author
    }
}


