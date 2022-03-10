package com.example.mydiary

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity(), ClickListener {

    private lateinit var floatingInputButton: View
    private lateinit var viewModel: DiaryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter: DiaryRVAdapter = DiaryRVAdapter(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(DiaryViewModel::class.java)
        viewModel.allDiaryNotes.observe(this, Observer { list->
            list?.let {
                adapter.updateItems(it)
            }
        })

        floatingInputButton = findViewById(R.id.input)
        floatingInputButton.setOnClickListener {
            val intent: Intent = Intent(this, WritingActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onItemClicked(diaryContent: DiaryContent) {
        viewModel.delete(diaryContent)
        Toast.makeText(this, "${diaryContent.heading} is deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onViewClicked(diaryContent: DiaryContent) {
        val intent = Intent(this, WritingActivity::class.java)
        intent.putExtra("text", diaryContent.text)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            Log.e("", "RESULT IS OKAY")
            val heading: String = data?.getStringExtra("heading").toString()
            val date: String = data?.getStringExtra("date").toString()
            val content: String = data?.getStringExtra("content").toString()
            viewModel.insert(DiaryContent(heading, date, content))
            Toast.makeText(this, "$heading is added", Toast.LENGTH_SHORT).show()
        }
    }

}