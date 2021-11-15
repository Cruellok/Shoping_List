package com.persAssistant.shopping_list.domain.interactors

import com.persAssistant.shopping_list.domain.enitities.Purchase
import com.persAssistant.shopping_list.domain.interactor_interfaces.PurchaseInteractorInterface
import com.persAssistant.shopping_list.domain.interactor_repositories.PurchaseRepositoryInterface

class PurchaseInteractor(private val purchaseRepositoryInterface: PurchaseRepositoryInterface):
    PurchaseInteractorInterface() {

    override fun getChangeSingle() {
        purchaseRepositoryInterface.getChangeSingle()
    }

    override fun insert(purchase: Purchase) {
        purchaseRepositoryInterface.insert(purchase)
    }

    override fun getAll() {
        purchaseRepositoryInterface.getAll()
    }

    override fun getAllByListId(id: Long) {
        purchaseRepositoryInterface.getAllByListId(id)
    }

    override fun getAllByCategoryId(id: Long) {
        purchaseRepositoryInterface.getAllByCategoryId(id)
    }

    override fun update(purchase: Purchase) {
        purchaseRepositoryInterface.update(purchase)
    }

    override fun delete(purchase: Purchase) {
        purchaseRepositoryInterface.delete(purchase)
    }
}