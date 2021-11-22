package com.persAssistant.shopping_list.presentation.category

import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.domain.interactor_interfaces.CategoryInteractorInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditorCategoryViewModel(val categoryInteractor: CategoryInteractorInterface, private var id: Long): CategoryViewModel() {

    init {
        categoryInteractor.getById(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                name.value = it.name
            }, {})
    }

    override fun save() {

        val category = Category(id = id, name = name.value ?: "")
        categoryInteractor.update(category)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }

}