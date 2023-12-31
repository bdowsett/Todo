package com.example.simpletodo.ui.to_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpletodo.data.ToDoRepository
import com.example.simpletodo.data.Todo
import com.example.simpletodo.util.Routes
import com.example.simpletodo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: ToDoRepository): ViewModel() {

    val todos = repository.getTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null

    fun onEvent(event: TodoListEvent){
        when(event) {
            is TodoListEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnUndoDeleteClick -> {
                    deletedTodo?.let{ todo ->
                        viewModelScope.launch {
                            repository.insertTodo(todo)
                        }
                    }
            }

            is TodoListEvent.OnDeleteTodoClick -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar(
                        message = "Todo deleted",
                        action = "Undo"
                    ))
                }
            }

            is TodoListEvent.OnDoneChanged -> {
                viewModelScope.launch { repository.insertTodo(event.todo.copy(
                    isDone = event.isDone
                )) }
            }
        }
    }

    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch { _uiEvent.send(event) }
    }

}