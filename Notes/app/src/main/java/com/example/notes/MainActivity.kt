package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesRVListener {

    private lateinit var viewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter =  NoteRVAdapter(this, this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer{list->
            //Checking if list is null or not
            list?.let {
                adapter.updateItems(it)
            }
        })

        val submitButton: Button = findViewById(R.id.submit)
        val editText: EditText = findViewById(R.id.input)
        submitButton.setOnClickListener {
            if(editText.text.toString().isNotEmpty()){
                viewModel.insert(Note(editText.text.toString()))
                Toast.makeText(this, "Note added", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onItemClicked(note: Note) {
        viewModel.delete(note)
        Toast.makeText(this, "Note deleted", Toast.LENGTH_LONG).show()
    }
}