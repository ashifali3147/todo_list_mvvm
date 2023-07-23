package com.test.todolist.ui.add_edit_todo

sealed class AddEditTodoEvents{
    data class OnTitleChange(val title: String): AddEditTodoEvents()
    data class OnDescriptionChange(val description: String): AddEditTodoEvents()
    object OnSaveTodoClick: AddEditTodoEvents()
}
