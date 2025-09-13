package com.safire.speedycalculator.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.safire.speedycalculator.R


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