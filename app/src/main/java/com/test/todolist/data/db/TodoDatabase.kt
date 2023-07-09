package com.test.todolist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.todolist.data.entity.Todo
import com.test.todolist.data.dao.TodoDao

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract val dao: TodoDao
}