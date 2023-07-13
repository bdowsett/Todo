package com.example.simpletodo.data

import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(private val dao: TodoDao): ToDoRepository {

    override suspend fun insertTodo(todo: Todo) {
      dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo? {
       return dao.getTodoById(id)
    }

    override fun getTodos(): Flow<List<Todo>> {
        return dao.getTodos()
    }
}