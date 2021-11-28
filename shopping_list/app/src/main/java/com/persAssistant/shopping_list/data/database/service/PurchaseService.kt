package com.persAssistant.shopping_list.data.database.service

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.PurchaseRoomDao
import com.persAssistant.shopping_list.data.database.dao.entity.RoomPurchase
import com.persAssistant.shopping_list.domain.entities.Purchase
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.lang.Exception
import java.util.*

class PurchaseService(private val purchaseRoomDao: PurchaseRoomDao){

    // сигнал об изменении в таблице
    fun getChangeSignal(): LiveData<List<RoomPurchase>> {
        return purchaseRoomDao.getChangeSignal()
    }

    // добавления записи в таблицу
    fun insert(purchase: Purchase): Completable {
        val roomPurchase = RoomPurchase(
            id = purchase.id,
            name = purchase.name,
            categoryId = purchase.categoryId,
            listId = purchase.listId,
            price = purchase.price,
            isCompleted = purchase.isCompleted)

        return Completable.fromAction {
            val result = purchaseRoomDao.insert(roomPurchase)
            if (result != -1L)
                purchase.id = result
            else
                throw Exception("Failed to execute insert")
        }
    }

    //запрос одного списка по айди
    fun getById(id: Long): Maybe<Purchase> {
        return purchaseRoomDao.getById(id)
            .map {
                Purchase(it.id, it.name, it.categoryId, it.listId, it.price, it.isCompleted)}
    }

    private fun processDaoPurchases(single: Single<List<RoomPurchase>>): Single<LinkedList<Purchase>>{
        return single.toObservable()
            .flatMapIterable {/*list*/ it}
            .map {Purchase(it.id, it.name, it.categoryId, it.listId, it.price, it.isCompleted)}
            .toList()
            .map {
                val linkedList = LinkedList<Purchase>()
                linkedList.addAll(it)
                linkedList
            }
    }

    //запрос всех списков
    fun getAll(): Single<LinkedList<Purchase>> {
        return processDaoPurchases(purchaseRoomDao.getAll())
    }

    //запрос списка покупок относящегося к определенной покупке по айди
    fun getAllByListId(id: Long): Single<LinkedList<Purchase>> {
        return processDaoPurchases(purchaseRoomDao.getAllByListId(id))
    }

    //запрос категорий относящихся к определенной покупке по айди
    fun getAllByCategoryId(id: Long): Single<LinkedList<Purchase>> {
        return processDaoPurchases(purchaseRoomDao.getAllByCategoryId(id))
    }

    //обновление списка
    fun update(purchase: Purchase): Completable {
        val roomPurchase = RoomPurchase(
            id = purchase.id,
            name = purchase.name,
            categoryId = purchase.categoryId,
            listId = purchase.listId,
            price = purchase.price,
            isCompleted = purchase.isCompleted)
        return Completable.fromAction {
            purchaseRoomDao.update(roomPurchase)
        }
    }

    //удаление списка
    fun delete(purchase: Purchase): Completable {
        val roomPurchase = RoomPurchase(
            id = purchase.id,
            name = purchase.name,
            categoryId = purchase.categoryId,
            listId = purchase.listId,
            price = purchase.price,
            isCompleted = purchase.isCompleted)
        return Completable.fromAction {
            purchaseRoomDao.delete(roomPurchase)
        }
    }

}