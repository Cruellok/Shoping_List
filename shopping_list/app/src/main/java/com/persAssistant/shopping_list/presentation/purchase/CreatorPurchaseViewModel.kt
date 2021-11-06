package com.persAssistant.shopping_list.presentation.purchase

import android.app.Application
import com.persAssistant.shopping_list.data.database.enitities.Purchase
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreatorPurchaseViewModel(application: Application, purchaseListId: Long) : PurchaseViewModel(application) {

    init {
        listId = purchaseListId
        val app = getApplication<App>()
        initCategoryName(app,categoryId)
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
        val app = getApplication<App>()
        if(price.value == null)
            price.value = "0"

        val purchase = Purchase(name = name.value ?: "", categoryId = categoryId, listId = listId, price = price.value?.toDouble(), isCompleted = 0)
        app.purchaseService.insert(purchase)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }
}