package com.persAssistant.shopping_list.presentation

import android.app.Application
import com.persAssistant.shopping_list.data.database.RoomDataBaseHelper
import com.persAssistant.shopping_list.data.database.repositories.CategoryRepository
import com.persAssistant.shopping_list.data.database.repositories.PurchaseRepository
import com.persAssistant.shopping_list.data.database.repositories.ShoppingListRepository
import com.persAssistant.shopping_list.data.database.service.CategoryService
import com.persAssistant.shopping_list.data.database.service.ShoppingListService
import com.persAssistant.shopping_list.data.database.service.PurchaseService
import com.persAssistant.shopping_list.domain.interactors.CategoryInteractor
import com.persAssistant.shopping_list.domain.interactors.PurchaseInteractor
import com.persAssistant.shopping_list.domain.interactors.ShoppingListInteractor

class App: Application() {

    lateinit var categoryService: CategoryService
    lateinit var purchaseService: PurchaseService
    lateinit var shoppingListService: ShoppingListService

    lateinit var categoryRepository: CategoryRepository
    lateinit var purchaseRepository: PurchaseRepository
    lateinit var shoppingListRepository: ShoppingListRepository

    lateinit var categoryInteractor: CategoryInteractor
    lateinit var purchaseInteractor: PurchaseInteractor
    lateinit var shoppingListInteractor: ShoppingListInteractor

    override fun onCreate() {
        super.onCreate()

        val dataBaseHelper = RoomDataBaseHelper.getInstance(this)

        val categoryDao = dataBaseHelper.getCategoryRoomDao()
        categoryService = CategoryService(categoryDao)
        categoryRepository = CategoryRepository(categoryService)
        categoryInteractor = CategoryInteractor(categoryRepository)

        val purchaseDao = dataBaseHelper.getPurchaseRoomDao()
        purchaseService = PurchaseService(purchaseDao)
        purchaseRepository = PurchaseRepository(purchaseService)
        purchaseInteractor = PurchaseInteractor(purchaseRepository)

        val shoppingListDao = dataBaseHelper.getShoppingListRoomDao()
        shoppingListService = ShoppingListService(shoppingListDao)
        shoppingListRepository = ShoppingListRepository(shoppingListService)
        shoppingListInteractor = ShoppingListInteractor(shoppingListRepository)
    }

}