package com.safire.speedycalculator.ui

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.safire.speedycalculator.calculator.Calculator
import com.safire.speedycalculator.model.CalculatorUiState
import com.safire.speedycalculator.model.KeyCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val OPENING_PARENTHESIS = "("
private const val CLOSING_PARENTHESIS = ")"

class CalculatorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState = _uiState.asStateFlow()

    private var lastEntryHasDecimal = false
    private var openingParenthesisCount = 0
    private val calculator = Calculator()


    fun onButtonClick(button: CalculatorButton) {

        val lastValue =
            if (_uiState.value.enteredExpression.isNotEmpty()) _uiState.value.enteredExpression.last() else ' '

        when (button.category) {
            KeyCategory.OPERATOR -> {

                if (lastValue.isDigit() || lastValue.isOpeningParenthesis() || lastValue.isClosingParenthesis()) {
                    addToEquation(button.text)
                } else if (lastValue.isOperator()) {
                    changeLastValue(button.text)
                }
            }

            KeyCategory.DECIMAL -> {
                if (!lastEntryHasDecimal) {
                    // if decimal is the first digit or comes after a non-digit item, change it to 0.
                    addToEquation(
                        if (!lastValue.isDigit()) "0${button.text}" else button.text
                    )
                    lastEntryHasDecimal = true
                }
            }

            KeyCategory.PARENTHESIS -> {
                if (lastValue.isDigit() || lastValue.isClosingParenthesis()) {
                    if (openingParenthesisCount > 0) {
                        addToEquation(CLOSING_PARENTHESIS)
                        openingParenthesisCount--
                    } else {
                        addToEquation("x$OPENING_PARENTHESIS")
                        openingParenthesisCount++
                    }
                } else if (lastValue.isWhitespace() || lastValue.isOperator() || lastValue.isOpeningParenthesis()) {
                    addToEquation(OPENING_PARENTHESIS)
                    openingParenthesisCount++
                }
            }

            KeyCategory.DIGIT -> {
                if (lastValue.isClosingParenthesis()) {
                    addToEquation("x${button.text}")
                }
                addToEquation(button.text)
            }

            KeyCategory.CLEAR -> {
                resetCalculator()

            }

            KeyCategory.EQUAL_SIGN -> {
                if (!lastValue.isOperator()) {
                    var result = calculator.calculate(_uiState.value.enteredExpression)
                    result = "%,.10f".format(result.toDouble())
                    // removes trailing zeros in decimal digits
                    val formattedResult = result.replace(Regex("\\.?0*$"), "")

                    _uiState.update {
                        it.copy(result = formattedResult)
                    }
                    openingParenthesisCount = 0
                    lastEntryHasDecimal = false
                }
            }

            KeyCategory.NUMBER_SIGN -> {
                val lasTwoDigits = _uiState.value.enteredExpression.takeLast(2)
                if (lasTwoDigits != "(-") {
                    addToEquation("(-")
                    openingParenthesisCount++
                } else {
                    _uiState.update { it ->
                        it.copy(enteredExpression = it.enteredExpression.dropLast(2))
                    }
                }
            }
        }


    }

    private fun addToEquation(value: String) {
        if (!value.isDigitsOnly()) {
            lastEntryHasDecimal = false
        }
        _uiState.update {
            it.copy(enteredExpression = it.enteredExpression + value, result = "")
        }
    }

    private fun changeLastValue(value: String) {
        _uiState.update {
            it.copy(enteredExpression = it.enteredExpression.dropLast(1) + value)
        }
    }

    fun clearLast() {
        val lastValue =
            (if (_uiState.value.enteredExpression.isNotEmpty()) _uiState.value.enteredExpression.last() else ' ').toString()

        when (lastValue) {
            OPENING_PARENTHESIS -> openingParenthesisCount--
            CLOSING_PARENTHESIS -> openingParenthesisCount++
            "." -> lastEntryHasDecimal = false
        }
        _uiState.update { it.copy(enteredExpression = it.enteredExpression.dropLast(1)) }

    }

    private fun resetCalculator() {
        _uiState.update { it -> CalculatorUiState("", "") }
        openingParenthesisCount = 0
        lastEntryHasDecimal = false
    }
}


fun Char.isClosingParenthesis() = this.toString() == CLOSING_PARENTHESIS
fun Char.isOpeningParenthesis() = this.toString() == OPENING_PARENTHESIS

fun Char.isDigit() = toString().matches(Regex("\\d"))

fun Char.isOperator() = this.toString().matches(Regex("[+\\-x÷%]"))