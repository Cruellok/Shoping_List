package com.persAssistant.shopping_list.presentation.purchase

import android.app.Application
import com.persAssistant.shopping_list.data.database.enitities.Purchase
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreatorPurchaseViewModel(application: Application, private var listId: Long) : PurchaseViewModel(application) {

    override fun save() {
        val app = getApplication<App>()
        val purchase = Purchase(name = name.value ?: "", categoryId = app.defaultCategoryId, listId = listId, price = price.value?.toDouble(), isCompleted = 0)
        app.purchaseService.insert(purchase)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }
}