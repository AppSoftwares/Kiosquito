package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Warehouse
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.util.format
import com.example.model.InventoryProduct
import com.example.model.SupplierInvoice
import com.example.model.UnitType
import com.example.ui.theme.AlertRed
import com.example.ui.theme.AlertRedContainer
import com.example.ui.theme.EmeraldFixed
import com.example.ui.theme.MarigoldContainer
import com.example.ui.theme.MarketEmerald
import com.example.ui.theme.OnAlertRedContainer
import com.example.ui.theme.OnEmeraldFixed
import com.example.ui.theme.OnSecondaryFixed
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.WarmSurfaceContainer
import com.example.ui.theme.WarmSurfaceContainerLow
import com.example.ui.theme.WarmSurfaceContainerLowest
import com.example.viewmodel.AppUiState
import com.example.viewmodel.KiosquitoViewModel

@Composable
fun StockScreen(
    state: AppUiState,
    viewModel: KiosquitoViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Inventario, 1: Facturas & Proveedores
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }
    var showRegisterPurchaseModal by remember { mutableStateOf(false) }

    val filteredProducts = state.inventoryProducts.filter { prod ->
        val matchesCategory = (selectedCategory == "Todos") || prod.category.contains(selectedCategory, ignoreCase = true)
        val matchesQuery = searchQuery.isBlank() || prod.name.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesQuery
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // 1. Key Metrics BENTO Ribbon (Valor Stock, Stock Bajo, Por Pagar)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Valor Stock
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = WarmSurfaceContainerLowest),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warehouse,
                                    contentDescription = null,
                                    tint = MarketEmerald,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = "VALOR STOCK",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = "$1,420",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "51.830 Bs",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    // Stock Bajo
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = AlertRedContainer),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = AlertRed,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = "STOCK BAJO",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnAlertRedContainer
                                )
                            }
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = "3",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = OnAlertRedContainer
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "alertas",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnAlertRedContainer
                                )
                            }
                        }
                    }

                    // Por Pagar
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SecondaryFixed),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Receipt,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = "POR PAGAR",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSecondaryFixed
                                )
                            }
                            Text(
                                text = "$185",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = OnSecondaryFixed
                            )
                            Text(
                                text = "2 Facturas",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }

            // 2. Registrar Compra Primary Action Button
            item {
                Button(
                    onClick = { showRegisterPurchaseModal = true },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MarketEmerald,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("btn_registrar_compra")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddBusiness,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "Registrar Compra de Mercancía",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // 3. Segmented View Tabs: Inventario vs Facturas & Proveedores
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WarmSurfaceContainer, RoundedCornerShape(14.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(
                        onClick = { selectedTab = 0 },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTab == 0) WarmSurfaceContainerLowest else Color.Transparent,
                            contentColor = if (selectedTab == 0) MarketEmerald else MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        elevation = if (selectedTab == 0) ButtonDefaults.buttonElevation(defaultElevation = 1.dp) else ButtonDefaults.buttonElevation(),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Inventory2,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Inventario (${state.inventoryProducts.size})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { selectedTab = 1 },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTab == 1) WarmSurfaceContainerLowest else Color.Transparent,
                            contentColor = if (selectedTab == 1) MarketEmerald else MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        elevation = if (selectedTab == 1) ButtonDefaults.buttonElevation(defaultElevation = 1.dp) else ButtonDefaults.buttonElevation(),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PendingActions,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Facturas & Proveedores",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(MarigoldContainer, CircleShape)
                        )
                    }
                }
            }

            if (selectedTab == 0) {
                // Search & Filter
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Buscar por nombre, código o peso...", fontSize = 13.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            },
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                        )

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(WarmSurfaceContainerLowest)
                                .clickable { /* barcode scan */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.QrCodeScanner,
                                contentDescription = "Scanner",
                                tint = MarketEmerald,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                // Category Chips
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val categories = listOf("Todos", "🧀 Charcutería", "🌾 Víveres", "☕ Bebidas & Café", "🍬 Snacks")
                        categories.forEach { cat ->
                            val isSel = (cat == "Todos" && selectedCategory == "Todos") || cat.contains(selectedCategory)
                            item {
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = if (isSel) MarketEmerald else WarmSurfaceContainerLowest,
                                    shadowElevation = 1.dp,
                                    modifier = Modifier.clickable {
                                        selectedCategory = if (cat == "Todos") "Todos" else cat.replace("🧀 ", "").replace("🌾 ", "").replace("☕ ", "").replace("🍬 ", "")
                                    }
                                ) {
                                    Text(
                                        text = cat,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Product Items
                items(filteredProducts) { prod ->
                    InventoryProductRow(product = prod)
                }
            } else {
                // Invoices Section
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cuentas por Pagar (Fiao de Proveedor)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Box(
                            modifier = Modifier
                                .background(SecondaryFixed, RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "$185.00 Total",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSecondaryFixed
                            )
                        }
                    }
                }

                items(state.supplierInvoices) { inv ->
                    SupplierInvoiceCard(invoice = inv)
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

    if (showRegisterPurchaseModal) {
        AlertDialog(
            onDismissRequest = { showRegisterPurchaseModal = false },
            title = {
                Text(
                    text = "Registrar Compra de Mercancía",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = "Distribuidora Los Andes",
                        onValueChange = {},
                        label = { Text("Proveedor") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = "F-8813",
                        onValueChange = {},
                        label = { Text("N° Factura") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = "120.00",
                        onValueChange = {},
                        label = { Text("Total Factura ($ USD)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showRegisterPurchaseModal = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MarketEmerald)
                ) {
                    Text("Guardar e Ingresar al Inventario")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRegisterPurchaseModal = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun InventoryProductRow(product: InventoryProduct) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WarmSurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.size(54.dp)) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(WarmSurfaceContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Inventory2,
                                contentDescription = null,
                                tint = MarketEmerald.copy(alpha = 0.5f)
                            )
                        }
                        if (product.isPesable) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .background(MarigoldContainer)
                                    .padding(vertical = 1.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "PESABLE",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = product.subtitle,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                        if (product.unitType == UnitType.UNIT) {
                            Row(
                                modifier = Modifier.padding(top = 2.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "$${product.retailPriceUsd.format(2)} c/u",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MarketEmerald
                                )
                                Text(
                                    text = "Costo: $${product.costPriceUsd.format(2)}",
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }

                // Stock status badge and qty
                Column(horizontalAlignment = Alignment.End) {
                    if (product.isCritical) {
                        Box(
                            modifier = Modifier
                                .background(AlertRed, RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "¡Agotándose!",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    } else if (product.stockQty < product.minStockQty) {
                        Box(
                            modifier = Modifier
                                .background(AlertRedContainer, RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Bajo (Mín ${product.minStockQty.toInt()} kg)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnAlertRedContainer
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .background(EmeraldFixed, RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .background(MarketEmerald, CircleShape)
                            )
                            Text(
                                text = "Óptimo",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnEmeraldFixed
                            )
                        }
                    }

                    val qtyText = if (product.unitType == UnitType.WEIGHT_KG)
                        "${product.stockQty.format(2)} kg"
                    else
                        "${product.stockQty.toInt()} und"
                    Text(
                        text = qtyText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (product.isCritical) AlertRed else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Tiered Pricing Matrix for Pesables
            if (product.isPesable) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WarmSurfaceContainerLow, RoundedCornerShape(12.dp))
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column {
                        Text("Costo / kg", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                        Text("$${product.costPriceUsd.format(2)}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Text("Detalle / kg", fontSize = 10.sp, color = MarketEmerald)
                        Text("$${product.retailPriceUsd.format(2)}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MarketEmerald)
                    }
                    Column {
                        Text("Mayor (+5kg)", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                        Text("$${product.wholesalePriceUsd.format(2)}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Critical item supplier quick reorder
            if (product.isCritical) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Proveedor: ${product.supplier}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(
                        onClick = { /* Reorder */ },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryFixed,
                            contentColor = OnSecondaryFixed
                        ),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Pedir a Proveedor", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SupplierInvoiceCard(invoice: SupplierInvoice) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WarmSurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SecondaryFixed),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalShipping,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = invoice.supplierName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Box(
                                modifier = Modifier
                                    .background(WarmSurfaceContainer, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = invoice.invoiceNumber,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Text(
                            text = invoice.categoryDescription,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .background(MarigoldContainer, RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "A Crédito",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // Details Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WarmSurfaceContainerLow, RoundedCornerShape(12.dp))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Vence", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Event,
                            contentDescription = null,
                            tint = AlertRed,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = invoice.dueDateText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AlertRed
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("Saldo Pendiente", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                    Text(
                        text = "$${invoice.pendingBalanceUsd.format(2)} USD",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* Abonar */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MarketEmerald,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Payments,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Abonar / Pagar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { /* Invoice receipt */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WarmSurfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.size(width = 44.dp, height = 40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
