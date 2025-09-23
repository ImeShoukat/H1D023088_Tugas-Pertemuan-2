package com.example.ifunsoedmobile1_ime.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifunsoedmobile1_ime.data.model.BookDoc
import com.example.ifunsoedmobile1_ime.data.network.RetrofitInstance
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val _books = MutableLiveData<List<BookDoc>>()

    val books: LiveData<List<BookDoc>> = _books

    fun fetchBooks(query: String){
        viewModelScope.launch {
            try{
                val response = RetrofitInstance.api.searchBooks(query, 10)
                if (response.isSuccessful){
                    val result=response.body()?.docs ?:emptyList()
                    _books.value = result
                    Log.d("SUCCESS_GET_DATA", "$result")
                } else {
                    Log.e("API_ERROR", "${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", e.localizedMessage ?: "Unknown error")
            }
        }
    }
}