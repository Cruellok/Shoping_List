package com.persAssistant.shopping_list.presentation.shopping_list

import com.persAssistant.shopping_list.domain.entities.ShoppingList
import com.persAssistant.shopping_list.domain.interactor_interfaces.ShoppingListInteractorInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditorShoppingListViewModel(private val shoppingListInteractor: ShoppingListInteractorInterface): ShoppingListViewModel() {

    private var  shoppingListId: Long = 0

    fun init(id: Long){
        shoppingListId = id

        shoppingListInteractor.getById(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                name.value = it.name
                date = it.date
            }, {})
    }

    override fun save() {
        val shoppingList = ShoppingList(id = shoppingListId, name = name.value ?: "",date = date)
        shoppingListInteractor.update(shoppingList)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }
}