package com.persAssistant.shopping_list.presentation.list_of_purchase_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.domain.interactors.FullPurchaseInteractor
import com.persAssistant.shopping_list.domain.interactors.PurchaseInteractor
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ListOfPurchaseViewModel(application: Application): AndroidViewModel(application)  {

    private val fullPurchaseInteractor: FullPurchaseInteractor
    var name = MutableLiveData<String>()
    var purchaseListPair = MutableLiveData<LinkedList<Pair<Purchase, Category>>>()

    var deletePurchaseId = MutableLiveData<Long>()
    private var enum = false

    init {
        val app = getApplication<App>()
        fullPurchaseInteractor = app.fullPurchaseInteractor
    }

    fun init(lifecycleOwner: LifecycleOwner, categoryId: Long, shoppingListId: Long){
        enum = when {
            categoryId != -1L -> {
                initByCategoryId(categoryId)
                true
            }
            shoppingListId != -1L -> {
                initByListId(shoppingListId)
                false
            }
            else -> throw Exception("Ошибка в ListOfPurchaseViewModel отсутствует listId и categoryId")
        }

        fullPurchaseInteractor.getChangeSingle().observe(lifecycleOwner, androidx.lifecycle.Observer {
            when (enum) {
                true -> initByCategoryId(categoryId)
                false -> initByListId(shoppingListId)
            }
        })
    }

    fun deleteItemPurchase(purchase: Purchase){
        fullPurchaseInteractor.delete(purchase)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Выполнено*/ }, {/*Ошибка*/ })
    }

    private fun initByCategoryId(id: Long) {
        fullPurchaseInteractor.getAllByCategoryId(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                /*Есть данные*/
                purchaseListPair.value = it
            }, {/*Ошибка*/ })
    }

    private fun initByListId(id: Long) {
        fullPurchaseInteractor.getAllByListId(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                /*Есть данные*/
                purchaseListPair.value = it
            }, {/*Ошибка*/ })
    }
}