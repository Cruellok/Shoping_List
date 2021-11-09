package com.persAssistant.shopping_list.presentation.list_of_category_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.persAssistant.shopping_list.data.database.enitities.Category
import com.persAssistant.shopping_list.data.database.service.CategoryService
import com.persAssistant.shopping_list.presentation.App
import com.persAssistant.shopping_list.presentation.list_of_category_fragment.adapter.CategoryAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListOfCategoryViewModel(application: Application): AndroidViewModel(application)  {

    lateinit var categoryAdapter: CategoryAdapter
    var categoryService: CategoryService

    init {
        val app = getApplication<App>()
        categoryService = app.categoryService
        getAllCategories()
    }

    fun getAllCategories() {
        categoryService.getAll()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                categoryAdapter.updateItems(it)
            }, {/*Ошибка*/ })
    }

    fun deleteItemCategory(category: Category){
        categoryService.delete(category)
        .subscribeOn(Schedulers.single())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({/*Выполнено*/
            categoryAdapter.removeCategory(category.id)
        }, {/*Ошибка*/ })
    }
}