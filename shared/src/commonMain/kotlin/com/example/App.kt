package com.example

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ui.components.KiosquitoBottomNav
import com.example.ui.components.KiosquitoTopBar
import com.example.ui.screens.CobrosScreen
import com.example.ui.screens.CatalogAdminScreen
import com.example.ui.screens.FiaoScreen
import com.example.ui.screens.PosScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.StockScreen
import com.example.ui.theme.MarketEmerald
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.KiosquitoViewModel
import com.example.viewmodel.MainTab
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App(viewModel: KiosquitoViewModel) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showEditRateDialog by remember { mutableStateOf(false) }
    var editRateInput by remember { mutableStateOf("") }

    LaunchedEffect(state.userToast) {
        state.userToast?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.dismissToast()
        }
    }

    val subtitle = when (state.currentTab) {
        MainTab.POS -> "Vender / POS"
        MainTab.COBROS -> "Cobros & Recibos"
        MainTab.FIAO -> "Clientes Confiables"
        MainTab.STOCK -> "Inventario & Compras"
        MainTab.PROFILE -> "Perfil de la Bodega"
        MainTab.CATALOG_ADMIN -> "Catálogo Público"
    }

    MyApplicationTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                KiosquitoTopBar(
                    storeName = state.storeName,
                    exchangeRate = state.exchangeRateUsdToBs,
                    subtitle = subtitle,
                    onProfileClick = { viewModel.switchTab(MainTab.PROFILE) },
                    onEditRateClick = {
                        editRateInput = state.exchangeRateUsdToBs.toString()
                        showEditRateDialog = true
                    }
                )
            },
            bottomBar = {
                KiosquitoBottomNav(
                    activeTab = state.currentTab,
                    onTabSelected = { viewModel.switchTab(it) }
                )
            }
        ) { innerPadding ->
            val screenModifier = Modifier.padding(innerPadding)
            when (state.currentTab) {
                MainTab.POS -> PosScreen(
                    state = state,
                    viewModel = viewModel,
                    onContinueToCobro = { viewModel.switchTab(MainTab.COBROS) },
                    modifier = screenModifier
                )
                MainTab.COBROS -> CobrosScreen(
                    state = state,
                    viewModel = viewModel,
                    modifier = screenModifier
                )
                MainTab.FIAO -> FiaoScreen(
                    state = state,
                    viewModel = viewModel,
                    modifier = screenModifier
                )
                MainTab.STOCK -> StockScreen(
                    state = state,
                    viewModel = viewModel,
                    modifier = screenModifier
                )
                MainTab.PROFILE -> ProfileScreen(
                    state = state,
                    viewModel = viewModel,
                    onBack = { viewModel.switchTab(MainTab.POS) },
                    modifier = screenModifier
                )
                MainTab.CATALOG_ADMIN -> CatalogAdminScreen(
                    state = state,
                    viewModel = viewModel,
                    onBack = { viewModel.switchTab(MainTab.PROFILE) },
                    modifier = screenModifier
                )
            }
        }

        if (showEditRateDialog) {
            AlertDialog(
                onDismissRequest = { showEditRateDialog = false },
                title = { Text("Actualizar Tasa de Cambio (1 USD)") },
                text = {
                    OutlinedTextField(
                        value = editRateInput,
                        onValueChange = { editRateInput = it },
                        label = { Text("Tasa en Bolívares (Bs)") },
                        singleLine = true
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val newRate = editRateInput.toDoubleOrNull()
                            if (newRate != null && newRate > 0) {
                                viewModel.updateExchangeRate(newRate)
                            }
                            showEditRateDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MarketEmerald)
                    ) {
                        Text("Actualizar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEditRateDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    App(viewModel = KiosquitoViewModel())
}
