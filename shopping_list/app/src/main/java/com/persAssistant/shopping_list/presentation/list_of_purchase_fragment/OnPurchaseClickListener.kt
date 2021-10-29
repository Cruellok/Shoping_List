package com.persAssistant.shopping_list.presentation.list_of_purchase_fragment

import com.persAssistant.shopping_list.data.database.enitities.Purchase

interface OnPurchaseClickListener {
    fun clickedPurchaseItem(purchase: Purchase)
    fun deleteItem(purchase: Purchase)
    fun editItem(purchase: Purchase)
    fun clickedMenuItem(purchase: Purchase)
}