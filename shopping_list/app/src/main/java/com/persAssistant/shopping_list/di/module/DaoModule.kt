package com.persAssistant.shopping_list.di.module

import com.persAssistant.shopping_list.data.database.RoomDataBaseHelper
import com.persAssistant.shopping_list.data.database.dao.CategoryRoomDao
import com.persAssistant.shopping_list.data.database.dao.PurchaseRoomDao
import com.persAssistant.shopping_list.data.database.dao.ShoppingListRoomDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {
    @Provides
    @Singleton
    fun provideCategoryRoomDao(roomDataBaseHelper: RoomDataBaseHelper): CategoryRoomDao {
        return roomDataBaseHelper.getCategoryRoomDao()
    }

    @Provides
    @Singleton
    fun providePurchaseRoomDao(roomDataBaseHelper: RoomDataBaseHelper): PurchaseRoomDao {
        return roomDataBaseHelper.getPurchaseRoomDao()
    }

    @Provides
    @Singleton
    fun provideShoppingListRoomDao(roomDataBaseHelper: RoomDataBaseHelper): ShoppingListRoomDao {
        return roomDataBaseHelper.getShoppingListRoomDao()
    }
}