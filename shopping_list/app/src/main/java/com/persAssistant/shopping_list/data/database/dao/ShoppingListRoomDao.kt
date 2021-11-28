package com.persAssistant.shopping_list.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.persAssistant.shopping_list.data.database.DbStruct
import com.persAssistant.shopping_list.data.database.dao.entity.RoomShoppingList
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ShoppingListRoomDao {

    //запрос на одно любое изменение
    @Query("SELECT * FROM ${DbStruct.ShoppingListTable.tableName}")
    fun getChangeSignal(): LiveData<List<RoomShoppingList>>

    // добавления записи в таблицу
    @Insert
    fun insert(roomShoppingList: RoomShoppingList): Long

    //запрос всех категорий
    @Query("SELECT * FROM ${DbStruct.ShoppingListTable.tableName}")
    fun getAll(): Single<List<RoomShoppingList>>

    //запрос одного списка по айди
    @Query("SELECT * FROM ${DbStruct.ShoppingListTable.tableName} WHERE ${DbStruct.ShoppingListTable.Cols.id} = :id")
    fun getById(id: Long): Maybe<RoomShoppingList>

    //обновление категории
    @Update
    fun update(roomShoppingList: RoomShoppingList): Int

    //удаление категории
    @Delete
    fun delete(roomShoppingList: RoomShoppingList): Int

}
