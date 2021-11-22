package com.persAssistant.shopping_list.presentation.purchase

import android.app.Application
import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.domain.interactor_interfaces.FullPurchaseInteractorInterface
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreatorPurchaseViewModel(val fullPurchaseInteractor: FullPurchaseInteractorInterface,
                               shoppingListId: Long) :
    PurchaseViewModel() {

    init {
        listId = shoppingListId
        initCategoryName(categoryId)
    }

    private fun initCategoryName ( categoryId: Long){
        fullPurchaseInteractor.getById(categoryId)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                categoryName.value = it.second.name
            }, {})
    }

    override fun save() {
        if(price.value == null)
            price.value = "0"

        val purchase = Purchase(name = name.value ?: "", categoryId = categoryId, listId = listId, price = price.value?.toDouble(), isCompleted = 0)
        fullPurchaseInteractor.insert(purchase)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }
}