package com.example.simpletodo.ui.to_list

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpletodo.util.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()) {

    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
                    }
                }

                is UiEvent.Navigate -> {
                    onNavigate
                }

                else -> Unit
            }


        }
    }
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.onEvent(TodoListEvent.OnAddTodoClick) }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add")
    }
    }) {
        LazyColumn(){
            items(todos.value){todo ->
                TodoItem(todo = todo, onEvent = viewModel::onEvent)

            }
        }
    }


}
    
