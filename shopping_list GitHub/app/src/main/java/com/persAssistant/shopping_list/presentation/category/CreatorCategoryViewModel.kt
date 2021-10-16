package com.persAssistant.shopping_list.presentation.category

import android.app.Application
import com.persAssistant.shopping_list.data.database.enitities.Category
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreatorCategoryViewModel(application: Application) : CategoryViewModel(application) {

    override fun save() {
        val app = getApplication<App>()
        val category = Category(name = name.value ?: "")
        app.categoryService.insert(category)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }
}