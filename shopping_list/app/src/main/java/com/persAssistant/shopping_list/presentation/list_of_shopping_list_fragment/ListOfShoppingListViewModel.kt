package com.persAssistant.shopping_list.presentation.list_of_shopping_list_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.persAssistant.shopping_list.domain.enitities.ShoppingList
import com.persAssistant.shopping_list.data.database.service.ShoppingListService
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ListOfShoppingListViewModel(application: Application): AndroidViewModel(application)  {

    var shoppingListService: ShoppingListService
    var listOfShoppingList = MutableLiveData<LinkedList<ShoppingList>>()
    var deleteShoppingListId = MutableLiveData<Long>()

    init {
        val app = getApplication<App>()
        shoppingListService = app.shoppingListService
    }

    fun init(lifecycleOwner: LifecycleOwner){
        shoppingListService.getChangeSingle().observe(lifecycleOwner, androidx.lifecycle.Observer {
            initShoppingList()
        })
        initShoppingList()
    }

    private fun initShoppingList() {
        shoppingListService.getAll()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                listOfShoppingList.value = it
            }, {/*Ошибка*/ })
    }

    fun deleteItemShoppingList(shoppingList: ShoppingList){
        shoppingListService.delete(shoppingList)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Выполнено*/
                deleteShoppingListId.value = shoppingList.id
            }, {/*Ошибка*/ })
    }
}