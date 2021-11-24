package com.persAssistant.shopping_list.domain.interactor_interfaces

import com.persAssistant.shopping_list.domain.entities.FullPurchase
import com.persAssistant.shopping_list.domain.entities.Purchase
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

abstract class FullPurchaseInteractorInterface {
    abstract fun getById(id: Long): Maybe<FullPurchase>
    abstract fun getAllByListId(id: Long): Single<LinkedList<FullPurchase>>
    abstract fun getAllByCategoryId(id: Long): Single<LinkedList<FullPurchase>>
}