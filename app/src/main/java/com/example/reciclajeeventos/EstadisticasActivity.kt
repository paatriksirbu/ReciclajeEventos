package com.example.reciclajeeventos


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EstadisticasActivity : AppCompatActivity() {

    // Variables para llevar la cuenta de los reciclajes
    private var envasesCount = 0
    private var vidrioCount = 0
    private var organicoCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas)

        // Referencias a los TextViews
        val envasesTextView = findViewById<TextView>(R.id.envases_count)
        val vidrioTextView = findViewById<TextView>(R.id.vidrio_count)
        val organicoTextView = findViewById<TextView>(R.id.organico_count)

        // Referencias a los botones
        val botonEnvases = findViewById<Button>(R.id.boton_envases)
        val botonVidrio = findViewById<Button>(R.id.boton_vidrio)
        val botonOrganico = findViewById<Button>(R.id.boton_organico)

        // Lógica para incrementar envases
        botonEnvases.setOnClickListener {
            envasesCount++
            envasesTextView.text = envasesCount.toString()
        }

        // Lógica para incrementar vidrio
        botonVidrio.setOnClickListener {
            vidrioCount++
            vidrioTextView.text = vidrioCount.toString()
        }

        // Lógica para incrementar orgánico
        botonOrganico.setOnClickListener {
            organicoCount++
            organicoTextView.text = organicoCount.toString()
        }
    }
}
