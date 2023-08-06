package com.test.todolist.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
class AddEditTodoViewModel @Inject constructor(private val repository: TodoRepository, savedStateHandle: SavedStateHandle): ViewModel(){

    var todo by mutableStateOf<Todo?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if (todoId != -1){
            viewModelScope.launch {
                repository.getTodoById(todoId)?.let { todo ->
                    title = todo.title
                    description = todo.description ?: ""
                    this@AddEditTodoViewModel.todo = todo
                }
            }
        }
    }

    fun onEvent(events: AddEditTodoEvents){
        when(events){
            is AddEditTodoEvents.OnTitleChange -> { title = events.title}
            is AddEditTodoEvents.OnDescriptionChange -> { description = events.description}
            is AddEditTodoEvents.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackBar("The title can't be empty"))
                        return@launch
                    }
                    repository.insertTodo(Todo(id = todo?.id, title = title, description = description, isDone = todo?.isDone ?: false))
                    sendUiEvent(UiEvent.PopBackStack)
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