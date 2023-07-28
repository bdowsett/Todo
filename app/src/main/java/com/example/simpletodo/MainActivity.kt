package com.example.simpletodo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simpletodo.ui.add_edit_todo.AddEditTodoScreen
import com.example.simpletodo.ui.theme.SimpleToDoTheme
import com.example.simpletodo.ui.to_list.ToDoListScreen
import com.example.simpletodo.util.Routes
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleToDoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.TODO_LIST) {
                    composable(Routes.TODO_LIST) {
                        ToDoListScreen(onNavigate = {
                            navController.navigate(it.route)
                        }

                        )
                    }
                    composable(route = Routes.ADD_EDIT_TODO + "?todoId={todoId",
                    arguments = listOf(
                        navArgument(name = "todoId"){
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )
                    ){
                        AddEditTodoScreen(onPopBackStack = { navController.popBackStack() })
                    }
                }
            }
        }


    }

}

