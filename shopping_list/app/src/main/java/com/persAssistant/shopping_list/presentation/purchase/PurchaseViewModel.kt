package com.persAssistant.shopping_list.presentation.purchase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.persAssistant.shopping_list.data.database.DbStruct
import com.persAssistant.shopping_list.data.database.enitities.Category

abstract class PurchaseViewModel(application: Application): AndroidViewModel(application) {
    var closeEvent = MutableLiveData<Unit>()
    var name = MutableLiveData<String>()
    var price = MutableLiveData<String>()
    var categoryId: Long = DbStruct.Category.Cols.DEFAULT_CATEGORIES_COUNT
    var listId: Long = DbStruct.PurchaseListTable.Cols.INVALID_ID
    var categoryName = MutableLiveData<String>()

    fun setCategory(category: Category){
        categoryId = category.id!!
        categoryName.value = category.name
    }
    abstract fun save()
}