package com.example.bookipedia

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.w3c.dom.Text

open class BookAdapter(context : Context, res : Int, booklist : ArrayList<Book>) : ArrayAdapter<Book>(context, 0, booklist) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var  listView : View? = convertView;

        val currentBook : Book? = getItem(position)


        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.my_layout, parent, false);
        }

        val booktitle : TextView = listView?.findViewById(R.id.book_title_view) as TextView
        booktitle.setText(currentBook?.getTitle())

        val bookauthors : TextView = listView.findViewById(R.id.book_author_view) as TextView
        bookauthors.setText(currentBook?.getAuthor())

        return listView
    }
}