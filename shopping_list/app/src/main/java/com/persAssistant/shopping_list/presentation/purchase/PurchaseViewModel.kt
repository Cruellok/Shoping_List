package com.persAssistant.shopping_list.presentation.purchase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.persAssistant.shopping_list.data.database.DbStruct
import com.persAssistant.shopping_list.domain.entities.Category

abstract class PurchaseViewModel: ViewModel() {
    var closeEvent = MutableLiveData<Unit>()
    var name = MutableLiveData<String>()
    var price = MutableLiveData<String>()
    var categoryId: Long = DbStruct.Category.Cols.DEFAULT_CATEGORIES_COUNT
    var listId: Long = DbStruct.ShoppingListTable.Cols.INVALID_ID
    var categoryName = MutableLiveData<String>()

    fun setCategory(category: Category){
        categoryId = category.id!!
        categoryName.value = category.name
    }
    abstract fun save()
}