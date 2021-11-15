package com.persAssistant.shopping_list.presentation.shopping_list

import android.app.Application
import com.persAssistant.shopping_list.domain.enitities.ShoppingList
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditorShoppingListViewModel(application: Application, private var id: Long): ShoppingListViewModel(application) {

    init {
        val app = getApplication<App>()
        app.shoppingListInteractor.getById(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                name.value = it.name
                date = it.date
            }, {})
    }

    override fun save() {
        val app = getApplication<App>()
        val shoppingList = ShoppingList(id = id, name = name.value ?: "",date = date)
        app.shoppingListInteractor.update(shoppingList)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }
}