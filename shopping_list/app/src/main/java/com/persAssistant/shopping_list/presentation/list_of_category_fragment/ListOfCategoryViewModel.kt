package com.persAssistant.shopping_list.presentation.list_of_category_fragment

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.domain.interactor_interfaces.CategoryInteractorInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ListOfCategoryViewModel @Inject constructor(val categoryInteractor: CategoryInteractorInterface): ViewModel()  {

    var categoryList = MutableLiveData<LinkedList<Category>>()
    var deleteCategoryId = MutableLiveData<Long>()

    fun init(lifecycleOwner: LifecycleOwner){
        categoryInteractor.getChangeSignal().observe(lifecycleOwner, androidx.lifecycle.Observer {
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