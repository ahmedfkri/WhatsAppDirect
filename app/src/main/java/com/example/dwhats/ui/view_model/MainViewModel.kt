package com.example.dwhats.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dwhats.data.NumberW
import com.example.dwhats.local.NumbersDatabase
import kotlinx.coroutines.launch

class MainViewModel(val database: NumbersDatabase) : ViewModel() {

    private val _numbers = database.numbersDao().getAllNumbers()
    val numbers: LiveData<List<NumberW>> = _numbers

    fun hasCode(num: String): Boolean {
        return num.first() == '+'
    }

    fun insertNumber(number: String) {
        viewModelScope.launch {
            database.numbersDao().insertNumber(NumberW(num = number))
        }
    }

    fun deleteNumber(number: NumberW) {
        viewModelScope.launch {
            database.numbersDao().deleteNumber(number)
        }

    }


}