package com.persAssistant.shopping_list.presentation.shopping_list

import android.content.Context
import android.content.Intent
import com.persAssistant.shopping_list.presentation.App
import java.lang.Exception

class EditorShoppingListActivity : ShoppingListActivity() {

    companion object {
        private const val KEY = "SHOPPINGLIST_ID"
        fun getIntent(context: Context, shoppingListId: Long): Intent {
            val intent = Intent(context, EditorShoppingListActivity::class.java)
            intent.putExtra( KEY, shoppingListId)
            return intent
        }
    }

    override fun createViewModel(): ShoppingListViewModel {
        val app = applicationContext as App
        val id = intent.getLongExtra(KEY,-1L)
        if(id == -1L)
            throw Exception("Ошибка в EditorShoppingListActivity.getIntent отсутствует Id ")
        return EditorShoppingListViewModel(app.appComponent.getShoppingListInteractor(),id)
    }
}