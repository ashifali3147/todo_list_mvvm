package com.test.todolist.ui.add_edit_todo

sealed class AddEditTodoEvents{
    sealed class OnTitleChange(val title: String): AddEditTodoEvents()
    sealed class OnDescriptionChange(val description: String): AddEditTodoEvents()
    object OnSaveTodoClick: AddEditTodoEvents()
}
