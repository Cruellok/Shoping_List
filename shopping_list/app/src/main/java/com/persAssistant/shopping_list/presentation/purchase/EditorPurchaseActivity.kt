package com.persAssistant.shopping_list.presentation.purchase

import android.content.Context
import android.content.Intent
import java.lang.Exception

class EditorPurchaseActivity: PurchaseActivity() {

    companion object {
        fun getIntent(context: Context, listId: Long): Intent {
            val intent = Intent(context, EditorPurchaseActivity::class.java)
            intent.putExtra(KEY_PURCHASELIST_ID, listId)
            return intent
        }
    }

    override fun createViewModel(listId: Long): PurchaseViewModel {
        val id = intent.getLongExtra(KEY_PURCHASELIST_ID,-1L)
        if(id == -1L)
            throw Exception("Ошибка в EditorPurchaseActivity.getIntent отсутствует Id")

        return EditorPurchaseViewModel(application, id, listId)
    }
}
