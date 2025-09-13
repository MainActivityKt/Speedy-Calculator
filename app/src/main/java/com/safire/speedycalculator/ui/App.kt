package com.safire.speedycalculator.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.preferredFrameRate
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
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

import com.safire.speedycalculator.R
import com.safire.speedycalculator.model.KeyCategory
import com.safire.speedycalculator.ui.theme.SpeedyCalculatorTheme

data class CalculatorButton(val text: String, val category: KeyCategory)

@Composable
fun App(
    viewModel: CalculatorViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val width = windowSizeClass.windowWidthSizeClass

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopBar() }
    ) { innerPadding ->

        if (width != WindowWidthSizeClass.EXPANDED) {
            Column(
                modifier = modifier.padding(innerPadding)

            ) {
                DisplayPanel(
                    uiState.value.enteredExpression,
                    uiState.value.result.format(),
                    modifier = Modifier.fillMaxHeight(0.3f)
                )

                EraserButton(
                    uiState.value.enteredExpression.isNotBlank(),
                    { viewModel.clearLast() },
                    modifier = Modifier
                        .align(Alignment.End)
                )

                KeypadPanel(
                    onClick = { viewModel.onButtonClick(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.7f)
                    ,
                    buttonModifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                    ,
                    rowModifier = Modifier.weight(1f)
                    // for calculator buttons' ratio
                )
            }
        } else {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(innerPadding)

            ) {
                KeypadPanel(
                    onClick = { viewModel.onButtonClick(it) },
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    buttonModifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    rowModifier = Modifier.weight(1f)

                )

                EraserButton(
                    uiState.value.enteredExpression.isNotBlank(),
                    { viewModel.clearLast() },
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                DisplayPanel(
                    uiState.value.enteredExpression,
                    uiState.value.result.format(),

                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )

            }
        }
    }
}

@Composable
fun DisplayPanel(
    enteredExpression: String,
    calculationResult: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .padding(8.dp)
    ) {

        Text(
            enteredExpression,
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 1.1.em,
            fontSize = 44.sp,
            textAlign = TextAlign.End,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(0.1f)
                    .padding(dimensionResource(R.dimen.medium_padding))
        )
        Text(
            calculationResult,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .padding(dimensionResource(R.dimen.medium_padding))
        )
    }
}

@Composable
fun EraserButton(
    isEraserEnabled: Boolean,
    onEraserClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val iconColor =
        if (isEraserEnabled) MaterialTheme.colorScheme.tertiary
        else MaterialTheme.colorScheme.tertiaryContainer

    Box(modifier = modifier) {
        IconButton(
            onClick = onEraserClick,
            enabled = isEraserEnabled,
            modifier = Modifier
                .size(dimensionResource(R.dimen.eraser_button_size))
        ) {
            Icon(
                painterResource(R.drawable.eraser),
                contentDescription = stringResource(R.string.eraser_description),
                tint = iconColor
            )
        }
    }
}

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold
            )
        }, navigationIcon = {
            Image(painterResource(R.drawable.ic_launcher_foreground), contentDescription = null)
        }, colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

fun String.format(): String {
    if (isEmpty()) return ""
    val formattedDigit = "%,.10f".format(this.toDouble())
    // removes trailing zeros in decimal digits
    return formattedDigit.replace("\\.?0*$".toRegex(), "")
}


@Preview(
    showBackground = true, showSystemUi = true,
    device = "id:pixel_xl"
)
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