package com.persAssistant.shopping_list.data.database.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.*
import androidx.room.PrimaryKey
import com.persAssistant.shopping_list.data.database.DbStruct

@Entity(tableName = DbStruct.Purchase.tableName,
        foreignKeys = [ForeignKey(entity = RoomCategory::class,
                parentColumns = arrayOf(DbStruct.Category.Cols.id),
                childColumns = arrayOf(DbStruct.Purchase.Cols.categoryId),
                onDelete = SET_DEFAULT),

                ForeignKey(entity = RoomShoppingList::class,
                parentColumns = arrayOf(DbStruct.ShoppingListTable.Cols.id),
                childColumns = arrayOf(DbStruct.Purchase.Cols.listId),
                onDelete = CASCADE)])

data class RoomPurchase(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DbStruct.Purchase.Cols.id)
        var id:Long? = null,

        @ColumnInfo(name = DbStruct.Purchase.Cols.name)
        var name: String,

        @ColumnInfo(name = DbStruct.Purchase.Cols.categoryId, defaultValue = DbStruct.Category.Cols.DEFAULT_CATEGORIES_COUNT.toString())
        var categoryId: Long,

        @ColumnInfo(name = DbStruct.Purchase.Cols.listId)
        var listId: Long,

        @ColumnInfo(name = DbStruct.Purchase.Cols.price)
        var price: Double? = null,

        @ColumnInfo(name = DbStruct.Purchase.Cols.isCompleted)
        var isCompleted: Int

)