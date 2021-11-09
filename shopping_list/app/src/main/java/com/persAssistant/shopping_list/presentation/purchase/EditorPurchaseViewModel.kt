package com.persAssistant.shopping_list.presentation.purchase

import android.app.Application
import com.persAssistant.shopping_list.data.database.DbStruct
import com.persAssistant.shopping_list.data.database.enitities.Category
import com.persAssistant.shopping_list.data.database.enitities.Purchase
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditorPurchaseViewModel(application: Application, private var purchaseId: Long): PurchaseViewModel(application) {

    init {
        val app = getApplication<App>()
        app.purchaseService.getById(purchaseId)
            .flatMap { purchase ->
                app.categoryService.getById(purchase.categoryId)
                    .map {
                        Pair<Purchase, Category>(purchase,it)
                    }
            }
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
        if(listId != DbStruct.PurchaseListTable.Cols.INVALID_ID){
            val app = getApplication<App>()
            if(price.value == null)
                price.value = "0"

            val purchase = Purchase(id = purchaseId, name = name.value ?: "", categoryId = categoryId, listId = listId, price = price.value?.toDouble(), isCompleted = 0)
            app.purchaseService.update(purchase)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    closeEvent.value = Unit
                }, {})
        }
    }
}