package com.persAssistant.shopping_list.data.database

object DbStruct {
    //данные для листа
    object ShoppingListTable {
        const val tableName = "shopping_lists"

        object Cols {
            const val id = "_id"
            const val date = "date"
            const val name = "name_списка_покупок"
            const val INVALID_ID = -1000L
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
        }
    }

    //данные для категории
    object Category {
        const val tableName = "categories"

        object Cols {
            const val id = "_id"
            const val name = "name_категории"
            const val DEFAULT_CATEGORIES_COUNT = -1000L
        }
    }
}