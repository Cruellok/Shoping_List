package com.persAssistant.shopping_list.presentation.shopping_list

import android.app.Application
import com.persAssistant.shopping_list.domain.enitities.ShoppingList
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreatorShoppingListViewModel (application: Application) : ShoppingListViewModel(application) {

    override fun save() {

        val app = getApplication<App>()
        val shoppingList = ShoppingList(name = name.value ?: "",date = date)
        app.shoppingListInteractor.insert(shoppingList)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }
}