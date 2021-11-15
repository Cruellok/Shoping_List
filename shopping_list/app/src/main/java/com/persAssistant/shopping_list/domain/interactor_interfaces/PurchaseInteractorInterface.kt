package com.persAssistant.shopping_list.domain.interactor_interfaces

import com.persAssistant.shopping_list.domain.enitities.Purchase

//abstract class PurchaseInteractorInterface: PurchaseRepositoryInterface()
abstract class PurchaseInteractorInterface {
    abstract fun getChangeSingle()
    abstract fun insert(purchase: Purchase)
    abstract fun getAll()
    abstract fun getAllByListId(id: Long)
    abstract fun getAllByCategoryId(id: Long)
    abstract fun update(purchase: Purchase)
    abstract fun delete(purchase: Purchase)
}