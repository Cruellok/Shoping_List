package com.persAssistant.shopping_list.presentation.purchase

import android.app.Application
import com.persAssistant.shopping_list.data.database.DbStruct
import com.persAssistant.shopping_list.data.database.enitities.Purchase
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditorPurchaseViewModel(application: Application, private var purchaseId: Long): PurchaseViewModel(application) {

    init {
        val app = getApplication<App>()
        app.purchaseService.getById(purchaseId)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                name.value = it.name
                price.value = it.price.toString()
                categoryId = it.categoryId
                listId = it.listId
                initCategoryName(app,categoryId)
            }, {})
    }

    private fun initCategoryName (app: App, categoryId: Long){
        app.categoryService.getById(categoryId)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                categoryName.value = it.name
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