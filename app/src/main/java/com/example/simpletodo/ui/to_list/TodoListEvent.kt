package com.example.simpletodo.ui.to_list

import com.example.simpletodo.data.Todo

sealed class TodoListEvent(){

    data class OnDeleteTodoClick(val todo: Todo): TodoListEvent()
    data class OnDoneChanged(val todo: Todo, val isDone: Boolean): TodoListEvent()
    object OnUndoDeleteClick: TodoListEvent()
    data class OnTodoClick(val todo: Todo): TodoListEvent()
    object OnAddTodoClick: TodoListEvent()


}