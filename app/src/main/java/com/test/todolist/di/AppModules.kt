package com.test.todolist.di

import android.app.Application
import androidx.room.Room
import com.test.todolist.data.db.TodoDatabase
import com.test.todolist.data.repository.TodoRepository
import com.test.todolist.data.repository.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {
    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase{
        return Room.databaseBuilder(app, TodoDatabase::class.java, "todo_db").build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository{
        return TodoRepositoryImpl(db.dao)
    }
}