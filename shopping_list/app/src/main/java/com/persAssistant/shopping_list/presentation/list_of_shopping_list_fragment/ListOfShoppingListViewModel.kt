package com.persAssistant.shopping_list.presentation.list_of_shopping_list_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.persAssistant.shopping_list.domain.enitities.ShoppingList
import com.persAssistant.shopping_list.domain.interactors.ShoppingListInteractor
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ListOfShoppingListViewModel(application: Application): AndroidViewModel(application)  {

    var shoppingListInteractor: ShoppingListInteractor
    var listOfShoppingList = MutableLiveData<LinkedList<ShoppingList>>()
    var deleteShoppingListId = MutableLiveData<Long>()

    init {
        val app = getApplication<App>()
        shoppingListInteractor = app.shoppingListInteractor
    }

    fun init(lifecycleOwner: LifecycleOwner){
        shoppingListInteractor.getChangeSingle().observe(lifecycleOwner, androidx.lifecycle.Observer {
            initShoppingList()
        })
        initShoppingList()
    }

    private fun initShoppingList() {
        shoppingListInteractor.getAll()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                listOfShoppingList.value = it
            }, {/*Ошибка*/ })
    }

    fun deleteItemShoppingList(shoppingList: ShoppingList){
        shoppingListInteractor.delete(shoppingList)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Выполнено*/
                deleteShoppingListId.value = shoppingList.id
            }, {/*Ошибка*/ })
    }
}