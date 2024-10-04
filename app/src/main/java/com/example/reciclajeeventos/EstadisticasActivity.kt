package com.example.reciclajeeventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.reciclajeeventos.ui.theme.ReciclajeEventosTheme
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class EstadisticasActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReciclajeEventosTheme {
                EstadisticasScreen()
            }
        }
    }

    @Composable
    fun EstadisticasScreen() {
        var envasesCount by remember { mutableStateOf(0) }
        var vidrioCount by remember { mutableStateOf(0) }
        var organicoCount by remember { mutableStateOf(0) }
        var showSnackbar by remember { mutableStateOf(false) }

        val entries = listOf(
            BarEntry(0f, envasesCount.toFloat()),
            BarEntry(1f, vidrioCount.toFloat()),
            BarEntry(2f, organicoCount.toFloat())
        )
        val dataSet = BarDataSet(entries, "Reciclaje")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        val barData = BarData(dataSet)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView(
                factory = { context ->
                    BarChart(context).apply {
                        data = barData
                        description.isEnabled = false
                        setFitBars(true)
                        axisLeft.axisMaximum = 10f // Set maximum value to 10
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                update = { chart ->
                    chart.data = barData
                    chart.invalidate()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(onClick = {
                    if (envasesCount + 1 > 10) {
                        envasesCount = 0
                        showSnackbar = true
                    } else {
                        envasesCount++
                    }
                    updateChartData(envasesCount, vidrioCount, organicoCount)
                }) {
                    Text("Envases")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    if (vidrioCount + 1 > 10) {
                        vidrioCount = 0
                        showSnackbar = true
                    } else {
                        vidrioCount++
                    }
                    updateChartData(envasesCount, vidrioCount, organicoCount)
                }) {
                    Text("Vidrio")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    if (organicoCount + 1 > 10) {
                        organicoCount = 0
                        showSnackbar = true
                    } else {
                        organicoCount++
                    }
                    updateChartData(envasesCount, vidrioCount, organicoCount)
                }) {
                    Text("Orgánico")
                }
            }

            if (showSnackbar) {
                Snackbar(
                    action = {
                        Button(onClick = { showSnackbar = false }) {
                            Text("OK")
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Felicidades eres un ECOfriend")
                }
            }
        }
    }

    private fun updateChartData(envases: Int, vidrio: Int, organico: Int): BarData {
        val entries = listOf(
            BarEntry(0f, envases.toFloat()),
            BarEntry(1f, vidrio.toFloat()),
            BarEntry(2f, organico.toFloat())
        )
        val dataSet = BarDataSet(entries, "Reciclaje")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        return BarData(dataSet)
    }
}