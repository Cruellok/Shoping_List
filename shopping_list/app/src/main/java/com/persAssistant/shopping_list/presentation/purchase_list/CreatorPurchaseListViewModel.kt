package com.persAssistant.shopping_list.presentation.purchase_list

import android.app.Application
import com.persAssistant.shopping_list.data.database.enitities.PurchaseList
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreatorPurchaseListViewModel (application: Application) : PurchaseListViewModel(application) {

    override fun save() {

        val app = getApplication<App>()
        val purchaseList = PurchaseList(name = name.value ?: "",date = date)
        app.purchaseListService.insert(purchaseList)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }
}