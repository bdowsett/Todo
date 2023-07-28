package com.example.simpletodo.ui.add_edit_todo

import android.app.UiAutomation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodo.data.ToDoRepository
import com.example.simpletodo.data.Todo
import com.example.simpletodo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: ToDoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    var todo by mutableStateOf<Todo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init{
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if(todoId != -1 ) {
            viewModelScope.launch {
                repository.getTodoById(todoId)?.let{todo ->
                    title = todo.title
                    this@AddEditTodoViewModel.todo = todo

                }
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when(event){
            is AddEditTodoEvent.OnTitleChanged -> {
                title = event.title
            }
            is AddEditTodoEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if(title.isBlank()){
                        sendUiEvent(event = UiEvent.ShowSnackBar(
                            message = "The title cannot be empty"
                        ))

                        return@launch
                    }
                    repository.insertTodo(Todo(title = title, description = description, isDone = todo?.isDone ?: false ))
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }


    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch { _uiEvent.send(event) }
    }

}