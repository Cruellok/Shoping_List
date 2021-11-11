package com.persAssistant.shopping_list.presentation

import android.app.Application
import com.persAssistant.shopping_list.data.database.RoomDataBaseHelper
import com.persAssistant.shopping_list.data.database.service.CategoryService
import com.persAssistant.shopping_list.data.database.service.ShoppingListService
import com.persAssistant.shopping_list.data.database.service.PurchaseService

class App: Application() {

    lateinit var categoryService: CategoryService
    lateinit var purchaseService: PurchaseService
    lateinit var shoppingListService: ShoppingListService

    override fun onCreate() {
        super.onCreate()

        val dataBaseHelper = RoomDataBaseHelper.getInstance(this)

        val categoryDao = dataBaseHelper.getCategoryRoomDao()
        categoryService = CategoryService(categoryDao)

        val purchaseDao = dataBaseHelper.getPurchaseRoomDao()
        purchaseService = PurchaseService(purchaseDao)

        val shoppingListDao = dataBaseHelper.getShoppingListRoomDao()
        shoppingListService = ShoppingListService(shoppingListDao)

    }

}