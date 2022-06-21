package com.database.room.dao

import androidx.room.*
import com.database.room.entity.Category
import java.util.*

@Dao
interface CategoryDao {
    //..make insertion
    @Insert
    fun arabicLanguageAdded(todo:Category)
    @Insert
    fun englishLanguageAdded(todo:Category)
    @Update
    fun updateTodo(todo: Category)
    @Delete
    fun deleteTodo(todo: Category)
    @Query("select * from Category")
    fun getTodos():List<Category>

}