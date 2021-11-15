package com.persAssistant.shopping_list.domain.interactor_interfaces

import com.persAssistant.shopping_list.domain.enitities.Category

//abstract class CategoryInteractorInterface: CategoryRepositoryInterface()
abstract class CategoryInteractorInterface{
    abstract fun getChangeSingle()
    abstract fun insert(category: Category)
    abstract fun getAll()
    abstract fun getById(id: Long)
    abstract fun update(category: Category)
    abstract fun delete(category: Category)
}