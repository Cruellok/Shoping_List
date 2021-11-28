package com.persAssistant.shopping_list.domain.interactors

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.entity.RoomShoppingList
import com.persAssistant.shopping_list.domain.entities.ShoppingList
import com.persAssistant.shopping_list.domain.interactor_interfaces.ShoppingListInteractorInterface
import com.persAssistant.shopping_list.domain.interactor_repositories.ShoppingListRepositoryInterface
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

class ShoppingListInteractor(private val shoppingListRepositoryInterface: ShoppingListRepositoryInterface): ShoppingListInteractorInterface() {

    // сигнал об изменении в таблице
    override fun getChangeSignal(): LiveData<List<RoomShoppingList>> {
        return shoppingListRepositoryInterface.getChangeSignal()
    }

    // добавления записи в таблицу
    override fun insert(shoppingList: ShoppingList): Completable {
        return shoppingListRepositoryInterface.insert(shoppingList)
    }

    //запрос всех списков
    override fun getAll(): Single<LinkedList<ShoppingList>> {
        return shoppingListRepositoryInterface.getAll()
    }

    //запрос одного списка по айди
    override fun getById(id: Long): Maybe<ShoppingList> {
        return shoppingListRepositoryInterface.getById(id)
    }

    //обновление списка
    override fun update(shoppingList: ShoppingList): Completable {
        return shoppingListRepositoryInterface.update(shoppingList)
    }

    //удаление списка по айди
    override fun delete(shoppingList: ShoppingList): Completable {
        return shoppingListRepositoryInterface.delete(shoppingList)
    }
}