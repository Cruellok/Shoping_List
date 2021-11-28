package com.persAssistant.shopping_list.data.database.service

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.ShoppingListRoomDao
import com.persAssistant.shopping_list.data.database.dao.entity.RoomShoppingList
import com.persAssistant.shopping_list.domain.entities.ShoppingList
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.lang.Exception
import java.util.*

class ShoppingListService(private val shoppingListRoomDao: ShoppingListRoomDao) {

    // сигнал об изменении в таблице
    fun getChangeSignal(): LiveData<List<RoomShoppingList>>{
        return shoppingListRoomDao.getChangeSignal()
    }

    // добавления записи в таблицу
    fun insert(shoppingList: ShoppingList): Completable{
        val roomShoppingList = RoomShoppingList(id = shoppingList.id,dateCode = shoppingList.date.time,name = shoppingList.name)

        return Completable.fromAction{
            val result = shoppingListRoomDao.insert(roomShoppingList)
            if (result != -1L) {
                shoppingList.id = result
            }
            else
                throw Exception("Failed to execute insert")
        }
    }

    //запрос всех списков
    fun getAll (): Single<LinkedList<ShoppingList>>{
        return shoppingListRoomDao.getAll()
            .toObservable()
            .flatMapIterable {/*list*/ it }
            .map {ShoppingList(it.id, it.date, it.name)}
            .toList()
            .map {
                val linkedList = LinkedList<ShoppingList>()
                linkedList.addAll(it)
                linkedList
            }
    }

    //запрос одного списка по айди
    fun getById(id: Long): Maybe<ShoppingList>{
        return shoppingListRoomDao.getById(id)
            .map {ShoppingList(id = it.id, date = it.date, name = it.name)}
    }

    //обновление списка
    fun update (shoppingList: ShoppingList): Completable{
        val roomShoppingList = RoomShoppingList(id = shoppingList.id,dateCode = shoppingList.date.time,name = shoppingList.name)
        return Completable.fromAction{
            shoppingListRoomDao.update(roomShoppingList)
        }
    }

    //удаление списка по айди
    fun delete (shoppingList: ShoppingList): Completable{
        val roomShoppingList = RoomShoppingList(id = shoppingList.id,dateCode = shoppingList.date.time,name = shoppingList.name)
        return Completable.fromAction{
            shoppingListRoomDao.delete(roomShoppingList)
        }
    }

}