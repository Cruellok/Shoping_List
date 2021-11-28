package com.persAssistant.shopping_list.presentation.shopping_list

import android.content.Context
import android.content.Intent
import com.persAssistant.shopping_list.presentation.App
import java.lang.Exception

class EditorShoppingListActivity : ShoppingListActivity() {

    companion object {
        private const val SHOPPING_LIST_KEY = "SHOPPINGLIST_ID"
        fun getIntent(context: Context, shoppingListId: Long): Intent {
            val intent = Intent(context, EditorShoppingListActivity::class.java)
            intent.putExtra( SHOPPING_LIST_KEY, shoppingListId)
            return intent
        }
    }

    override fun createViewModel(): ShoppingListViewModel {
        val app = applicationContext as App
        val shoppingListId = intent.getLongExtra(SHOPPING_LIST_KEY,-1L)
        if(shoppingListId == -1L)
            throw Exception("Ошибка в EditorShoppingListActivity.getIntent отсутствует Id ")

        val viewModel = app.appComponent.getEditorShoppingListViewModel()
        viewModel.init(shoppingListId)
        return viewModel
    }
}