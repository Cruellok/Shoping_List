package com.persAssistant.shopping_list.presentation.category

import android.content.Context
import android.content.Intent
import com.persAssistant.shopping_list.presentation.App
import java.lang.Exception

class EditorCategoryActivity : CategoryActivity() {

    companion object {
        private const val KEY_CATEGORY = "CATEGORY_ID"
        fun getIntent(context: Context, categoryId: Long): Intent {
            val intent = Intent(context, EditorCategoryActivity::class.java)
            intent.putExtra( KEY_CATEGORY, categoryId)
            return intent
        }
    }

    override fun createViewModel(): CategoryViewModel {
        val app = applicationContext as App
        val id = intent.getLongExtra(KEY_CATEGORY,-1L)
        if(id == -1L)
            throw Exception("Ошибка в EditorCategoryActivity.getIntent отсутствует Id")
//        return EditorCategoryViewModel(app.appComponent.getCategoryInteractor(),id)
        val viewModel = app.appComponent.getEditorCategoryViewModel()
        viewModel.init(id)
        return viewModel
    }
}