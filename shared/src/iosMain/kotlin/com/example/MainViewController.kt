package com.example

import androidx.compose.ui.window.ComposeUIViewController
import com.example.viewmodel.KiosquitoViewModel

fun MainViewController() = ComposeUIViewController {
    val viewModel = KiosquitoViewModel()
    App(viewModel = viewModel)
}
