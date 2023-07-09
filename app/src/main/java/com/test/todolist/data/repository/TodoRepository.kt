package com.test.todolist.data.repository

import com.test.todolist.data.entity.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insertTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    suspend fun getTodoById(id: Int): Todo?
    fun getTodo(): Flow<List<Todo>>
}