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
import com.safire.speedycalculator.model.CalculatorButton
import com.safire.speedycalculator.model.DataSource


@Composable
fun KeypadPanel(
    onClick: (CalculatorButton) -> Unit,
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier
) {



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

        DataSource.calculatorKeys.chunked(4).forEach { keysRow ->
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