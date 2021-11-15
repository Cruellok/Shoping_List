package com.persAssistant.shopping_list.domain.enitities


data class Purchase(
        var id:Long? = null,
        var name: String,
        var categoryId: Long,
        var listId: Long,
        var price: Double? = null,
//        var quantity: Double,
//        var unit: String? = null,
        var isCompleted: Int
)