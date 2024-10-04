package com.example.reciclajeeventos

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import androidx.activity.ComponentActivity
import kotlin.random.Random

class MapActivity : ComponentActivity() {

    private lateinit var imageView: ImageView
    private lateinit var point1: ImageView
    private lateinit var point2: ImageView
    private lateinit var point3: ImageView
    private lateinit var point4: ImageView
    private lateinit var point5: ImageView
    private lateinit var point6: ImageView
    private lateinit var point7: ImageView

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
    private val matrix = Matrix()
    private val savedMatrix = Matrix()

    // Variables para el desplazamiento
    private var startX = 0f
    private var startY = 0f
    private var translateX = 0f
    private var translateY = 0f
    private var mode = NONE

    // Variables para almacenar las coordenadas de los puntos
    private val pointCoordinates = mutableListOf<Pair<Float, Float>>()

    companion object {
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 2
    }

    @SuppressLint("ClickableViewAccessibility", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        imageView = findViewById(R.id.mapImageView)
        point1 = findViewById(R.id.point1)
        point2 = findViewById(R.id.point2)
        point3 = findViewById(R.id.point3)
        point4 = findViewById(R.id.point4)
        point5 = findViewById(R.id.point5)
        point6 = findViewById(R.id.point6)
        point7 = findViewById(R.id.point7)

        // Ajustar la imagen a la pantalla
        imageView.post {
            val imageWidth = imageView.drawable.intrinsicWidth
            val imageHeight = imageView.drawable.intrinsicHeight

            val scaleX = imageView.width.toFloat() / imageWidth
            val scaleY = imageView.height.toFloat() / imageHeight

            // Escalar la imagen para que llene la pantalla sin distorsión
            val scale = scaleX.coerceAtLeast(scaleY)
            matrix.setScale(scale, scale, imageView.width / 2f, imageView.height / 2f)

            // Centrar la imagen
            matrix.postTranslate(
                (imageView.width - imageWidth * scale) / 2f,
                (imageView.height - imageHeight * scale) / 2f
            )
            imageView.imageMatrix = matrix

            // Llamamos a limitImageMovement() inmediatamente después de cargar la imagen
            limitImageMovement()
            placePointsRandomly()
        }

        // Configura el detector de gestos de zoom
        scaleGestureDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = scaleFactor.coerceIn(0.5f, 3.0f) // Limitar el zoom
                matrix.postScale(detector.scaleFactor, detector.scaleFactor, detector.focusX, detector.focusY)
                imageView.imageMatrix = matrix
                limitImageMovement()  // Restringir el movimiento
                updatePointsPosition()
                return true
            }
        })

        // Configura los eventos táctiles para el desplazamiento y el zoom
        imageView.setOnTouchListener { _, event ->
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    savedMatrix.set(matrix)
                    startX = event.x
                    startY = event.y
                    mode = DRAG
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    mode = ZOOM
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    mode = NONE
                }
                MotionEvent.ACTION_MOVE -> {
                    if (mode == DRAG) {
                        // Desplazamiento
                        matrix.set(savedMatrix)
                        translateX = event.x - startX
                        translateY = event.y - startY
                        matrix.postTranslate(translateX, translateY)
                    } else if (mode == ZOOM) {
                        // Escalar con dos dedos
                        scaleGestureDetector.onTouchEvent(event)
                    }
                }
            }
            imageView.imageMatrix = matrix
            limitImageMovement()  // Restringir el movimiento
            updatePointsPosition()
            true
        }
    }

    // Método para restringir el desplazamiento
    private fun limitImageMovement() {
        val drawable = imageView.drawable ?: return

        val imageWidth = drawable.intrinsicWidth * scaleFactor
        val imageHeight = drawable.intrinsicHeight * scaleFactor

        val viewWidth = imageView.width.toFloat()
        val viewHeight = imageView.height.toFloat()

        // Calcula los límites del movimiento
        val bounds = RectF(0f, 0f, imageWidth, imageHeight)
        matrix.mapRect(bounds)

        var offsetX = 0f
        var offsetY = 0f

        // Restringe en el eje X
        if (bounds.left > 0) {
            offsetX = -bounds.left
        } else if (bounds.right < viewWidth) {
            offsetX = viewWidth - bounds.right
        }

        // Restringe en el eje Y
        if (bounds.top > 0) {
            offsetY = -bounds.top
        } else if (bounds.bottom < viewHeight) {
            offsetY = viewHeight - bounds.bottom
        }

        // Aplica las restricciones de movimiento
        matrix.postTranslate(offsetX, offsetY)
        imageView.imageMatrix = matrix
    }

    // Método para colocar los puntos en posiciones aleatorias
    private fun placePointsRandomly() {
        val points = listOf(point1, point2, point3, point4, point5, point6, point7)
        val drawable = imageView.drawable ?: return

        val imageWidth = drawable.intrinsicWidth
        val imageHeight = drawable.intrinsicHeight

        if (pointCoordinates.isEmpty()) {
            for (point in points) {
                val randomX = Random.nextFloat() * imageWidth
                val randomY = Random.nextFloat() * imageHeight
                pointCoordinates.add(Pair(randomX, randomY))
            }
        }

        updatePointsPosition()
    }

    // Método para actualizar la posición de los puntos
    private fun updatePointsPosition() {
        val points = listOf(point1, point2, point3, point4, point5, point6, point7)
        for ((index, point) in points.withIndex()) {
            val (randomX, randomY) = pointCoordinates[index]
            val mappedPoints = floatArrayOf(randomX, randomY)
            matrix.mapPoints(mappedPoints)
            point.x = mappedPoints[0] - point.width / 2
            point.y = mappedPoints[1] - point.height / 2
            point.visibility = ImageView.VISIBLE
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Necesario para el zoom
        return scaleGestureDetector.onTouchEvent(event)
    }
}