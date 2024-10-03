package com.example.reciclajeeventos

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.Month

class CalendarActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calendar)

        val monthYearText = findViewById<TextView>(R.id.month_year_text)
        val month = Month.OCTOBER
        val year = 2024

        monthYearText.text = "${month.name} $year"

        val composeView = findViewById<ComposeView>(R.id.compose_calendar)
        composeView.setContent {
            CalendarPreview(month, year)
        }
    }

    @Composable
    fun CalendarPreview(month: Month, year: Int) {
        val daysInMonth = LocalDate.of(year, month.value, 1).lengthOfMonth()
        val firstDayOfMonth = LocalDate.of(year, month.value, 1).dayOfWeek.value % 7

        var currentDay = 1

        Column {
            // Título de los días de la semana
            Row(modifier = Modifier.fillMaxWidth()) {
                val daysOfWeek = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")
                for (day in daysOfWeek) {
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f).padding(8.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            // Días del mes
            for (week in 0..5) { // Máximo de 6 semanas en un mes
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (dayOfWeek in 0..6) { // Días de la semana (0 = Lunes, 6 = Domingo)
                        if (week == 0 && dayOfWeek < firstDayOfMonth || currentDay > daysInMonth) {
                            // Colocamos un espacio vacío si no hay día que mostrar
                            Spacer(modifier = Modifier.weight(1f).padding(8.dp))
                        } else {
                            // Mostramos el día actual
                            Text(
                                text = currentDay.toString(),
                                modifier = Modifier.weight(1f).padding(8.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center // Centramos el texto
                            )
                            currentDay++
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun CalendarPreview() {
        CalendarPreview(Month.OCTOBER, 2024)
    }
}
