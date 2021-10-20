package com.persAssistant.shopping_list.presentation.purchaseList

import android.app.Application
import com.persAssistant.shopping_list.data.database.enitities.PurchaseList
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class EditorPurchaseListViewModel(application: Application, private var id: Long): PurchaseListViewModel(application) {

    init {

        val app = getApplication<App>()
        app.purchaseListService.getById(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                name.value = it.name
                date = it.date
            }, {})
    }

    override fun save() {

        val app = getApplication<App>()
        val purchaseList = PurchaseList(id = id, name = name.value ?: "",date = date)
        app.purchaseListService.update(purchaseList)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }



}