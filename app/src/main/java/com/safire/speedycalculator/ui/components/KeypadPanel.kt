package com.safire.speedycalculator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.safire.speedycalculator.R
import com.safire.speedycalculator.model.KeyCategory
import com.safire.speedycalculator.ui.Button
import com.safire.speedycalculator.ui.CalculatorButton


@Composable
fun KeypadPanel(
    onClick: (CalculatorButton) -> Unit,
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier
) {

    val keys = listOf(
        CalculatorButton("C", KeyCategory.CLEAR),
        CalculatorButton("()", KeyCategory.PARENTHESIS),
        CalculatorButton("%", KeyCategory.OPERATOR),
        CalculatorButton("÷", KeyCategory.OPERATOR),

        CalculatorButton("7", KeyCategory.DIGIT),
        CalculatorButton("8", KeyCategory.DIGIT),
        CalculatorButton("9", KeyCategory.DIGIT),
        CalculatorButton("x", KeyCategory.OPERATOR),

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

    Column(
        modifier
            .fillMaxHeight()
            .padding(
                top = dimensionResource(R.dimen.small_padding),
                bottom = dimensionResource(R.dimen.medium_padding),
                start = dimensionResource(R.dimen.medium_padding),
                end = dimensionResource(R.dimen.medium_padding)
            ),
        verticalArrangement = Arrangement.Bottom
    ) {

        keys.chunked(4).forEach { keysRow ->
            Row(
                modifier = rowModifier
                    .fillMaxWidth()

            ) {
                keysRow.forEach { key ->
                    Button(
                        key,
                        onClick,
                        buttonModifier
                    )
                }
            }
        }
    }
}