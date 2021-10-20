package com.persAssistant.shopping_list.presentation.purchaseList

import android.content.Context
import android.content.Intent

class CreatorPurchaseListActivity : PurchaseListActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CreatorPurchaseListActivity::class.java)
        }
    }

    override fun createViewModel(): PurchaseListViewModel {
        return CreatorPurchaseListViewModel(application)
    }
}