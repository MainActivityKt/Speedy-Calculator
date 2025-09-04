package com.safire.speedycalculator.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.safire.speedycalculator.R
import com.safire.speedycalculator.model.KeyCategory
import com.safire.speedycalculator.ui.theme.SpeedyCalculatorTheme

data class CalculatorButton(val text: String, val category: KeyCategory)

@Composable
fun App(
    viewModel: CalculatorViewModel = viewModel(),
    modifier: Modifier = Modifier
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

    val uiState = viewModel.uiState.collectAsState()


    Scaffold(
        topBar = { TopBar() }
    ) { innerPadding ->

        Column(

            modifier = modifier
                .padding(innerPadding)
        ) {
            Text(
                uiState.value.enteredEquation,
                style = MaterialTheme.typography.bodySmall,
                lineHeight = 1.2.em,
                fontSize = 38.sp,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.medium_padding))
            )

            Text(
                uiState.value.result,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 34.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.medium_padding))
            )


            IconButton(
                onClick = { viewModel.clearLast() },
                modifier = Modifier
                    .align(Alignment.End)
                    .size(56.dp)

                    .padding(dimensionResource(R.dimen.medium_padding))

            ) {
                Icon(
                    painterResource(R.drawable.eraser),
                    contentDescription = "eraser",
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            }



            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.Bottom,
                contentPadding = PaddingValues(
                    top = dimensionResource(R.dimen.medium_padding),
                    bottom = dimensionResource(R.dimen.large_padding),
                    start = dimensionResource(R.dimen.small_padding),
                    end = dimensionResource(R.dimen.small_padding)),
                modifier = Modifier
                    .fillMaxHeight()

            ) {




                items(keys) { it ->
                    Button(calculatorButton = it, onClick = { viewModel.onButtonClick(it) })
                }

            }
        }


    }
}

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
        modifier = Modifier
            .fillMaxWidth()
            .size(84.dp)
            .padding(4.dp),
        onClick = { onClick(calculatorButton) }
    ) {
        Text(
            calculatorButton.text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier


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
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
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