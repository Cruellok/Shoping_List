package com.persAssistant.shopping_list.presentation.purchase

import android.app.Application
import com.persAssistant.shopping_list.data.database.enitities.Purchase
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditorPurchaseViewModel(application: Application, private var id: Long, private var listId: Long): PurchaseViewModel(application) {

    init {
        val app = getApplication<App>()
        app.purchaseService.getById(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                name.value = it.name
                price.value = it.price.toString()
            }, {})
    }

    override fun save() {
        val app = getApplication<App>()
        val purchase = Purchase(id = id, name = name.value ?: "", categoryId = app.defaultCategoryId, listId = listId, price = price.value?.toDouble(), isCompleted = 0)
        app.purchaseService.update(purchase)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }
}