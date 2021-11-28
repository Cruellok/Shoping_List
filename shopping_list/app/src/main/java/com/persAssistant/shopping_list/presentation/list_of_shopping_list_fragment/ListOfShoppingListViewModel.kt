package com.persAssistant.shopping_list.presentation.list_of_shopping_list_fragment

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.persAssistant.shopping_list.domain.entities.ShoppingList
import com.persAssistant.shopping_list.domain.interactor_interfaces.ShoppingListInteractorInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ListOfShoppingListViewModel @Inject constructor(private val shoppingListInteractor: ShoppingListInteractorInterface):
    ViewModel()  {

    var listOfShoppingList = MutableLiveData<LinkedList<ShoppingList>>()
    var deleteShoppingListId = MutableLiveData<Long>()

    fun init(lifecycleOwner: LifecycleOwner){
        shoppingListInteractor.getChangeSignal().observe(lifecycleOwner, androidx.lifecycle.Observer {
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