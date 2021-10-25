package com.persAssistant.shopping_list.presentation.purchase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.persAssistant.shopping_list.data.database.DbStruct
import com.persAssistant.shopping_list.presentation.App
import java.lang.Exception

abstract class PurchaseViewModel(application: Application): AndroidViewModel(application) {
    var closeEvent = MutableLiveData<Unit>()
    var name = MutableLiveData<String>()
    var price = MutableLiveData<String>()
    var categoryId: Long
    var listId: Long = DbStruct.Purchase.Cols.INVALID_ID
    set(value) {
        field = value
    }

    init {
        val app = getApplication<App>()
        categoryId = app.defaultCategoryId
    }

    abstract fun save()
}