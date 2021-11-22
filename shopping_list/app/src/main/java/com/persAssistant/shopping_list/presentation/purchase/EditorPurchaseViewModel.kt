package com.persAssistant.shopping_list.presentation.purchase

import com.persAssistant.shopping_list.data.database.DbStruct
import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.domain.interactor_interfaces.FullPurchaseInteractorInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditorPurchaseViewModel (val fullPurchaseInteractor: FullPurchaseInteractorInterface,private var purchaseId: Long): PurchaseViewModel() {

    init {
        fullPurchaseInteractor.getById(purchaseId)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                categoryName.value = it.second.name
                name.value = it.first.name
                price.value = it.first.price.toString()
                categoryId = it.first.categoryId
                listId = it.first.listId
            }, {})
    }

    override fun save() {
        if(listId != DbStruct.ShoppingListTable.Cols.INVALID_ID){
            if(price.value == null)
                price.value = "0"

            val purchase = Purchase(id = purchaseId, name = name.value ?: "", categoryId = categoryId, listId = listId, price = price.value?.toDouble(), isCompleted = 0)
            fullPurchaseInteractor.update(purchase)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    closeEvent.value = Unit
                }, {})
        }
    }
}