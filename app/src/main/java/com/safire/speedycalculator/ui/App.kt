package com.safire.speedycalculator.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowWidthSizeClass
import com.safire.speedycalculator.model.KeyCategory
import com.safire.speedycalculator.ui.components.*
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

fun String.format(): String {
    if (isEmpty()) return ""
    val formattedDigit = "%,.10f".format(this.toDouble())
    // removes trailing zeros in decimal digits
    return formattedDigit.replace("\\.0*$".toRegex(), "")
}


@Preview(
    showBackground = true, showSystemUi = true
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