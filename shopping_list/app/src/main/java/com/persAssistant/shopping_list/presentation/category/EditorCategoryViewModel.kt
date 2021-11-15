package com.persAssistant.shopping_list.presentation.category

import android.app.Application
import com.persAssistant.shopping_list.domain.enitities.Category
import com.persAssistant.shopping_list.presentation.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditorCategoryViewModel(application: Application, private var id: Long): CategoryViewModel(application) {

    init {
        val app = getApplication<App>()
        app.categoryService.getById(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                name.value = it.name
            }, {})
    }

    override fun save() {
        val app = getApplication<App>()
        val category = Category(id = id, name = name.value ?: "")
        app.categoryService.update(category)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }

}