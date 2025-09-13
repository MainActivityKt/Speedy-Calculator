package com.safire.speedycalculator.model

object DataSource {

    val calculatorKeys = listOf(
        CalculatorButton("C", KeyCategory.CLEAR),
        CalculatorButton("()", KeyCategory.PARENTHESIS),
        CalculatorButton("%", KeyCategory.OPERATOR),
        CalculatorButton("÷", KeyCategory.OPERATOR),

        CalculatorButton("7", KeyCategory.DIGIT),
        CalculatorButton("8", KeyCategory.DIGIT),
        CalculatorButton("9", KeyCategory.DIGIT),
        CalculatorButton("×", KeyCategory.OPERATOR),

        CalculatorButton("4", KeyCategory.DIGIT),
        CalculatorButton("5", KeyCategory.DIGIT),
        CalculatorButton("6", KeyCategory.DIGIT),
        CalculatorButton("-", KeyCategory.OPERATOR),

        CalculatorButton("1", KeyCategory.DIGIT),
        CalculatorButton("2", KeyCategory.DIGIT),
        CalculatorButton("3", KeyCategory.DIGIT),
        CalculatorButton("+", KeyCategory.OPERATOR),

        CalculatorButton("+/-", KeyCategory.NUMBER_SIGN),
        CalculatorButton("0", KeyCategory.DIGIT),
        CalculatorButton(".", KeyCategory.DECIMAL),
        CalculatorButton("=", KeyCategory.EQUAL_SIGN),
    )
}