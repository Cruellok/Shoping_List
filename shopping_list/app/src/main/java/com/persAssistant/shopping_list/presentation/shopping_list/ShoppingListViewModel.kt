package com.persAssistant.shopping_list.presentation.shopping_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

abstract class ShoppingListViewModel (application: Application): AndroidViewModel(application) {
    var closeEvent = MutableLiveData<Unit>()
    var name = MutableLiveData<String>()
    var strDate = MutableLiveData<String>()
    var date = Date()
    set(value) {
        field = value
        strDate.value = SimpleDateFormat("dd.MM.yyyy").format(date)
    }

    init {
        date = Date()
    }

    abstract fun save()
}