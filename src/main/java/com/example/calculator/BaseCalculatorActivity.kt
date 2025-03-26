package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseCalculatorActivity : AppCompatActivity() {
    protected var input = ""
    protected var result = "0"
    protected var operator = ""
    protected var isOperationFinished = false

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("input", input)
        outState.putString("result", result)
        outState.putString("operator", operator)
        outState.putBoolean("isOperationFinished", isOperationFinished)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        input = savedInstanceState.getString("input", "")
        result = savedInstanceState.getString("result", "0")
        operator = savedInstanceState.getString("operator", "")
        isOperationFinished = savedInstanceState.getBoolean("isOperationFinished", false)
        updateDisplay()
    }

    protected fun setupNumericButtons(numButtons: List<Pair<Int, String>>) {
        numButtons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener {
                if (isOperationFinished) {
                    input = ""
                    isOperationFinished = false
                }
                if (value == "0" && input == "") {
                    input = ""
                } else input += value
                updateDisplay()
            }
        }
    }

    protected fun setupOperatorButtons(opButtons: List<Pair<Int, String>>) {
        opButtons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener {
                if (input.isNotEmpty()) {
                    if (operator.isNotEmpty()) {
                        performCalculation()
                    } else {
                        result = input
                    }
                }
                operator = value
                input = ""
            }
        }
    }

    protected fun setupDotButton(dotButton: Int) {
        findViewById<Button>(dotButton).setOnClickListener {
            if (isOperationFinished) {
                input = "0."
                isOperationFinished = false
            }
            if (!input.contains('.')) {
                if (input.isEmpty()) {
                    input = "0."
                } else {
                    input += "."
                }
                updateDisplay()
            }
        }
    }

    protected fun setupChangeSignButton(changeSignButton: Int) {
        findViewById<Button>(changeSignButton).setOnClickListener {
            if (input.isNotEmpty() && input[0] == '-') {
                input = input.substring(1)
            } else {
                input = "-$input"
            }
            updateDisplay()
        }
    }

    protected fun setupClearButtons(cButton: Int, acButton: Int) {
        findViewById<Button>(cButton).setOnClickListener {
            if (input.isEmpty()) {
                result = "0"
                operator = ""
            } else input = ""
            updateDisplay()
        }

        findViewById<Button>(acButton).setOnClickListener {
            input = ""
            result = "0"
            operator = ""
            isOperationFinished = false
            updateDisplay()
        }
    }

    protected fun setupEqualsButton(equalsButton: Int) {
        findViewById<Button>(equalsButton).setOnClickListener {
            if (input.isNotEmpty() && operator.isNotEmpty()) {
                performCalculation()
                operator = ""
                isOperationFinished = true
            } else {
                Toast.makeText(this, "Błąd: niepoprawne działanie", Toast.LENGTH_SHORT).show()
            }
        }
    }

    protected abstract fun performCalculation()

    protected fun updateDisplay() {
        val textDisplay: TextView = findViewById(R.id.display)
        if (input.isEmpty()) {
            if(result.isEmpty()){
                textDisplay.text = "0"
            } else {
                textDisplay.text = result
            }
        } else textDisplay.text = input
    }
}