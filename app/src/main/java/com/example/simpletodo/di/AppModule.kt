package com.example.simpletodo.di

import android.app.Application
import androidx.room.Room
import com.example.simpletodo.data.ToDoRepository
import com.example.simpletodo.data.ToDoRepositoryImpl
import com.example.simpletodo.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTodoDatabase(app:Application): TodoDatabase{
        return Room.databaseBuilder(
            app, TodoDatabase::class.java, "todo_db").build()
    }

    @Provides
    @Singleton
    fun providesToDoRepository(db: TodoDatabase): ToDoRepository {
        return ToDoRepositoryImpl(db.dao)
    }

}