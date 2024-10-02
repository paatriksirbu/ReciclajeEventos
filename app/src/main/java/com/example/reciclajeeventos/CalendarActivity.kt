package com.example.reciclajeeventos

import android.os.Bundle
import android.widget.TableLayout
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.children
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

        monthYearText.text = "${month.name} $year"

        fillCalendar(calendarRow, month, year)
    }

    @Composable
    private fun fillCalendar(calendarRow: TableRow, month: Month, year: Int) {
        val daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth()
        val firstDayOfMonth = LocalDate.of(year, month, 1).dayOfWeek.value

        var currentDay = 1

        for (row in 0..5) { // Máximo 6 filas para el mes
            val tableRow = TableRow(this)

            for (column in 0..6) { // 7 días de la semana
                val textView = TextView(this).apply {
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f // Ocupa el mismo espacio
                    )
                    gravity = android.view.Gravity.CENTER
                    if (row == 0 && column < firstDayOfMonth - 1 || currentDay > daysInMonth) {
                        // Espacio en blanco
                        text = ""
                    } else {
                        // Mostrar el día
                        text = currentDay.toString()
                        currentDay++
                    }
                }
                tableRow.addView(textView)
            }
            calendarRow.addView(tableRow)
        }
    }

    @Composable
    fun CalendarPreview(month: Month, year: Int) {
        val daysInMonth = LocalDate.of(year, month.value, 1).lengthOfMonth()
        val firstDayOfMonth = LocalDate.of(year, month.value, 1).dayOfWeek.value

        Column {
            for (week in 0..5) {
                Row {
                    for (dayOfWeek in 1..7) {
                        val day = (week * 7 + dayOfWeek) - (firstDayOfMonth - 1)
                        if (day in 1..daysInMonth) {
                            Text(text = day.toString(), modifier = Modifier.padding(8.dp))
                        } else {
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }
    }

}
