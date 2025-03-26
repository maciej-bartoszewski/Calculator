package com.example.calculator

import android.os.Bundle
import java.math.BigDecimal
import java.math.RoundingMode
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SimpleCalculatorActivity : BaseCalculatorActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simple_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val numButtons = listOf(
            R.id.button_0 to "0", R.id.button_1 to "1", R.id.button_2 to "2", R.id.button_3 to "3",
            R.id.button_4 to "4", R.id.button_5 to "5", R.id.button_6 to "6", R.id.button_7 to "7",
            R.id.button_8 to "8", R.id.button_9 to "9"
        )

        val opButtons = listOf(
            R.id.button_plus to "+", R.id.button_minus to "-",
            R.id.button_multiply to "*", R.id.button_divide to "/"
        )

        setupNumericButtons(numButtons)
        setupOperatorButtons(opButtons)
        setupDotButton(R.id.button_dot)
        setupChangeSignButton(R.id.button_change_sign)
        setupClearButtons(R.id.button_c, R.id.button_ac)
        setupEqualsButton(R.id.button_equals)
    }

    override fun performCalculation() {
        try {
            when (operator) {
                "+" -> result = (result.toBigDecimal() + input.toBigDecimal()).toString()
                "-" -> result = (result.toBigDecimal() - input.toBigDecimal()).toString()
                "*" -> result = (result.toBigDecimal() * input.toBigDecimal()).toString()
                "/" -> {
                    if (input.toBigDecimal() == BigDecimal.ZERO) {
                        Toast.makeText(this, "Błąd: nie można dzielić przez 0", Toast.LENGTH_SHORT).show()
                        return
                    }
                    result = result.toBigDecimal().divide(input.toBigDecimal(), 10, RoundingMode.HALF_UP).toString()
                }
                else -> result = input
            }
            result = BigDecimal(result).stripTrailingZeros().toPlainString()

            input = result
            updateDisplay()
        } catch (e: Exception) {
            Toast.makeText(this, "Błąd obliczeń", Toast.LENGTH_SHORT).show()
        }
    }
}