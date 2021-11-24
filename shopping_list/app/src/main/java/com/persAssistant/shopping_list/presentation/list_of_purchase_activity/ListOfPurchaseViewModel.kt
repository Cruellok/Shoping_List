package com.persAssistant.shopping_list.presentation.list_of_purchase_activity

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.persAssistant.shopping_list.domain.entities.FullPurchase
import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.domain.interactor_interfaces.FullPurchaseInteractorInterface
import com.persAssistant.shopping_list.domain.interactor_interfaces.PurchaseInteractorInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class ListOfPurchaseViewModel @Inject constructor(val purchaseInteractor: PurchaseInteractorInterface,
                                                  val fullPurchaseInteractor: FullPurchaseInteractorInterface): ViewModel() {

    var purchaseListPair = MutableLiveData<LinkedList<FullPurchase>>()
    var deletePurchaseId = MutableLiveData<Long>()
    var name = MutableLiveData<String>()

    enum class IdTypes {
        CATEGORY,
        SHOPPINGLIST;
    }

    fun init(lifecycleOwner: LifecycleOwner, categoryId: Long, shoppingListId: Long, name: String){
        canCreatePurchase(categoryId,shoppingListId,name)

        purchaseInteractor.getChangeSingle().observe(lifecycleOwner, androidx.lifecycle.Observer {
            canCreatePurchase(categoryId,shoppingListId,name)
        })
    }

    private fun canCreatePurchase(categoryId: Long, shoppingListId: Long, name: String){
        when (name) {
            IdTypes.SHOPPINGLIST.name -> initByListId(shoppingListId)
            IdTypes.CATEGORY.name -> initByCategoryId(categoryId)
        }
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