package com.persAssistant.shopping_list.presentation.category

import android.content.Context
import android.content.Intent
import com.persAssistant.shopping_list.presentation.App

class CreatorCategoryActivity : CategoryActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CreatorCategoryActivity::class.java)
        }
    }

    override fun createViewModel(): CategoryViewModel {
        val app = applicationContext as App
        return CreatorCategoryViewModel(app.appComponent.getCategoryInteractor())
    }
}