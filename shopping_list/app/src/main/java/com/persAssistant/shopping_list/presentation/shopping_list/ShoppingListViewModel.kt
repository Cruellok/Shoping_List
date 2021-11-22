package com.persAssistant.shopping_list.presentation.shopping_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

abstract class ShoppingListViewModel : ViewModel() {
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