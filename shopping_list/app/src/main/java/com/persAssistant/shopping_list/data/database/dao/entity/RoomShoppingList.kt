package com.persAssistant.shopping_list.data.database.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.persAssistant.shopping_list.data.database.DbStruct
import java.util.*

@Entity(tableName = DbStruct.ShoppingListTable.tableName)
data class RoomShoppingList(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbStruct.ShoppingListTable.Cols.id)
    var id: Long? = null,

    @ColumnInfo(name = DbStruct.ShoppingListTable.Cols.name)
    var name: String,

    @ColumnInfo(name = DbStruct.ShoppingListTable.Cols.date)
    var dateCode: Long
){
    var date: Date
    set(value) {
        dateCode = value.time
    }
    get() = Date(dateCode)
}

