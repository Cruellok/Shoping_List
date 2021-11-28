package com.persAssistant.shopping_list.data.database.repositories

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.entity.RoomPurchase
import com.persAssistant.shopping_list.domain.interactor_repositories.PurchaseRepositoryInterface
import com.persAssistant.shopping_list.data.database.service.PurchaseService
import com.persAssistant.shopping_list.domain.entities.Purchase
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

class PurchaseRepository(private val purchaseService: PurchaseService): PurchaseRepositoryInterface() {

    // сигнал об изменении в таблице
    override fun getChangeSignal(): LiveData<List<RoomPurchase>> {
        return purchaseService.getChangeSignal()
    }

    // добавления записи в таблицу
    override fun insert(purchase: Purchase): Completable {
        return purchaseService.insert(purchase)
    }

    //запрос одного списка по айди
    override fun getAll(): Single<LinkedList<Purchase>> {
        return purchaseService.getAll()
    }

    //запрос всех списков
    override fun getById(id: Long): Maybe<Purchase> {
        return purchaseService.getById(id)
    }

    //запрос списка покупок относящегося к определенной покупке по айди
    override fun getAllByListId(id: Long): Single<LinkedList<Purchase>> {
        return purchaseService.getAllByListId(id)
    }

    //запрос категорий относящихся к определенной покупке по айди
    override fun getAllByCategoryId(id: Long): Single<LinkedList<Purchase>> {
        return purchaseService.getAllByCategoryId(id)
    }

    //обновление списка
    override fun update(purchase: Purchase): Completable {
        return purchaseService.update(purchase)
    }

    //удаление списка
    override fun delete(purchase: Purchase): Completable {
        return purchaseService.delete(purchase)
    }
}