package com.persAssistant.shopping_list.presentation.list_of_purchase_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.persAssistant.shopping_list.domain.enitities.Purchase
import com.persAssistant.shopping_list.domain.interactors.PurchaseInteractor
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ListOfPurchaseViewModel(application: Application): AndroidViewModel(application)  {

    var purchaseInteractor: PurchaseInteractor
    var purchaseList = MutableLiveData<LinkedList<Purchase>>()
    var deletePurchaseId = MutableLiveData<Long>()

    private var entity = ""
    private val categories = "categories"
    private val shoppingList = "shoppingList"

    init {
        val app = getApplication<App>()
        purchaseInteractor = app.purchaseInteractor
    }

    fun init(lifecycleOwner: LifecycleOwner, categoryId: Long, shoppingListId: Long){
        if(shoppingListId == -1L && categoryId != -1L) {
            entity = categories
            getAllByCategoryId(categoryId)
        }else if(shoppingListId != -1L && categoryId == -1L) {
            entity = shoppingList
            getAllByListId(shoppingListId)
        }else
            throw Exception("Ошибка в ListOfPurchaseViewModel отсутствует listId или categoryId")

        purchaseInteractor.getChangeSingle().observe(lifecycleOwner, androidx.lifecycle.Observer {
            when (entity) {
                categories -> getAllByCategoryId(categoryId)
                shoppingList -> getAllByListId(shoppingListId)
                else -> false
            }
        })
    }

    fun deleteItemPurchase(purchase: Purchase){
        purchaseInteractor.delete(purchase)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Выполнено*/
                deletePurchaseId.value = purchase.id
            }, {/*Ошибка*/ })
    }

    private fun getAllByCategoryId(id: Long) {
        purchaseInteractor.getAllByCategoryId(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                purchaseList.value = it
            }, {/*Ошибка*/ })
    }
    private fun getAllByListId(id: Long) {
        purchaseInteractor.getAllByListId(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                purchaseList.value = it
            }, {/*Ошибка*/ })
    }
}