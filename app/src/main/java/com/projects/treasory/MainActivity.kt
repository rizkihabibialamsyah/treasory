package com.projects.treasory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.projects.treasory.ui.TreasoryApp
import com.projects.treasory.ui.theme.TreasoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TreasoryTheme {
                TreasoryApp()
            }
        }
    }
}
