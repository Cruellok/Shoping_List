package com.persAssistant.shopping_list.data.database.repositories

import com.persAssistant.shopping_list.domain.interactor_repositories.PurchaseRepositoryInterface
import com.persAssistant.shopping_list.data.database.service.PurchaseService
import com.persAssistant.shopping_list.domain.enitities.Purchase

class PurchaseRepository(private val purchaseService: PurchaseService): PurchaseRepositoryInterface() {

    override fun getChangeSingle() {
        purchaseService.getChangeSingle()
    }

    override fun insert(purchase: Purchase) {
        purchaseService.insert(purchase)
    }

    override fun getAll() {
        purchaseService.getAll()
    }

    override fun getAllByListId(id: Long) {
        purchaseService.getAllByListId(id)
    }

    override fun getAllByCategoryId(id: Long) {
        purchaseService.getAllByCategoryId(id)
    }

    override fun update(purchase: Purchase) {
        purchaseService.update(purchase)
    }

    override fun delete(purchase: Purchase) {
        purchaseService.delete(purchase)
    }
}