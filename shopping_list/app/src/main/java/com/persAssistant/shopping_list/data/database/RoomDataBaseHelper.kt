package com.persAssistant.shopping_list.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.persAssistant.shopping_list.data.database.dao.CategoryRoomDao
import com.persAssistant.shopping_list.data.database.dao.ShoppingListRoomDao
import com.persAssistant.shopping_list.data.database.dao.PurchaseRoomDao
import com.persAssistant.shopping_list.data.database.dao.entity.RoomCategory
import com.persAssistant.shopping_list.data.database.dao.entity.RoomPurchase
import com.persAssistant.shopping_list.data.database.dao.entity.RoomShoppingList
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Database(
    entities = [RoomCategory::class, RoomShoppingList::class, RoomPurchase::class],
    version = RoomDataBaseHelper.DATABASE_VERSION, exportSchema = false)
abstract class RoomDataBaseHelper : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "database_shopping_list.db"
        const val DATABASE_VERSION = 1

        fun getInstance(context: Context): RoomDataBaseHelper {
            return Room.databaseBuilder(
                context.applicationContext,
                RoomDataBaseHelper::class.java,
                DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        // creating default category
                        val dataBaseHelper = getInstance(context)
                        val categoryDao = dataBaseHelper.getCategoryRoomDao()
                        val defaultRoomCategory = RoomCategory(DbStruct.Category.Cols.DEFAULT_CATEGORIES_COUNT,"Универсальная категория")

                        Completable.fromAction {
                            categoryDao.insert(defaultRoomCategory
                        )}
                            .subscribeOn(Schedulers.single())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                            }, {})
                    }
                })
                .build()
        }
    }


    abstract fun getCategoryRoomDao(): CategoryRoomDao
    abstract fun getPurchaseRoomDao(): PurchaseRoomDao
    abstract fun getShoppingListRoomDao(): ShoppingListRoomDao
}
