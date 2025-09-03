package com.safire.speedycalculator.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.safire.speedycalculator.R
import com.safire.speedycalculator.model.KeyCategory
import com.safire.speedycalculator.ui.theme.SpeedyCalculatorTheme

data class CalculatorButton(val text: String, val category: KeyCategory)

@Composable
fun App(modifier: Modifier = Modifier) {

    val keys = listOf<CalculatorButton>(
        CalculatorButton("C", KeyCategory.CLEAR),
        CalculatorButton("()", KeyCategory.OPERATOR),
        CalculatorButton("%", KeyCategory.OPERATOR),
        CalculatorButton("÷", KeyCategory.OPERATOR),

        CalculatorButton("7", KeyCategory.DIGIT_OR_DECIMAL),
        CalculatorButton("8", KeyCategory.DIGIT_OR_DECIMAL),
        CalculatorButton("9", KeyCategory.DIGIT_OR_DECIMAL),
        CalculatorButton("x", KeyCategory.OPERATOR),

        CalculatorButton("4", KeyCategory.DIGIT_OR_DECIMAL),
        CalculatorButton("5", KeyCategory.DIGIT_OR_DECIMAL),
        CalculatorButton("6", KeyCategory.DIGIT_OR_DECIMAL),
        CalculatorButton("-", KeyCategory.OPERATOR),

        CalculatorButton("1", KeyCategory.DIGIT_OR_DECIMAL),
        CalculatorButton("2", KeyCategory.DIGIT_OR_DECIMAL),
        CalculatorButton("3", KeyCategory.DIGIT_OR_DECIMAL),
        CalculatorButton("+", KeyCategory.OPERATOR),

        CalculatorButton("+/-", KeyCategory.NUMBER_SIGN),
        CalculatorButton("0", KeyCategory.DIGIT_OR_DECIMAL),
        CalculatorButton(".", KeyCategory.DIGIT_OR_DECIMAL),
        CalculatorButton("=", KeyCategory.EQUAL_SIGN),
    )


    Scaffold(
        topBar = { TopBar() }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {

            LazyVerticalGrid (
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.Bottom,
                contentPadding = innerPadding,
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
            ) {
                items(keys) { it ->
                    Button(calculatorButton = it, onClick = {})
                }

            }

    }
}
    }

@Composable
fun Button(
    calculatorButton: CalculatorButton,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = when(calculatorButton.category) {
        KeyCategory.DIGIT_OR_DECIMAL, KeyCategory.NUMBER_SIGN -> MaterialTheme.colorScheme.secondary
        KeyCategory.OPERATOR -> MaterialTheme.colorScheme.tertiary
        KeyCategory.EQUAL_SIGN -> MaterialTheme.colorScheme.onTertiary
        KeyCategory.CLEAR -> MaterialTheme.colorScheme.onErrorContainer
    }

    val contentColor = when(calculatorButton.category) {
        KeyCategory.DIGIT_OR_DECIMAL, KeyCategory.NUMBER_SIGN -> MaterialTheme.colorScheme.onSecondary
        KeyCategory.OPERATOR -> MaterialTheme.colorScheme.onTertiary
        KeyCategory.EQUAL_SIGN -> MaterialTheme.colorScheme.tertiary
        KeyCategory.CLEAR -> MaterialTheme.colorScheme.onError
    }



    val buttonColors = ButtonDefaults.buttonColors().copy(
        containerColor = containerColor,
        contentColor = contentColor
    )

    Button(
        contentPadding = PaddingValues(0.dp),
        colors = buttonColors,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 8.dp),
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        onClick = onClick
    ) {
        Text(
            calculatorButton.text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleMedium
            )
        }, navigationIcon = {
            Image(painterResource(R.drawable.ic_launcher_foreground), contentDescription = null)
        }, colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreviewLightMode() {
    SpeedyCalculatorTheme {
        App()
    }
}

@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun AppPreviewLDarkMode() {
    SpeedyCalculatorTheme {
        App()
    }
}