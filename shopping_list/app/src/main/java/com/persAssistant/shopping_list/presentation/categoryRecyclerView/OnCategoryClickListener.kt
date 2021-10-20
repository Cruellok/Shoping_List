package com.persAssistant.shopping_list.presentation.categoryRecyclerView

import com.persAssistant.shopping_list.data.database.enitities.Category

interface OnCategoryClickListener {
    fun categoryItemClicked(category: Category)
    fun deleteItem(category: Category)
}