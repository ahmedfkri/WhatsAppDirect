package com.example.dwhats.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dwhats.local.NumbersDatabase

class MainViewModelFactory(
    val database: NumbersDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(database) as T
    }

}