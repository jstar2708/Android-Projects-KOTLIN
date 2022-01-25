package com.example.bookipedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import java.lang.reflect.Executable
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BookList : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        val progressBar : ProgressBar = findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
        val topic : String? = intent.getStringExtra("topic")
        val executorService : ExecutorService = Executors.newFixedThreadPool(4)

        executorService.execute {
            val bookList : ArrayList<Book> = Tasks().makeHttpRequest(topic)

            runOnUiThread {
                val booklistView : ListView = findViewById(R.id.book_list_view)
                if(bookList.size == 0){
                    Log.e("", "List is Empty")

                    val bookAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, listOf("No Books Found"))
                    progressBar.visibility = View.INVISIBLE
                    booklistView.adapter = bookAdapter
                }
                else{
                    val bookAdapter : BookAdapter = BookAdapter(this, 0, bookList)
                    progressBar.visibility = View.INVISIBLE
                    booklistView.adapter = bookAdapter
                }

            }
        }
    }
}