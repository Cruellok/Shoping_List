package com.persAssistant.shopping_list.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.persAssistant.shopping_list.data.database.DbStruct
import com.persAssistant.shopping_list.data.database.dao.enitity.RoomPurchaseList
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface PurchaseListRoomDao {

    //запрос всех категорий
    @Query("SELECT * FROM ${DbStruct.PurchaseListTable.tableName}")
    fun getChangeManager(): LiveData<List<RoomPurchaseList>>

    // добавления записи в таблицу
    @Insert
    fun insert(roomPurchaseList: RoomPurchaseList): Long

    //запрос всех категорий
    @Query("SELECT * FROM ${DbStruct.PurchaseListTable.tableName}")
    fun getAll(): Single<List<RoomPurchaseList>>

    //запрос одного списка по айди
    @Query("SELECT * FROM ${DbStruct.PurchaseListTable.tableName} WHERE ${DbStruct.PurchaseListTable.Cols.id} = :id")
    fun getById(id: Long): Maybe<RoomPurchaseList>

    //обновление категории
    @Update
    fun update(roomPurchaseList: RoomPurchaseList): Int

    //удаление категории
    @Delete
    fun delete(roomPurchaseList: RoomPurchaseList): Int

}
