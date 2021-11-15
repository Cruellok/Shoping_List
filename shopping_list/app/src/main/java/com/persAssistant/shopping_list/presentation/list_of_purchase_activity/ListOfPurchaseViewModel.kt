package com.persAssistant.shopping_list.presentation.list_of_purchase_activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.persAssistant.shopping_list.domain.enitities.Purchase
import com.persAssistant.shopping_list.data.database.service.PurchaseService
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ListOfPurchaseViewModel(application: Application): AndroidViewModel(application)  {

    var purchaseService: PurchaseService
    var purchaseList = MutableLiveData<LinkedList<Purchase>>()
    var deletePurchaseId = MutableLiveData<Long>()

    val categories = "categories"
    val shoppingList = "shoppingList"

    init {
        val app = getApplication<App>()
        purchaseService = app.purchaseService
    }

    fun init(lifecycleOwner: LifecycleOwner, id: Long, entity: String){
        purchaseService.getChangeSingle().observe(lifecycleOwner, androidx.lifecycle.Observer {
            when (entity) {
                categories -> getAllByCategoryId(id)
                shoppingList -> getAllByListId(id)
                else -> false
            }
        })
    }

    fun deleteItemPurchase(purchase: Purchase){
        purchaseService.delete(purchase)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Выполнено*/
                deletePurchaseId.value = purchase.id
            }, {/*Ошибка*/ })
    }

    private fun getAllByCategoryId(id: Long) {
        purchaseService.getAllByCategoryId(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                purchaseList.value = it
            }, {/*Ошибка*/ })
    }
    private fun getAllByListId(id: Long) {
        purchaseService.getAllByListId(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                purchaseList.value = it
            }, {/*Ошибка*/ })
    }
}