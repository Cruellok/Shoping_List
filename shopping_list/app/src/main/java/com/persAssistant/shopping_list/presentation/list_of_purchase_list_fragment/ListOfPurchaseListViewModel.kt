//package com.persAssistant.shopping_list.presentation.list_of_purchase_list_fragment
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.MutableLiveData
//import com.persAssistant.shopping_list.data.database.enitities.Category
//import com.persAssistant.shopping_list.data.database.enitities.PurchaseList
//import com.persAssistant.shopping_list.data.database.service.CategoryService
//import com.persAssistant.shopping_list.data.database.service.PurchaseListService
//import com.persAssistant.shopping_list.data.database.service.PurchaseService
//import com.persAssistant.shopping_list.presentation.App
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//import java.util.*
//
//class ListOfPurchaseListViewModel(application: Application): AndroidViewModel(application)  {
//
//    var purchaseListService: PurchaseListService
//    var listPurchase = MutableLiveData<LinkedList<PurchaseList>>()
//    var deletePurchaseListId = MutableLiveData<Long>()
//
//    init {
//        val app = getApplication<App>()
//        purchaseListService = app.purchaseListService
//        getAllPurchaseList()
//    }
//
//    var onChanges = purchaseListService.getChangeSingle()
//
//    fun getAllPurchaseList() {
//        purchaseListService.getAll()
//            .subscribeOn(Schedulers.single())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({/*Есть данные*/
//                listPurchase.value = it
//            }, {/*Ошибка*/ })
//    }
//
//    fun deleteItemCategory(purchaseList: PurchaseList){
//        purchaseListService.delete(purchaseList)
//        .subscribeOn(Schedulers.single())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe({/*Выполнено*/
//            deletePurchaseListId.value = purchaseList.id
//        }, {/*Ошибка*/ })
//    }
//}