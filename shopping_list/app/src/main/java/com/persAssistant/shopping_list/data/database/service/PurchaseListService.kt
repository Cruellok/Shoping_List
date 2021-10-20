package com.persAssistant.shopping_list.data.database.service

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.PurchaseListRoomDao
import com.persAssistant.shopping_list.data.database.dao.enitity.RoomPurchaseList
import com.persAssistant.shopping_list.data.database.enitities.PurchaseList
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.lang.Exception
import java.util.*

class PurchaseListService(private val purchaseListRoomDao: PurchaseListRoomDao) {

    fun getChangeSingle(): LiveData<List<RoomPurchaseList>>{
        return purchaseListRoomDao.getChangeSingle()
    }

    // добавления записи в таблицу
    fun insert(purchaseList: PurchaseList): Completable{
        val roomPurchaseList = RoomPurchaseList(id = purchaseList.id,dateCode = purchaseList.date.time,name = purchaseList.name)

        return Completable.fromAction{
            val result = purchaseListRoomDao.insert(roomPurchaseList)
            if (result != -1L) {
                purchaseList.id = result
            }
            else
                throw Exception("Failed to execute insert")
        }
    }

    //запрос всех списков
    fun getAll (): Single<LinkedList<PurchaseList>>{
        return purchaseListRoomDao.getAll()
            .toObservable()
            .flatMapIterable {/*list*/ it }
            .map {PurchaseList(it.id, it.date, it.name)}
            .toList()
            .map {
                val linkedList = LinkedList<PurchaseList>()
                linkedList.addAll(it)
                linkedList
            }
    }

    //запрос одного списка по айди
    fun getById(id: Long): Maybe<PurchaseList>{
        return purchaseListRoomDao.getById(id)
            .map {PurchaseList(id = it.id, date = it.date, name = it.name)}
    }

    //обновление списка
    fun update (purchaseList: PurchaseList): Completable{
        val roomPurchaseList = RoomPurchaseList(id = purchaseList.id,dateCode = purchaseList.date.time,name = purchaseList.name)
        return Completable.fromAction{
            purchaseListRoomDao.update(roomPurchaseList)
        }
    }

    //удаление списка по айди
    fun delete (purchaseList: PurchaseList): Completable{
        val roomPurchaseList = RoomPurchaseList(id = purchaseList.id,dateCode = purchaseList.date.time,name = purchaseList.name)
        return Completable.fromAction{
            purchaseListRoomDao.delete(roomPurchaseList)
        }
    }

}