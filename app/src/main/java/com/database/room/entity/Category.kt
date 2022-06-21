package com.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Category(
    //...to make insert data in sqlite
    @PrimaryKey(autoGenerate = true)
    //..to make data in column in Sqlite
    @ColumnInfo
    var arabicLanguage: Boolean?=false,
    @ColumnInfo
    var englishLanguage:Boolean?=false,

): Serializable{
}

