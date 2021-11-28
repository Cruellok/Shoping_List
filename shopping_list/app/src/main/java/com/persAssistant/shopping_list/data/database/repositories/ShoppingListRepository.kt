package com.persAssistant.shopping_list.data.database.repositories

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.entity.RoomShoppingList
import com.persAssistant.shopping_list.domain.interactor_repositories.ShoppingListRepositoryInterface
import com.persAssistant.shopping_list.data.database.service.ShoppingListService
import com.persAssistant.shopping_list.domain.entities.ShoppingList
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

class ShoppingListRepository(private val shoppingListService: ShoppingListService): ShoppingListRepositoryInterface() {

    // сигнал об изменении в таблице
    override fun getChangeSignal(): LiveData<List<RoomShoppingList>> {
        return shoppingListService.getChangeSignal()
    }

    // добавления записи в таблицу
    override fun insert(shoppingList: ShoppingList): Completable {
        return shoppingListService.insert(shoppingList)
    }

    //запрос всех списков
    override fun getAll(): Single<LinkedList<ShoppingList>> {
        return shoppingListService.getAll()
    }

    //запрос одного списка по айди
    override fun getById(id: Long): Maybe<ShoppingList> {
        return shoppingListService.getById(id)
    }

    //обновление списка
    override fun update(shoppingList: ShoppingList): Completable {
        return shoppingListService.update(shoppingList)
    }

    //удаление списка по айди
    override fun delete(shoppingList: ShoppingList): Completable {
        return shoppingListService.delete(shoppingList)
    }
}