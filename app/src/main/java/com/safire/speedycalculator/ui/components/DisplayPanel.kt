package com.safire.speedycalculator.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.safire.speedycalculator.R


@Composable
fun DisplayPanel(
    enteredExpression: String,
    calculationResult: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .padding(dimensionResource(R.dimen.medium_padding))
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