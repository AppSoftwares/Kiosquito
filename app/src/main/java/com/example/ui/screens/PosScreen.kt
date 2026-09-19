package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
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
import coil.compose.AsyncImage
import com.example.model.CartItem
import com.example.model.PricingMode
import com.example.model.UnitType
import com.example.ui.theme.MarigoldContainer
import com.example.ui.theme.MarketEmerald
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.WarmSurfaceContainer
import com.example.ui.theme.WarmSurfaceContainerLow
import com.example.ui.theme.WarmSurfaceContainerLowest
import com.example.viewmodel.AppUiState
import com.example.viewmodel.KiosquitoViewModel

@Composable
fun PosScreen(
    state: AppUiState,
    viewModel: KiosquitoViewModel,
    onContinueToCobro: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showClientDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<CartItem?>(null) }
    var newClientInput by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val totalUsd = viewModel.getCartTotalUsd().toDouble()
    val totalBs = totalUsd * state.exchangeRateUsdToBs.toDouble()

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

            // 1. Selector de Tarifa (Detalle vs Mayoreo) y Cliente
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmSurfaceContainerLowest),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Switch Segmentado Detalle / Mayor
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(WarmSurfaceContainerLow, RoundedCornerShape(12.dp))
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val isDetalle = state.pricingMode == PricingMode.DETALLE
                            Button(
                                onClick = { viewModel.setPricingMode(PricingMode.DETALLE) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isDetalle) MarketEmerald else Color.Transparent,
                                    contentColor = if (isDetalle) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)
                                    .testTag("btn_precio_detalle")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Storefront,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Precio Detalle", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = { viewModel.setPricingMode(PricingMode.MAYOREO) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (!isDetalle) MaterialTheme.colorScheme.secondary else Color.Transparent,
                                    contentColor = if (!isDetalle) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)
                                    .testTag("btn_precio_mayoreo")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Inventory,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Precio Mayoreo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Client Selector Strip
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(SecondaryFixed),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "CLIENTE DE LA VENTA",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        letterSpacing = 0.5.sp
                                    )
                                    Text(
                                        text = state.currentCustomerName,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            Button(
                                onClick = { showClientDialog = true },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = WarmSurfaceContainer,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier
                                    .height(34.dp)
                                    .testTag("btn_cambiar_cliente")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Cambiar Cliente",
                                    tint = MarketEmerald,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Cambiar", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            // 2. Buscador Táctil y Botón Escáner
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar Harina, Arroz, Refresco...", fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Limpiar",
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        },
                        shape = RoundedCornerShape(18.dp),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("input_buscar_producto")
                    )

                    Button(
                        onClick = { /* trigger camera scan */ },
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MarigoldContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        modifier = Modifier
                            .height(52.dp)
                            .testTag("btn_escanear_codigo")
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = "Escanear",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Escanear", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // 3. Category Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        ChipItem(label = "Canasta (${state.cartItems.size})", isSelected = true)
                    }
                    item {
                        ChipItem(label = "A Granel (Peso)", icon = Icons.Default.Scale)
                    }
                    item {
                        ChipItem(label = "Charcutería")
                    }
                    item {
                        ChipItem(label = "Víveres")
                    }
                    item {
                        ChipItem(label = "Bebidas & Café")
                    }
                }
            }

            // 4. Cart Product Items
            items(state.cartItems) { item ->
                CartProductCard(
                    item = item,
                    pricingMode = state.pricingMode,
                    exchangeRate = state.exchangeRateUsdToBs.toDouble(),
                    onWeightChange = { weight -> viewModel.updateCartWeight(item.id, weight) },
                    onQuantityChange = { delta -> viewModel.updateCartQuantity(item.id, delta) },
                    onDelete = { itemToDelete = item }
                )
            }

            // 5. Quick Actions: Artículo Vario & Descuento Manual
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { /* Articulo vario */ },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WarmSurfaceContainerLow,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null,
                            tint = MarketEmerald,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Artículo Vario ($)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Button(
                        onClick = { /* Descuento manual */ },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WarmSurfaceContainerLow,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Percent,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Descuento Manual", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }
        }

        // Bottom Sticky Totals and Checkout Button
        Surface(
            color = WarmSurfaceContainerLowest,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            shadowElevation = 12.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Totals Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WarmSurfaceContainerLow, RoundedCornerShape(16.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MarketEmerald.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null,
                                tint = MarketEmerald,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "${state.cartItems.size} ARTÍCULOS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Total Calculado",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "USD ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MarketEmerald
                            )
                            Text(
                                text = "$${String.format("%.2f", totalUsd)}",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MarketEmerald,
                                letterSpacing = (-0.5).sp
                            )
                        }
                        Text(
                            text = "${String.format("%.2f", totalBs)} Bs",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                // Primary Checkout Button
                Button(
                    onClick = { onContinueToCobro() },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MarketEmerald,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("btn_continuar_a_cobrar")
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
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Payments,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "Continuar a Cobrar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$${String.format("%.2f", totalUsd)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Delete Confirmation Dialog
    if (itemToDelete != null) {
        AlertDialog(
            onDismissRequest = { itemToDelete = null },
            icon = { Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
            title = { Text("¿Quitar producto?") },
            text = { Text("¿Estás seguro de que deseas eliminar ${itemToDelete?.name} de la canasta?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.removeCartItem(itemToDelete!!.id)
                    itemToDelete = null
                }) {
                    Text("Sí, eliminar", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { itemToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Client Selection Dialog
    if (showClientDialog) {
        AlertDialog(
            onDismissRequest = { showClientDialog = false },
            title = {
                Text(
                    text = "Seleccionar Cliente",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = newClientInput,
                        onValueChange = { newClientInput = it },
                        label = { Text("Nombre o apodo del cliente") },
                        placeholder = { Text("Ej: Doña Carmen, Don Pedro...") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Clientes frecuentes de la bodega:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    listOf(
                        "Doña Carmen (Pensión)",
                        "Sr. Ramón (Mecánico)",
                        "Profesor Alberto Soto",
                        "Consumidor Final (Contado)"
                    ).forEach { client ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    viewModel.setCustomerName(client)
                                    showClientDialog = false
                                }
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MarketEmerald,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = client, fontSize = 14.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newClientInput.isNotBlank()) {
                            viewModel.setCustomerName(newClientInput.trim())
                        }
                        showClientDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MarketEmerald)
                ) {
                    Text("Asignar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClientDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun CartProductCard(
    item: CartItem,
    pricingMode: PricingMode,
    exchangeRate: Double,
    onWeightChange: (Int) -> Unit,
    onQuantityChange: (Int) -> Unit,
    onDelete: () -> Unit
) {
    val itemTotalUsd = item.getEffectivePrice(pricingMode)
    val itemTotalBs = itemTotalUsd * exchangeRate

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
            // Header: Thumbnail + Name/Category + Delete
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(WarmSurfaceContainer)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val badgeText = if (item.unitType == UnitType.WEIGHT_KG) "POR PESO" else "UNIDAD"
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (item.unitType == UnitType.WEIGHT_KG) SecondaryFixed else WarmSurfaceContainer,
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = badgeText,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            val unitPrice = if (pricingMode == PricingMode.DETALLE) item.retailPriceUsd else item.wholesalePriceUsd
                            Text(
                                text = "$${String.format("%.2f", unitPrice)} ${if (item.unitType == UnitType.WEIGHT_KG) "/ kg" else "c/u"}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = item.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = item.subtitle,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(32.dp)
                        .background(WarmSurfaceContainerLow, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Weight selector (for Pesables)
            if (item.unitType == UnitType.WEIGHT_KG) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WarmSurfaceContainerLow, RoundedCornerShape(12.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Scale,
                                contentDescription = null,
                                tint = MarketEmerald,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = "PESO ACTUAL:",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = "${item.weightGrams} gramos",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MarketEmerald
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val weights = listOf(100 to "100g", 250 to "250g", 500 to "1/2 kg", 1000 to "1 kg")
                        weights.forEach { (grams, label) ->
                            val isSelected = item.weightGrams == grams
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) MarigoldContainer else WarmSurfaceContainerLowest)
                                    .clickable { onWeightChange(grams) }
                                    .padding(vertical = 5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            // Bottom controls: Plus/Minus or grams + total item price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.unitType == UnitType.UNIT) {
                    Row(
                        modifier = Modifier
                            .background(WarmSurfaceContainerLow, RoundedCornerShape(12.dp))
                            .padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(WarmSurfaceContainerLowest)
                                .clickable { onQuantityChange(-1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("-", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = "${item.quantityUnits}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(WarmSurfaceContainerLowest)
                                .clickable { onQuantityChange(1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("+", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(WarmSurfaceContainer)
                                .clickable { onWeightChange((item.weightGrams - 50).coerceAtLeast(50)) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("-", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = "${item.weightGrams} g",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(WarmSurfaceContainer)
                                .clickable { onWeightChange(item.weightGrams + 50) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("+", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Price
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$${String.format("%.2f", itemTotalUsd)}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MarketEmerald
                    )
                    Text(
                        text = "${String.format("%.2f", itemTotalBs)} Bs",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ChipItem(
    label: String,
    isSelected: Boolean = false,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (isSelected) MarketEmerald else WarmSurfaceContainerLowest,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) Color.White else MarketEmerald,
                    modifier = Modifier.size(14.dp)
                )
            }
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
