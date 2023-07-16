package com.test.todolist.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.todolist.data.repository.TodoRepository

@Suppress("UNCHECKED_CAST")
class TodoListViewModelFactory(private val repository: TodoRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoListViewModel(repository) as T
    }
}