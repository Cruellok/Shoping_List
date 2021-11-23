package com.persAssistant.shopping_list.presentation.purchase

import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.domain.interactor_interfaces.FullPurchaseInteractorInterface
import com.persAssistant.shopping_list.domain.interactors.PurchaseInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreatorPurchaseViewModel @Inject constructor(val purchaseInteractor: PurchaseInteractor,
                                                   val fullPurchaseInteractor: FullPurchaseInteractorInterface) : PurchaseViewModel() {

    fun init (shoppingListId: Long){
        listId = shoppingListId
        initCategoryName(categoryId)
    }

    private fun initCategoryName ( categoryId: Long){
        fullPurchaseInteractor.getAllByCategoryId(categoryId)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.map {
                categoryName.value = it.category.name }
            }, {})
    }

    override fun save() {
        if(price.value == null)
            price.value = "0"

        val purchase = Purchase(name = name.value ?: "", categoryId = categoryId, listId = listId, price = price.value?.toDouble(), isCompleted = 0)
        purchaseInteractor.insert(purchase)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }
}