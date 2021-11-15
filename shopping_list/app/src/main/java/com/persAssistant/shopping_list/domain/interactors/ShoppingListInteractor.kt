package com.persAssistant.shopping_list.domain.interactors

import com.persAssistant.shopping_list.domain.enitities.ShoppingList
import com.persAssistant.shopping_list.domain.interactor_interfaces.ShoppingListInteractorInterface
import com.persAssistant.shopping_list.domain.interactor_repositories.ShoppingListRepositoryInterface

class ShoppingListInteractor(private val shoppingListRepositoryInterface: ShoppingListRepositoryInterface): ShoppingListInteractorInterface() {
    override fun getChangeSingle() {
        shoppingListRepositoryInterface.getChangeSingle()
    }

    override fun insert(shoppingList: ShoppingList) {
        shoppingListRepositoryInterface.insert(shoppingList)
    }

    override fun getAll() {
        shoppingListRepositoryInterface.getAll()
    }

    override fun getById(id: Long) {
        shoppingListRepositoryInterface.getById(id)
    }

    override fun update(shoppingList: ShoppingList) {
        shoppingListRepositoryInterface.update(shoppingList)
    }

    override fun delete(shoppingList: ShoppingList) {
        shoppingListRepositoryInterface.delete(shoppingList)
    }

}