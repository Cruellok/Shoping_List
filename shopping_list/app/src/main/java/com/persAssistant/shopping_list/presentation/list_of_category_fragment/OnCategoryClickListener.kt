package com.persAssistant.shopping_list.presentation.list_of_category_fragment

import com.persAssistant.shopping_list.data.database.enitities.Category

interface OnCategoryClickListener {
    fun categoryItemClicked(category: Category)
    fun deleteItem(category: Category)
    fun editItem(category: Category)
}