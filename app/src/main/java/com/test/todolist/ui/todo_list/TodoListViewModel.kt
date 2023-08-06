package com.test.todolist.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.todolist.data.entity.Todo
import com.test.todolist.data.repository.TodoRepository
import com.test.todolist.util.Routes
import com.test.todolist.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val repository: TodoRepository): ViewModel() {

    val todo = repository.getTodo()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null

    fun onEvent(events: TodoListEvents){
        when(events){
            is TodoListEvents.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(events.todo.id.toString()))
            }
            is TodoListEvents.DeleteTodo -> {
                viewModelScope.launch {
                    deletedTodo = events.todo
                    repository.deleteTodo(events.todo)
                    sendUiEvent(UiEvent.ShowSnackBar("Todo deleted!", "Undo"))
                }
            }
            is TodoListEvents.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(events.todo.copy(isDone = events.isDone))
                }
            }
            is TodoListEvents.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvents.OnUndoDeleteClick -> {
                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
                }
            }
        }
    }

    private fun sendUiEvent(events: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(events)
        }
    }
}