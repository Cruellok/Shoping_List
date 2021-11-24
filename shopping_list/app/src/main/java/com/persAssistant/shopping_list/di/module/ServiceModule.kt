package com.persAssistant.shopping_list.di.module

import com.persAssistant.shopping_list.data.database.dao.CategoryRoomDao
import com.persAssistant.shopping_list.data.database.dao.PurchaseRoomDao
import com.persAssistant.shopping_list.data.database.dao.ShoppingListRoomDao
import com.persAssistant.shopping_list.data.database.service.CategoryService
import com.persAssistant.shopping_list.data.database.service.PurchaseService
import com.persAssistant.shopping_list.data.database.service.ShoppingListService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ServiceModule{
    @Provides
    @Singleton
    fun provideCategoryService(categoryRoomDao: CategoryRoomDao): CategoryService {
        return CategoryService(categoryRoomDao)
    }

    @Provides
    @Singleton
    fun providePurchaseService(purchaseRoomDao: PurchaseRoomDao): PurchaseService {
        return PurchaseService(purchaseRoomDao)
    }

    @Provides
    @Singleton
    fun provideShoppingListService(shoppingListRoomDao: ShoppingListRoomDao): ShoppingListService {
        return ShoppingListService(shoppingListRoomDao)
    }
}