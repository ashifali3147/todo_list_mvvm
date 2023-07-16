package com.test.todolist.ui.todo_list

import com.test.todolist.data.entity.Todo

sealed class TodoListEvents{
    data class DeleteTodo(val todo: Todo): TodoListEvents()
    data class OnDoneChange(val todo: Todo, val isDone: Boolean): TodoListEvents()
    object OnUndoDeleteClick: TodoListEvents()
    data class OnTodoClick(val todo: Todo): TodoListEvents()
    object OnAddTodoClick: TodoListEvents()
}
