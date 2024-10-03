package com.example.reciclajeeventos

import android.os.Bundle
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
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
        val calendarRow = findViewById<TableRow>(R.id.calendar_row)
        val month = Month.OCTOBER
        val year = 2024

        // Seteamos el nombre del mes y el año en el TextView
        monthYearText.text = "${month.name} $year"

        val composeView = findViewById<ComposeView>(R.id.compose_calendar)
        composeView.setContent {
            CalendarPreview(month, year)
        }
    }

    @Composable
    fun CalendarPreview(month: Month, year: Int) {
        val daysInMonth = LocalDate.of(year, month.value, 1).lengthOfMonth()
        // Ajustamos el primer día del mes. El valor % 7 para hacer que el calendario empiece correctamente.
        val firstDayOfMonth = LocalDate.of(year, month.value, 1).dayOfWeek.value % 7

        var currentDay = 1

        Column {
            for (week in 0..5) { // Generamos las semanas del mes
                Row {
                    for (dayOfWeek in 1..7) { // Días de la semana (1 = lunes, 7 = domingo)
                        // Calculamos el número del día que corresponde a la semana actual
                        val day = (week * 7 + dayOfWeek) - (firstDayOfMonth - 1)

                        if (day in 1..daysInMonth) {
                            // Mostramos el día en el calendario
                            Text(
                                text = day.toString(),
                                modifier = Modifier
                                    .weight(1f) // Se asegura de que cada día ocupe el mismo espacio
                                    .padding(8.dp),
                                textAlign = TextAlign.Center // Centramos el texto
                            )
                        } else {
                            // Espacio vacío para los días fuera de los límites del mes
                            Spacer(modifier = Modifier.weight(1f)) // Se asegura que el espacio vacío tenga el mismo peso que los días
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
