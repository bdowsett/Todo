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
import com.example.simpletodo.ui.theme.SimpleToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleToDoTheme {

            }
        }


    }

}

