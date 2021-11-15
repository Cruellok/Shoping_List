package com.persAssistant.shopping_list.data.database.repositories

import com.persAssistant.shopping_list.domain.interactor_repositories.CategoryRepositoryInterface
import com.persAssistant.shopping_list.data.database.service.CategoryService
import com.persAssistant.shopping_list.domain.enitities.Category

class CategoryRepository(private val categoryService: CategoryService): CategoryRepositoryInterface() {

    override fun getChangeSingle() {
        categoryService.getChangeSingle()
    }

    override fun insert(category: Category) {
        categoryService.insert(category)
    }

    override fun getAll() {
        categoryService.getAll()
    }

    override fun getById(id: Long) {
        categoryService.getById(id)
    }

    override fun update(category: Category) {
        categoryService.update(category)
    }

    override fun delete(category: Category) {
        categoryService.delete(category)
    }

}