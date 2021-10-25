package com.persAssistant.shopping_list.presentation.purchase

import android.content.Context
import android.content.Intent

class CreatorPurchaseActivity: PurchaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CreatorPurchaseActivity::class.java)
        }
    }

    override fun createViewModel(listId: Long): PurchaseViewModel {
        return CreatorPurchaseViewModel(application, listId)
    }
}
