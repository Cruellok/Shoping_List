package com.persAssistant.shopping_list.data.database.dao.enitity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.persAssistant.shopping_list.data.database.DbStruct
import java.util.*

@Entity(tableName = DbStruct.PurchaseListTable.tableName)
data class RoomPurchaseList(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbStruct.PurchaseListTable.Cols.id)
    var id: Long? = null,

    @ColumnInfo(name = DbStruct.PurchaseListTable.Cols.name)
    var name: String,

    @ColumnInfo(name = DbStruct.PurchaseListTable.Cols.date)
    var dateCode: Long
) {
    var date: Date
    set(value) {
        dateCode = value.time
    }
    get() = Date(dateCode)
}

