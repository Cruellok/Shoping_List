package com.persAssistant.shopping_list.data.database.repositories

import com.persAssistant.shopping_list.domain.interactor_repositories.ShoppingListRepositoryInterface
import com.persAssistant.shopping_list.data.database.service.ShoppingListService
import com.persAssistant.shopping_list.domain.enitities.ShoppingList

class ShoppingListRepository(private val shoppingListService: ShoppingListService): ShoppingListRepositoryInterface() {

    override fun getChangeSingle() {
        shoppingListService.getChangeSingle()
    }

    override fun insert(shoppingList: ShoppingList) {
        shoppingListService.insert(shoppingList)
    }

    override fun getAll() {
        shoppingListService.getAll()
    }

    override fun getById(id: Long) {
        shoppingListService.getById(id)
    }

    override fun update(shoppingList: ShoppingList) {
        shoppingListService.update(shoppingList)
    }

    override fun delete(shoppingList: ShoppingList) {
        shoppingListService.delete(shoppingList)
    }
}