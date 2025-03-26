package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSimpleCalculator : Button = findViewById(R.id.buttonSimpleCalculator)
        btnSimpleCalculator.setOnClickListener {
            val intent = Intent(this, SimpleCalculatorActivity::class.java)
            startActivity(intent)
        }

        val btnAdvancedCalculator : Button = findViewById(R.id.buttonAdvancedCalculator)
        btnAdvancedCalculator.setOnClickListener {
            val intent = Intent(this, AdvancedCalculatorActivity::class.java)
            startActivity(intent)
        }

        val btnAbout : Button = findViewById(R.id.buttonAbout)
        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        val btnExit : Button = findViewById(R.id.buttonExit)
        btnExit.setOnClickListener {
            finish()
        }
    }
}