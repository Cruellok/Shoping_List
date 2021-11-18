package com.persAssistant.shopping_list.presentation.list_of_category_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.domain.interactors.CategoryInteractor
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ListOfCategoryViewModel(application: Application): AndroidViewModel(application)  {

    var categoryInteractor: CategoryInteractor
    var categoryList = MutableLiveData<LinkedList<Category>>()
    var deleteCategoryId = MutableLiveData<Long>()

    init {
        val app = getApplication<App>()
        categoryInteractor = app.categoryInteractor
    }

    fun init(lifecycleOwner: LifecycleOwner){
        categoryInteractor.getChangeSingle().observe(lifecycleOwner, androidx.lifecycle.Observer {
            initCategoryList()
        })
        initCategoryList()
    }

    private fun initCategoryList() {
        categoryInteractor.getAll()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                categoryList.value = it
            }, {/*Ошибка*/ })
    }

    fun deleteItemCategory(category: Category){
        categoryInteractor.delete(category)
        .subscribeOn(Schedulers.single())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({/*Выполнено*/
            deleteCategoryId.value = category.id
        }, {/*Ошибка*/ })
    }
}