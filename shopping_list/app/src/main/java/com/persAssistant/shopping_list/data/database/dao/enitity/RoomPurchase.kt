package com.persAssistant.shopping_list.data.database.dao.enitity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.persAssistant.shopping_list.data.database.DbStruct

@Entity(tableName = DbStruct.Purchase.tableName)
data class RoomPurchase(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DbStruct.Purchase.Cols.id)
        var id:Long? = null,

        @ColumnInfo(name = DbStruct.Purchase.Cols.name)
        var name: String,

        @ColumnInfo(name = DbStruct.Purchase.Cols.categoryId)
        var categoryId: Long,

        @ColumnInfo(name = DbStruct.Purchase.Cols.listId)
        var listId: Long,

        @ColumnInfo(name = DbStruct.Purchase.Cols.price)
        var price: Double? = null,

        @ColumnInfo(name = DbStruct.Purchase.Cols.isCompleted)
        var isCompleted: Int

)