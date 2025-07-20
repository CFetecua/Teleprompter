package com.example.teleprompter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputText: EditText
    private lateinit var startButton: Button
    private lateinit var scrollView: ScrollView
    private lateinit var displayText: TextView
    private val handler = Handler(Looper.getMainLooper())

    private var scrollY = 0
    private var isScrolling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pantalla completa
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.inputText)
        startButton = findViewById(R.id.startButton)
        scrollView = findViewById(R.id.scrollView)
        displayText = findViewById(R.id.displayText)

        startButton.setOnClickListener {
            val text = inputText.text.toString()
            if (text.isNotEmpty()) {
                displayText.text = text
                inputText.visibility = EditText.GONE
                startButton.visibility = Button.GONE
                scrollView.visibility = ScrollView.VISIBLE
                startAutoScroll()
            } else {
                Toast.makeText(this, "Escribe algún texto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startAutoScroll() {
        isScrolling = true
        handler.post(object : Runnable {
            override fun run() {
                if (isScrolling) {
                    scrollY += 2 // velocidad del scroll
                    scrollView.smoothScrollTo(0, scrollY)
                    handler.postDelayed(this, 50) // velocidad de repetición
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        isScrolling = false
    }
}
