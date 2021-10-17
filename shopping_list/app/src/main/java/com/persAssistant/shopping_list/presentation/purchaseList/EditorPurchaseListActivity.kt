package com.persAssistant.shopping_list.presentation.purchaseList

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import java.lang.Exception
import java.util.*

class EditorPurchaseListActivity : PurchaseListActivity() {

    companion object {
        private const val KEY = "PURCHASELIST_ID"
        fun getIntent(context: Context, purchaseListId: Long): Intent {
            val intent = Intent(context, EditorPurchaseListActivity::class.java)
            intent.putExtra( KEY, purchaseListId)
            return intent
        }
    }

    override fun createViewModel(): PurchaseListViewModel {
        val id = intent.getLongExtra(KEY,-1L)
        if(id == -1L)
            throw Exception("Ошибка в EditorPurchaseListActivity.getIntent отсутствует Id ")
        return EditorPurchaseListViewModel(application,id)
    }
}