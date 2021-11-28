package com.persAssistant.shopping_list.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.persAssistant.shopping_list.data.database.DbStruct
import com.persAssistant.shopping_list.data.database.dao.entity.RoomCategory
import io.reactivex.Maybe
import io.reactivex.Single


@Dao
interface CategoryRoomDao {

    //запрос на одно любое изменение
    @Query("SELECT * FROM ${DbStruct.Category.tableName}")
    fun getChangeSignal(): LiveData<List<RoomCategory>>

    // добавления записи в таблицу
    @Insert
    fun insert(roomCategory: RoomCategory): Long

    //запрос всех категорий
    @Query("SELECT * FROM ${DbStruct.Category.tableName}")
    fun getAll(): Single<List<RoomCategory>>

    //запрос одного списка по айди
    @Query("SELECT * FROM ${DbStruct.Category.tableName} WHERE ${DbStruct.Category.Cols.id} = :id")
    fun getById(id: Long): Maybe<RoomCategory>

    //обновление категории
    @Update
    fun update(roomCategory: RoomCategory): Int

    //удаление категории
    @Delete
    fun delete(roomCategory: RoomCategory): Int
}

