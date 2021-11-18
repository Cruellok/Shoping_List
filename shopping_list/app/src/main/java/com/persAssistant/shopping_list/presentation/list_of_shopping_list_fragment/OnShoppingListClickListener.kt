package com.persAssistant.shopping_list.presentation.list_of_shopping_list_fragment

import com.persAssistant.shopping_list.domain.entities.ShoppingList

interface OnShoppingListClickListener {
    fun clickedShoppingListItem(shoppingList: ShoppingList)
    fun deleteItem(shoppingList: ShoppingList)
    fun editItem(shoppingList: ShoppingList)
}