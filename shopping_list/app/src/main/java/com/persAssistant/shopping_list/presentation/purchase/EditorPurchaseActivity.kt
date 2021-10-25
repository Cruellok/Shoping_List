package com.persAssistant.shopping_list.presentation.purchase

import android.content.Context
import android.content.Intent
import java.lang.Exception

class EditorPurchaseActivity: PurchaseActivity() {

    companion object {
        fun getIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, EditorPurchaseActivity::class.java)
            intent.putExtra(KEY_PURCHASE_ID, id)
            return intent
        }
    }

    override fun createViewModel(): PurchaseViewModel {
        val id = intent.getLongExtra(KEY_PURCHASE_ID,-1L)
        if(id == -1L)
            throw Exception("Ошибка в EditorPurchaseActivity.getIntent отсутствует Id")

        return EditorPurchaseViewModel(application, id)
    }
}
