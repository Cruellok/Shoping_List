package com.persAssistant.shopping_list.presentation.list_of_category_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.persAssistant.shopping_list.data.database.dao.enitity.RoomCategory
import com.persAssistant.shopping_list.data.database.enitities.Category
import com.persAssistant.shopping_list.data.database.service.CategoryService
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ListOfCategoryViewModel(application: Application): AndroidViewModel(application)  {

    var categoryService: CategoryService
    var listCategory = MutableLiveData<LinkedList<Category>>()
    var deleteCategoryId = MutableLiveData<Long>()

    init {
        val app = getApplication<App>()
        categoryService = app.categoryService
        getAllCategories()
    }

    var onChanges = categoryService.getChangeSingle()

    fun getAllCategories() {
        categoryService.getAll()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                listCategory.value = it
            }, {/*Ошибка*/ })
    }

    fun deleteItemCategory(category: Category){
        categoryService.delete(category)
        .subscribeOn(Schedulers.single())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({/*Выполнено*/
            deleteCategoryId.value = category.id
        }, {/*Ошибка*/ })
    }
}