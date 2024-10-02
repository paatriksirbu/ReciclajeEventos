package com.example.reciclajeeventos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ScaleGestureDetector
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.KeyEventDispatcher.Component

class MapActivity : ComponentActivity() {

    private lateinit var imageView: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        imageView = findViewById(R.id.mapImageView)

        // Configurar el detector de gestos para el zoom
        scaleGestureDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = scaleFactor.coerceIn(0.5f, 3.0f) // Limitar el zoom
                imageView.scaleX = scaleFactor
                imageView.scaleY = scaleFactor
                return true
            }
        })
    }

    override fun onTouchEvent(event: android.view.MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        when(event.actionMasked) {
            android.view.MotionEvent.ACTION_SCROLL -> {
                val scrollDelta = event.getAxisValue(android.view.MotionEvent.AXIS_VSCROLL)
                handleMouseScrollZoom(scrollDelta)
            }
        }

        return true
    }

    private fun handleMouseScrollZoom(scrollDelta: Float) {
        if (scrollDelta > 0) {
            scaleFactor *= 1.1f // Acercar zoom
        } else {
            scaleFactor *= 0.9f // Alejar zoom
        }

        // Limitar el zoom entre 0.5x y 3.0x
        scaleFactor = scaleFactor.coerceIn(0.5f, 3.0f)

        imageView.scaleX = scaleFactor
        imageView.scaleY = scaleFactor
    }

}
