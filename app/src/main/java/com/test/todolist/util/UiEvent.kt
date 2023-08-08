package com.test.todolist.util

sealed class UiEvent{
    object PopBackStack: UiEvent()
    object UpdateUI: UiEvent()
    data class Navigate(val route: String, val todoId: Int? = null): UiEvent()
    data class ShowSnackBar(val message: String, val action: String? = null): UiEvent()
}
