package com.persAssistant.shopping_list.domain.interactors

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.entity.RoomPurchase
import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.domain.interactor_interfaces.CategoryInteractorInterface
import com.persAssistant.shopping_list.domain.interactor_interfaces.FullPurchaseInteractorInterface
import com.persAssistant.shopping_list.domain.interactor_interfaces.PurchaseInteractorInterface
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

class FullPurchaseInteractor (private val purchaseInteractorInterface: PurchaseInteractorInterface, private val categoryInteractorInterface: CategoryInteractorInterface):
    FullPurchaseInteractorInterface() {

    override fun getChangeSingle(): LiveData<List<RoomPurchase>> {
        return purchaseInteractorInterface.getChangeSingle()
    }

    override fun getById(id: Long): Maybe<Pair<Purchase, Category>> {
        return purchaseInteractorInterface.getById(id)
            .flatMap { purchase ->
                categoryInteractorInterface.getById(purchase.categoryId)
                    .map {
                        Pair<Purchase, Category>(purchase, it)
                    }
            }
    }

    override fun getAllByListId(id: Long): Single<LinkedList<Pair<Purchase, Category>>> {
        return processInteractorInterfacePurchases(purchaseInteractorInterface.getAllByListId(id))
    }

    override fun getAllByCategoryId(id: Long): Single<LinkedList<Pair<Purchase, Category>>> {
        return processInteractorInterfacePurchases(purchaseInteractorInterface.getAllByCategoryId(id))
    }

    override fun delete(purchase: Purchase): Completable {
        return purchaseInteractorInterface.delete(purchase)
    }

    private fun processInteractorInterfacePurchases(single: Single<LinkedList<Purchase>>):
            Single<LinkedList<Pair<Purchase, Category>>>{
        return single
            .toObservable()
            .flatMapIterable{it}
            .flatMap{ purchase->
                categoryInteractorInterface.getById(purchase.categoryId)
                    .map { category ->
                        Pair<Purchase, Category>(purchase, category)
                    }.toObservable()
            }
            .toList()
            .map {
                val linkedList = LinkedList<Pair<Purchase, Category>>()
                linkedList.addAll(it)
                linkedList
            }
    }

}