package com.persAssistant.shopping_list.data.database.enitities


data class Purchase(
        var id:Long? = null,
        var name: String,
        var categoryId: Long,
        var listId: Long,
        var price: Double? = null,
        var isCompleted: Int
)