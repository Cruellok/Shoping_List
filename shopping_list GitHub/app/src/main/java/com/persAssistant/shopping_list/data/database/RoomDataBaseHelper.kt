package com.persAssistant.shopping_list.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.persAssistant.shopping_list.data.database.dao.CategoryRoomDao
import com.persAssistant.shopping_list.data.database.dao.PurchaseListRoomDao
import com.persAssistant.shopping_list.data.database.dao.PurchaseRoomDao
import com.persAssistant.shopping_list.data.database.dao.enitity.RoomCategory
import com.persAssistant.shopping_list.data.database.dao.enitity.RoomPurchase
import com.persAssistant.shopping_list.data.database.dao.enitity.RoomPurchaseList

@Database(
    entities = [RoomCategory::class, RoomPurchaseList::class, RoomPurchase::class],
    version = RoomDataBaseHelper.DATABASE_VERSION, exportSchema = false)
abstract class RoomDataBaseHelper : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "database_shopping_list.db"
        const val DATABASE_VERSION = 1

        fun getInstance(context: Context): RoomDataBaseHelper {
            return Room.databaseBuilder(
                context.applicationContext,
                RoomDataBaseHelper::class.java,
                DATABASE_NAME
            )
            .build()
        }
    }

    abstract fun getCategoryRoomDao(): CategoryRoomDao
    abstract fun getPurchaseRoomDao(): PurchaseRoomDao
    abstract fun getPurchaseListRoomDao(): PurchaseListRoomDao
}