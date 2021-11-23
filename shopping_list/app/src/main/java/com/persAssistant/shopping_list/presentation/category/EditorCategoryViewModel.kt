package com.persAssistant.shopping_list.presentation.category

import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.domain.interactor_interfaces.CategoryInteractorInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditorCategoryViewModel @Inject constructor(val categoryInteractor: CategoryInteractorInterface): CategoryViewModel() {

    private var categoryId: Long = 0

    fun init(id: Long) {
        categoryId = id

        categoryInteractor.getById(id)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                name.value = it.name
            }, {})
    }

    override fun save() {
        val category = Category(id = categoryId, name = name.value ?: "")
        categoryInteractor.update(category)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                closeEvent.value = Unit
            }, {})
    }

}