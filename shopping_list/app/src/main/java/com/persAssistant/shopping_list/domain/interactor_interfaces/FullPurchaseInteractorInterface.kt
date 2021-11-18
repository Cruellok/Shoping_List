package com.persAssistant.shopping_list.domain.interactor_interfaces

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.entity.RoomPurchase
import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.domain.entities.Purchase
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

abstract class FullPurchaseInteractorInterface {
    abstract fun getChangeSingle(): LiveData<List<RoomPurchase>>
    abstract fun getById(id: Long): Maybe<Pair<Purchase, Category>>
    abstract fun getAllByListId(id: Long): Single<LinkedList<Pair<Purchase, Category>>>
    abstract fun getAllByCategoryId(id: Long): Single<LinkedList<Pair<Purchase, Category>>>
    abstract fun delete(purchase: Purchase): Completable
}