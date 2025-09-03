package com.safire.speedycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.safire.speedycalculator.ui.App
import com.safire.speedycalculator.ui.theme.SpeedyCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpeedyCalculatorTheme {
                App()
            }
        }
    }
}