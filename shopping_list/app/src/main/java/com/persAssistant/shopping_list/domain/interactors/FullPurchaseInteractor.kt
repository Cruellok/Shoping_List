package com.persAssistant.shopping_list.domain.interactors

import com.persAssistant.shopping_list.domain.entities.FullPurchase
import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.domain.interactor_interfaces.CategoryInteractorInterface
import com.persAssistant.shopping_list.domain.interactor_interfaces.FullPurchaseInteractorInterface
import com.persAssistant.shopping_list.domain.interactor_interfaces.PurchaseInteractorInterface
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*
import javax.inject.Inject


class FullPurchaseInteractor @Inject constructor(private val purchaseInteractorInterface: PurchaseInteractorInterface,
                              private val categoryInteractorInterface: CategoryInteractorInterface): FullPurchaseInteractorInterface() {

    override fun getById(id: Long): Maybe<FullPurchase> {
        return purchaseInteractorInterface.getById(id)
            .flatMap { purchase ->
                categoryInteractorInterface.getById(purchase.categoryId)
                    .map {
                        FullPurchase(purchase, it)
                    }
            }
    }

    override fun getAllByListId(id: Long): Single<LinkedList<FullPurchase>> {
        return processPurchases(purchaseInteractorInterface.getAllByListId(id))
    }

    override fun getAllByCategoryId(id: Long): Single<LinkedList<FullPurchase>> {
        return processPurchases(purchaseInteractorInterface.getAllByCategoryId(id))
    }

    private fun processPurchases(single: Single<LinkedList<Purchase>>): Single<LinkedList<FullPurchase>>{
        return single
            .toObservable()
            .flatMapIterable{it}
            .flatMap{ purchase->
                categoryInteractorInterface.getById(purchase.categoryId)
                    .map {
                        FullPurchase(purchase, it)
                    }
                .toObservable()
            }
            .toList()
            .map {
                val linkedList = LinkedList<FullPurchase>()
                linkedList.addAll(it)
                linkedList
            }
    }
}