package com.persAssistant.shopping_list.data.database.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.persAssistant.shopping_list.data.database.DbStruct

@Entity(tableName = DbStruct.Category.tableName)
data class RoomCategory(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbStruct.Category.Cols.id)
    var id:Long? = null,

    @ColumnInfo(name = DbStruct.Category.Cols.name)
    var name: String
)
