package com.persAssistant.shopping_list.data.database.service

import com.persAssistant.shopping_list.data.database.dao.PurchaseRoomDao
import com.persAssistant.shopping_list.data.database.dao.enitity.RoomPurchase
import com.persAssistant.shopping_list.data.database.enitities.Purchase
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.lang.Exception

class PurchaseService(private val purchaseRoomDao: PurchaseRoomDao) {

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
            if (result != -1L) {
                purchase.id = result
            }
            else
                throw Exception("Failed to execute insert")
        }
    }

    //запрос всех списков
    fun getAll(): Single<List<Purchase>> {
        return purchaseRoomDao.getAll()
            .toObservable()
            .flatMapIterable {/*list*/ it }
            .map { Purchase(it.id, it.name, it.categoryId, it.listId, it.price, it.isCompleted) }
            .toList()
    }

    //запрос одного списка по айди
    fun getById(id: Long): Maybe<Purchase> {
        return purchaseRoomDao.getById(id)
            .map { Purchase(it.id, it.name, it.categoryId, it.listId, it.price, it.isCompleted)}
    }

    //запрос списка покупок относящегося к определенному по айди
    fun getAllByListId(id: Long): Single<List<Purchase>> {
        return purchaseRoomDao.getAllByListId(id)
            .toObservable()
            .flatMapIterable {/*list*/ it }
            .map { Purchase(it.id, it.name, it.categoryId, it.listId, it.price, it.isCompleted) }
            .toList()
    }

    //обновление списка по id
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

    //удаление списка по айди
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