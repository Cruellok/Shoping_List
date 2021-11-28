package com.persAssistant.shopping_list.domain.interactors

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.entity.RoomPurchase
import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.domain.interactor_interfaces.PurchaseInteractorInterface
import com.persAssistant.shopping_list.domain.interactor_repositories.PurchaseRepositoryInterface
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

class PurchaseInteractor(private val purchaseRepositoryInterface: PurchaseRepositoryInterface):
    PurchaseInteractorInterface() {

    // сигнал об изменении в таблице
    override fun getChangeSignal(): LiveData<List<RoomPurchase>> {
        return purchaseRepositoryInterface.getChangeSignal()
    }

    // добавления записи в таблицу
    override fun insert(purchase: Purchase): Completable {
        return purchaseRepositoryInterface.insert(purchase)
    }

    //запрос одного списка по айди
    override fun getAll(): Single<LinkedList<Purchase>> {
        return purchaseRepositoryInterface.getAll()
    }

    //запрос всех списков
    override fun getById(id: Long): Maybe<Purchase> {
        return purchaseRepositoryInterface.getById(id)
    }

    //запрос списка покупок относящегося к определенной покупке по айди
    override fun getAllByListId(id: Long): Single<LinkedList<Purchase>> {
        return purchaseRepositoryInterface.getAllByListId(id)
    }

    //запрос категорий относящихся к определенной покупке по айди
    override fun getAllByCategoryId(id: Long): Single<LinkedList<Purchase>> {
        return purchaseRepositoryInterface.getAllByCategoryId(id)
    }

    //обновление списка
    override fun update(purchase: Purchase): Completable {
        return purchaseRepositoryInterface.update(purchase)
    }

    //удаление списка
    override fun delete(purchase: Purchase): Completable {
        return purchaseRepositoryInterface.delete(purchase)
    }
}