package com.persAssistant.shopping_list.domain.interactors

import com.persAssistant.shopping_list.domain.enitities.Category
import com.persAssistant.shopping_list.domain.interactor_interfaces.CategoryInteractorInterface
import com.persAssistant.shopping_list.domain.interactor_repositories.CategoryRepositoryInterface

class CategoryInteractor(private val categoryRepositoryInterface: CategoryRepositoryInterface):
    CategoryInteractorInterface() {

    override fun getChangeSingle() {
        categoryRepositoryInterface.getChangeSingle()
    }

    override fun insert(category: Category) {
        categoryRepositoryInterface.insert(category)
    }

    override fun getAll() {
        categoryRepositoryInterface.getAll()
    }

    override fun getById(id: Long) {
        categoryRepositoryInterface.getById(id)
    }

    override fun update(category: Category) {
        categoryRepositoryInterface.update(category)
    }

    override fun delete(category: Category) {
        categoryRepositoryInterface.delete(category)
    }

}