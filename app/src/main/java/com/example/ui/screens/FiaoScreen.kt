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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.TrustedCustomer
import com.example.ui.theme.AlertRed
import com.example.ui.theme.AlertRedContainer
import com.example.ui.theme.MarketEmerald
import com.example.ui.theme.OnAlertRedContainer
import com.example.ui.theme.OnSecondaryFixed
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.WarmSurfaceContainer
import com.example.ui.theme.WarmSurfaceContainerHigh
import com.example.ui.theme.WarmSurfaceContainerLow
import com.example.ui.theme.WarmSurfaceContainerLowest
import com.example.viewmodel.AppUiState
import com.example.viewmodel.KiosquitoViewModel

@Composable
fun FiaoScreen(
    state: AppUiState,
    viewModel: KiosquitoViewModel,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("all") } // all, pending, exceeded, settled
    var activeAbonoCustomer by remember { mutableStateOf<TrustedCustomer?>(null) }
    var abonoInputAmount by remember { mutableStateOf("") }
    var showNewCustomerDialog by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current
    val clipboardManager = LocalClipboardManager.current

    val totalDebtUsd = state.customers.sumOf { it.debtUsd.toDouble() }
    val totalDebtBs = totalDebtUsd * state.exchangeRateUsdToBs.toDouble()
    val exceededCount = state.customers.count { it.isExceeded }

    val filteredCustomers = state.customers.filter { cust ->
        val matchesFilter = when (selectedFilter) {
            "pending" -> cust.debtUsd.toDouble() > 0
            "exceeded" -> cust.isExceeded
            "settled" -> cust.debtUsd.toDouble() == 0.0
            else -> true
        }
        val matchesQuery = searchQuery.isBlank() ||
                cust.name.contains(searchQuery, ignoreCase = true) ||
                cust.aliasOrAddress.contains(searchQuery, ignoreCase = true)
        matchesFilter && matchesQuery
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

            // 1. Bento Metric Ribbon: Libreta Comunitaria
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmSurfaceContainerLow),
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
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = MarketEmerald,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "LIBRETA COMUNITARIA",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 0.5.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(WarmSurfaceContainerHigh, RoundedCornerShape(50))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "${state.customers.size} Vecinos fiao",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Column {
                                Text(
                                    text = "Total por cobrar",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = "$${String.format("%.2f", totalDebtUsd)}",
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        letterSpacing = (-0.5).sp
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "USD",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Payments,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "${String.format("%.2f", totalDebtBs)} Bs (Tasa: ${String.format("%.2f", state.exchangeRateUsdToBs)})",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }

                            if (exceededCount > 0) {
                                Row(
                                    modifier = Modifier
                                        .background(AlertRedContainer, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.NotificationImportant,
                                        contentDescription = null,
                                        tint = OnAlertRedContainer,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Text(
                                        text = "$exceededCount con límite excedido",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnAlertRedContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 2. Search & New Client Button
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar vecino por nombre o apodo...", fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                modifier = Modifier.size(18.dp)
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
                        shape = RoundedCornerShape(50),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("input_buscar_vecino")
                    )

                    Button(
                        onClick = { showNewCustomerDialog = true },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MarketEmerald,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .height(50.dp)
                            .testTag("btn_nuevo_vecino")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = "Nuevo Vecino",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Nuevo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // 3. Filter Pills
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        FilterPill(
                            label = "Todos (${state.customers.size})",
                            isSelected = selectedFilter == "all",
                            onClick = { selectedFilter = "all" }
                        )
                    }
                    item {
                        FilterPill(
                            label = "Con saldo (${state.customers.count { it.debtUsd > 0 }})",
                            isSelected = selectedFilter == "pending",
                            onClick = { selectedFilter = "pending" }
                        )
                    }
                    item {
                        FilterPill(
                            label = "Límite superado ($exceededCount)",
                            isSelected = selectedFilter == "exceeded",
                            onClick = { selectedFilter = "exceeded" }
                        )
                    }
                    item {
                        FilterPill(
                            label = "Al día (${state.customers.count { it.debtUsd == 0.0 }})",
                            isSelected = selectedFilter == "settled",
                            onClick = { selectedFilter = "settled" }
                        )
                    }
                }
            }

            // 4. Customer List
            items(filteredCustomers) { customer ->
                CustomerCard(
                    customer = customer,
                    exchangeRate = state.exchangeRateUsdToBs,
                    onRegistrarAbono = { activeAbonoCustomer = customer },
                    onSendWhatsApp = { /* Send friendly WhatsApp */ }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

    // Abono Bottom Sheet / Dialog
    if (activeAbonoCustomer != null) {
        val cust = activeAbonoCustomer!!
        val abonoAmount = abonoInputAmount.toDoubleOrNull() ?: 0.0
        val remainingDebt = (cust.debtUsd.toDouble() - abonoAmount).coerceAtLeast(0.0)

        AlertDialog(
            onDismissRequest = { activeAbonoCustomer = null },
            title = {
                Text(
                    text = "Abonar a cuenta de ${cust.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Deuda actual: $${String.format("%.2f", cust.debtUsd)} USD (${String.format("%.2f", cust.debtUsd.toDouble() * state.exchangeRateUsdToBs.toDouble())} Bs)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    OutlinedTextField(
                        value = abonoInputAmount,
                        onValueChange = { abonoInputAmount = it },
                        label = { Text("Monto a abonar ($ USD)") },
                        placeholder = { Text("Ej: 10.00") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nueva deuda: $${String.format("%.2f", remainingDebt)}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MarketEmerald
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = { abonoInputAmount = "5.00" },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = WarmSurfaceContainer),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("$5", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
                        }
                        Button(
                            onClick = { abonoInputAmount = "10.00" },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = WarmSurfaceContainer),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("$10", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
                        }
                        Button(
                            onClick = { abonoInputAmount = String.format("%.2f", cust.debtUsd) },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SecondaryFixed),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Total", color = OnSecondaryFixed, fontSize = 12.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = abonoInputAmount.toDoubleOrNull() ?: 0.0
                        if (amount > 0) {
                            viewModel.recordAbono(cust.id, java.math.BigDecimal(amount.toString()))
                        }
                        activeAbonoCustomer = null
                        abonoInputAmount = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MarketEmerald)
                ) {
                    Text("Confirmar Abono")
                }
            },
            dismissButton = {
                TextButton(onClick = { activeAbonoCustomer = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showNewCustomerDialog) {
        AlertDialog(
            onDismissRequest = { showNewCustomerDialog = false },
            title = { Text("Registrar Nuevo Cliente Confiable") },
            text = { Text("Ingresa los datos del nuevo vecino para abrirle libreta de fiao.") },
            confirmButton = {
                Button(
                    onClick = { showNewCustomerDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MarketEmerald)
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewCustomerDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun CustomerCard(
    customer: TrustedCustomer,
    exchangeRate: java.math.BigDecimal,
    onRegistrarAbono: () -> Unit,
    onSendWhatsApp: () -> Unit
) {
    val debtBs = customer.debtUsd.multiply(exchangeRate)
    val progress = if (customer.creditLimitUsd.toDouble() > 0) (customer.debtUsd.toDouble() / customer.creditLimitUsd.toDouble()).toFloat().coerceIn(0f, 1f) else 0f

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
            // Top Row: Avatar + Name/Address + Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(46.dp)) {
                        AsyncImage(
                            model = customer.avatarUrl,
                            contentDescription = customer.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(WarmSurfaceContainer)
                        )
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(if (customer.isExceeded) AlertRed else MarketEmerald, CircleShape)
                                .align(Alignment.BottomEnd)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = customer.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = customer.aliasOrAddress,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (customer.isExceeded) {
                    Row(
                        modifier = Modifier
                            .background(AlertRedContainer, RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = OnAlertRedContainer,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "Límite superado",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnAlertRedContainer
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .background(WarmSurfaceContainer, RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(MarketEmerald, CircleShape)
                        )
                        Text(
                            text = "Al día",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Progress & Debt metrics strip
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (customer.isExceeded) AlertRedContainer.copy(alpha = 0.35f) else WarmSurfaceContainerLow,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (customer.isExceeded) "Deuda acumulada" else "Deuda activa",
                        fontSize = 11.sp,
                        color = if (customer.isExceeded) AlertRed else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (customer.isExceeded) FontWeight.Bold else FontWeight.Normal
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "$${String.format("%.2f", customer.debtUsd)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (customer.isExceeded) AlertRed else MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "/ ${String.format("%.2f", debtBs)} Bs",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(50)),
                    color = if (customer.isExceeded) AlertRed else MarketEmerald,
                    trackColor = WarmSurfaceContainerHigh
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Cupo: $${String.format("%.2f", customer.creditLimitUsd)} max",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    val available = (customer.creditLimitUsd - customer.debtUsd).coerceAtLeast(0.0)
                    Text(
                        text = if (customer.isExceeded) "Excedido por +$${String.format("%.2f", customer.debtUsd - customer.creditLimitUsd)}"
                        else "Disponible: $${String.format("%.2f", available)}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (customer.isExceeded) AlertRed else MarketEmerald
                    )
                }
            }

            // Historical note
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = MarketEmerald,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "Último abono: ${customer.lastPaymentText}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "${customer.pendingPurchasesCount} compras pendientes",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Action Buttons
            if (customer.isExceeded) {
                Button(
                    onClick = onSendWhatsApp,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MarketEmerald.copy(alpha = 0.15f),
                        contentColor = MarketEmerald
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Recordatorio amable por WhatsApp",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onRegistrarAbono,
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
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Registrar Abono", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { /* Ver Libreta */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WarmSurfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Ver Libreta", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun FilterPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) MarketEmerald else WarmSurfaceContainerHigh)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
