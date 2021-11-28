package com.persAssistant.shopping_list.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.persAssistant.shopping_list.data.database.DbStruct
import com.persAssistant.shopping_list.data.database.dao.entity.RoomPurchase
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface PurchaseRoomDao {

    //запрос на одно любое изменение
    @Query("SELECT * FROM ${DbStruct.Purchase.tableName}")
    fun getChangeSignal(): LiveData<List<RoomPurchase>>

    // добавления записи в таблицу
    @Insert
    fun insert(roomPurchase: RoomPurchase): Long

    //запрос всех категорий
    @Query("SELECT * FROM ${DbStruct.Purchase.tableName}")
    fun getAll(): Single<List<RoomPurchase>>

    //запрос одного списка по айди
    @Query("SELECT * FROM ${DbStruct.Purchase.tableName} WHERE ${DbStruct.Purchase.Cols.id} = :id")
    fun getById(id: Long): Maybe<RoomPurchase>

    //запрос списка по List id
    @Query("SELECT * FROM ${DbStruct.Purchase.tableName} WHERE ${DbStruct.Purchase.Cols.listId} = :id")
    fun getAllByListId(id: Long): Single<List<RoomPurchase>>

    //запрос списка по Category id
    @Query("SELECT * FROM ${DbStruct.Purchase.tableName} WHERE ${DbStruct.Purchase.Cols.categoryId} = :id")
    fun getAllByCategoryId(id: Long): Single<List<RoomPurchase>>

    //обновление категории
    @Update
    fun update(roomPurchase: RoomPurchase): Int

    //удаление категории
    @Delete
    fun delete(roomPurchase: RoomPurchase): Int

}

