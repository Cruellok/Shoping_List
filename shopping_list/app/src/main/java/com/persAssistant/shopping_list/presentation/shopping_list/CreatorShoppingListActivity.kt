package com.persAssistant.shopping_list.presentation.shopping_list

import android.content.Context
import android.content.Intent
import com.persAssistant.shopping_list.presentation.App

class CreatorShoppingListActivity : ShoppingListActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CreatorShoppingListActivity::class.java)
        }
    }

    override fun createViewModel(): ShoppingListViewModel {
        val app = applicationContext as App
        return app.appComponent.getCreatorShoppingListViewModel()
    }
}