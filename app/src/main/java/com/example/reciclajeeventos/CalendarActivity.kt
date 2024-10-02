package com.example.reciclajeeventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview

class CalendarActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calendar)

        val composeView = findViewById<ComposeView>(R.id.compose_view)
    }

    @Composable
    fun calendario(){

    }

    @Composable
    @Preview(showBackground = true)
    fun calendarioPreview(){
        calendario()
    }
}