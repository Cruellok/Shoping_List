package com.persAssistant.shopping_list.presentation.category

import android.content.Context
import android.content.Intent

class CreatorCategoryActivity : CategoryActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CreatorCategoryActivity::class.java)
        }
    }

    override fun createViewModel(): CategoryViewModel {
        return CreatorCategoryViewModel(application)
    }
}