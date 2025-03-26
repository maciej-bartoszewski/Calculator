package com.example.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class AdvancedCalculatorActivity : BaseCalculatorActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_advanced_calculator)
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
            R.id.button_multiply to "*", R.id.button_divide to "/",
            R.id.button_powy to "powy"
        )

        val imOpButtons = listOf(
            R.id.button_sin to "sin", R.id.button_cos to "cos",
            R.id.button_tan to "tan", R.id.button_ln to "ln",
            R.id.button_log to "log", R.id.button_sqrt to "sqrt",
            R.id.button_pow to "pow"
        )

        setupNumericButtons(numButtons)
        setupOperatorButtons(opButtons)
        setupDotButton(R.id.button_dot)
        setupChangeSignButton(R.id.button_change_sign)
        setupClearButtons(R.id.button_c, R.id.button_ac)
        setupEqualsButton(R.id.button_equals)

        imOpButtons.forEach { (id, value) ->
            findViewById<android.widget.Button>(id).setOnClickListener {
                operator = value
                findViewById<android.widget.Button>(R.id.button_equals).performClick()
            }
        }

        findViewById<android.widget.Button>(R.id.button_procent).setOnClickListener {
            if (input.isNotEmpty()) {
                if (operator.isNotEmpty()) {
                    when (operator) {
                        "+", "-" -> input = (result.toBigDecimal() * (input.toBigDecimal().divide(100.toBigDecimal(), 10, RoundingMode.HALF_UP))).toString()
                        "*", "/" -> input = (input.toBigDecimal().divide(100.toBigDecimal(), 10, RoundingMode.HALF_UP)).toString()
                        else -> {
                            Toast.makeText(this, "Błąd: niepoprawne działanie", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                    }
                    performCalculation()
                } else {
                    input = (input.toBigDecimal().divide(100.toBigDecimal(), 10, RoundingMode.HALF_UP)).toString()
                    performCalculation()
                }
            } else {
                Toast.makeText(this, "Błąd: niepoprawne działanie", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun performCalculation() {
        try {
            when (operator) {
                "+" -> result = (result.toBigDecimal() + input.toBigDecimal()).toString()
                "-" -> result = (result.toBigDecimal() - input.toBigDecimal()).toString()
                "*" -> result = (result.toBigDecimal() * input.toBigDecimal()).toString()
                "/" -> {
                    if (result.toBigDecimal() == BigDecimal.ZERO) {
                        Toast.makeText(this, "Błąd: nie można dzielić przez 0", Toast.LENGTH_SHORT).show()
                        return
                    } else result = (result.toBigDecimal().divide(input.toBigDecimal(), 10, RoundingMode.HALF_UP)).toString()
                }
                "powy" -> result = result.toDouble().pow(input.toDouble()).toString()

                "ln" -> {
                    if (input.toBigDecimal() <= BigDecimal.ZERO) {
                        Toast.makeText(this, "Błąd: ln(x) wymaga x >= 0", Toast.LENGTH_SHORT).show()
                        return
                    }
                    result = ln(input.toDouble()).toString()
                }

                "log" -> {
                    if (input.toBigDecimal() <= BigDecimal.ZERO) {
                        Toast.makeText(this, "Błąd: log(x) wymaga x >= 0", Toast.LENGTH_SHORT).show()
                        return
                    }
                    result = log10(input.toDouble()).toString()
                }

                "sqrt" -> {
                    if (input.toBigDecimal() < BigDecimal.ZERO) {
                        Toast.makeText(this, "Błąd: sqrt(x) wymaga x > 0", Toast.LENGTH_SHORT).show()
                        return
                    }
                    result = sqrt(input.toDouble()).toString()
                }

                "sin" -> result = sin(input.toDouble()).toString()
                "cos" -> result = cos(input.toDouble()).toString()
                "tan" -> result = tan(input.toDouble()).toString()
                "pow" -> result = input.toDouble().pow(2.0).toString()
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