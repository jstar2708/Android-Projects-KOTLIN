package com.example.myapplication

import android.view.View

interface ContactItemClicked {
    fun onCallButtonClicked(contact: Contact)
    fun onDeleteButtonClicked(contact: Contact)
}