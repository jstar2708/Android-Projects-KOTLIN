package com.example.dbmvvmdemo.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.dbmvvmdemo.Product
import java.security.Provider

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var product: Product = Product("Hello")

    fun getProduct(): Product{
        return this.product
    }

}