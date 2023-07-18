package com.test.todolist.data.dao

import androidx.room.*
import com.test.todolist.data.entity.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)
    @Delete
    suspend fun deleteTodo(todo: Todo)
    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo?
    @Query("SELECT * FROM todo")
    fun getTodo(): Flow<MutableList<Todo>>
}