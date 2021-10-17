package com.persAssistant.shopping_list.presentation.recycleVIew

import com.persAssistant.shopping_list.data.database.enitities.PurchaseList

interface OnPurchaseListClickListener {
    fun purchaseListItemClicked(purchaseList: PurchaseList)
    fun deleteItem(purchaseList: PurchaseList)
    fun editItem(purchaseList: PurchaseList)


}