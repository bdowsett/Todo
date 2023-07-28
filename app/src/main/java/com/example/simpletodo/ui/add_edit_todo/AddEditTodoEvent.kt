package com.example.simpletodo.ui.add_edit_todo

sealed class AddEditTodoEvent{
    data class OnTitleChanged(val title: String): AddEditTodoEvent()
    data class OnDescriptionChange(val description: String): AddEditTodoEvent()
    object OnSaveTodoClick : AddEditTodoEvent()

}