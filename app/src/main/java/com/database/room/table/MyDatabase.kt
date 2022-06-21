package com.database.room.table

import android.content.Context
import androidx.room.*
import com.database.room.dao.CategoryDao
import com.database.room.entity.Category


@Database(entities = [Category::class], version = 1)
//@TypeConverters(Converters::class)
abstract class MyDatabase:RoomDatabase() {
    //.. take object from Data access objects
    abstract fun todoDao():CategoryDao
    //... to set table static *
//    .... to use singleton pattern this is (if the database is existed in memory return data else create database and return data )
//    to use it in kotlin make companion object and make function return database (getInsertion)
    companion object{
        //... make table name in dataBase
        private var myDatabase:MyDatabase?=null
        const val DATABASE_NAME="todo-Database"
        //... make insertion
        fun getInsertion(context: Context):MyDatabase{
            //...take object from database to check about Existing data (used singleton pattern)
            when (myDatabase) {
                null -> {
                    myDatabase = Room.databaseBuilder(context,MyDatabase::class.java,DATABASE_NAME)
                        //... make stack about update (version) database
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build()
                }
            }
            return myDatabase!!
        }
    }
}