package com.persAssistant.shopping_list.presentation.shopping_list

import com.persAssistant.shopping_list.domain.entities.ShoppingList
import com.persAssistant.shopping_list.domain.interactor_interfaces.ShoppingListInteractorInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreatorShoppingListViewModel (val shoppingListInteractor: ShoppingListInteractorInterface):
    ShoppingListViewModel() {

    override fun save() {

        val shoppingList = ShoppingList(name = name.value ?: "",date = date)
        shoppingListInteractor.insert(shoppingList)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }
}