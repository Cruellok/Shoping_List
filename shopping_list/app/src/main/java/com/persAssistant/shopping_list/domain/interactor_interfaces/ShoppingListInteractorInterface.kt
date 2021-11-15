package com.persAssistant.shopping_list.domain.interactor_interfaces

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.enitity.RoomShoppingList
import com.persAssistant.shopping_list.domain.enitities.ShoppingList
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

//abstract class ShoppingListInteractorInterface : ShoppingListRepositoryInterface()
abstract class ShoppingListInteractorInterface {
    abstract fun getChangeSingle(): LiveData<List<RoomShoppingList>>
    abstract fun insert(shoppingList: ShoppingList): Completable
    abstract fun getAll(): Single<LinkedList<ShoppingList>>
    abstract fun getById(id: Long): Maybe<ShoppingList>
    abstract fun update(shoppingList: ShoppingList): Completable
    abstract fun delete(shoppingList: ShoppingList): Completable
}