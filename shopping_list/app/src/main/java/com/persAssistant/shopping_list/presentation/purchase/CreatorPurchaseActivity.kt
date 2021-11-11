package com.persAssistant.shopping_list.presentation.purchase

import android.content.Context
import android.content.Intent
import java.lang.Exception

class CreatorPurchaseActivity: PurchaseActivity() {

    companion object {
        fun getIntent(context: Context, listId: Long): Intent {
            val intent = Intent(context, CreatorPurchaseActivity::class.java)
            intent.putExtra(KEY_SHOPPINGLIST_ID,listId)
            return intent
        }
    }

    override fun createViewModel(): PurchaseViewModel {
        val listId = intent.getLongExtra(KEY_SHOPPINGLIST_ID,-1L)
        if (listId == -1L){
            throw Exception("Ошибка в PurchaseActivity отсутствует listId")
        }
        return CreatorPurchaseViewModel(application,listId)
    }
}
