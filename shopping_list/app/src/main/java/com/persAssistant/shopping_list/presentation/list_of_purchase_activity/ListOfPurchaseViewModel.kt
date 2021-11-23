package com.persAssistant.shopping_list.presentation.list_of_purchase_activity

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.domain.entities.FullPurchase
import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.domain.interactor_interfaces.FullPurchaseInteractorInterface
import com.persAssistant.shopping_list.domain.interactors.PurchaseInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class ListOfPurchaseViewModel @Inject constructor(val purchaseInteractor: PurchaseInteractor,
                                                  val fullPurchaseInteractor: FullPurchaseInteractorInterface): ViewModel() {

    var purchaseListPair = MutableLiveData<LinkedList<FullPurchase>>()
    var deletePurchaseId = MutableLiveData<Long>()
    var name = MutableLiveData<String>()
    private var enum = false

    fun init(lifecycleOwner: LifecycleOwner, categoryId: Long, shoppingListId: Long,
             canCreatePurchase: Boolean){
        enum = when {
            canCreatePurchase -> {
                initByListId(shoppingListId)
                true
            }
            !canCreatePurchase -> {
                initByCategoryId(categoryId)
                false
            }

            else -> throw Exception("Ошибка в ListOfPurchaseViewModel отсутствует listId и categoryId")
        }

        purchaseInteractor.getChangeSingle().observe(lifecycleOwner, androidx.lifecycle.Observer {
            when (enum) {
                true -> initByListId(shoppingListId)
                false -> initByCategoryId(categoryId)
            }
        })
    }

    fun deleteItemPurchase(purchase: Purchase){
        purchaseInteractor.delete(purchase)
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