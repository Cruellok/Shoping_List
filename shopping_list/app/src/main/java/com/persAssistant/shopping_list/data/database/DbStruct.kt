package com.persAssistant.shopping_list.data.database

object DbStruct {
    //данные для листа
    object PurchaseListTable {
        const val tableName = "purchase_lists"

        object Cols {
            const val id = "_id"
            const val date = "date"
            const val name = "name_списка_покупок"
        }
    }

    // данные для списка покупок
    object Purchase {
        const val tableName = "purchase"

        object Cols {
            const val id = "_id"
            const val categoryId = "category_id"
            const val name = "name_покупок"
            const val listId = "listId"
            const val price = "price"
            const val isCompleted = "isCompleted"
            const val INVALID_ID = -1L
        }
    }

    //данные для категории
    object Category {
        const val tableName = "categories"

        object Cols {
            const val id = "_id"
            const val name = "name_категории"
        }
    }
}