package com.safire.speedycalculator.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.safire.speedycalculator.model.KeyCategory
import com.safire.speedycalculator.ui.CalculatorButton

@Composable
fun Button(
    calculatorButton: CalculatorButton,
    onClick: (CalculatorButton) -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = when (calculatorButton.category) {
        KeyCategory.DIGIT, KeyCategory.DECIMAL, KeyCategory.NUMBER_SIGN -> MaterialTheme.colorScheme.secondary
        KeyCategory.OPERATOR, KeyCategory.PARENTHESIS -> MaterialTheme.colorScheme.tertiary
        KeyCategory.EQUAL_SIGN -> MaterialTheme.colorScheme.onTertiary
        KeyCategory.CLEAR -> MaterialTheme.colorScheme.onErrorContainer
    }

    val contentColor = when (calculatorButton.category) {
        KeyCategory.DIGIT, KeyCategory.DECIMAL, KeyCategory.NUMBER_SIGN -> MaterialTheme.colorScheme.onSecondary
        KeyCategory.OPERATOR, KeyCategory.PARENTHESIS -> MaterialTheme.colorScheme.onTertiary
        KeyCategory.EQUAL_SIGN -> MaterialTheme.colorScheme.tertiary
        KeyCategory.CLEAR -> MaterialTheme.colorScheme.onError
    }

    val buttonColors = ButtonDefaults.buttonColors().copy(containerColor, contentColor)

    Button(
        contentPadding = PaddingValues(0.dp),
        colors = buttonColors,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        shape = CircleShape,
        modifier = modifier
            .padding(4.dp),
        onClick = { onClick(calculatorButton) }
    ) {
        Text(
            calculatorButton.text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
    }
}