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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.PublicProduct
import com.example.ui.theme.AlertRed
import com.example.ui.theme.AlertRedContainer
import com.example.ui.theme.MarketEmerald
import com.example.ui.theme.OnAlertRedContainer
import com.example.ui.theme.WarmSurfaceContainer
import com.example.ui.theme.WarmSurfaceContainerLowest
import com.example.viewmodel.AppUiState
import com.example.viewmodel.KiosquitoViewModel

@Composable
fun CatalogAdminScreen(
    state: AppUiState,
    viewModel: KiosquitoViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddProductModal by remember { mutableStateOf(false) }
    
    // Form fields state
    var nameInput by remember { mutableStateOf("") }
    var categoryInput by remember { mutableStateOf("") }
    var descriptionInput by remember { mutableStateOf("") }
    var priceInput by remember { mutableStateOf("") }
    var imageUrlInput by remember { mutableStateOf("") }

    val uriHandler = LocalUriHandler.current
    val clipboardManager = LocalClipboardManager.current
    val catalogLink = "http://192.168.1.100:8080/web/catalog/index.html"

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

            // Back navigation row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onBack,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WarmSurfaceContainer,
                            contentColor = MarketEmerald
                        ),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Volver al Perfil", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Feature 2: "Share Catalog" Bento Card Ribbon
            item {
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
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Link,
                                contentDescription = null,
                                tint = MarketEmerald,
                                modifier = Modifier.size(18.dp)
                              )
                            Text(
                                text = "COMPARTIR CATÁLOGO PÚBLICO",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Text(
                            text = "Tus clientes pueden ver tus productos en tiempo real y hacer pedidos directamente desde la web.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // URL display container
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = WarmSurfaceContainer,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = catalogLink,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MarketEmerald,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(catalogLink))
                                        // Triggering a toast simulation via existing architecture or local mechanism
                                    },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copiar enlace",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        // WhatsApp Share Action Button
                        Button(
                            onClick = {
                                val baseMsg = "¡Hola! Te comparto nuestro catálogo actualizado de Bodega San Antonio 🏪 Consulta nuestros productos y haz tu pedido aquí: $catalogLink"
                                val encoded = baseMsg.replace(" ", "%20").replace("\n", "%0A").replace("*", "%2A")
                                uriHandler.openUri("https://wa.me/?text=$encoded")
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF25D366),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Compartir vía WhatsApp", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Feature 1: Public Catalog Management List Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Column {
                        Text(
                            text = "Productos en Catálogo",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${state.publicCatalogProducts.size} artículos visibles públicamente",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    Button(
                        onClick = {
                            nameInput = ""
                            categoryInput = ""
                            descriptionInput = ""
                            priceInput = ""
                            imageUrlInput = ""
                            showAddProductModal = true
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MarketEmerald)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Agregar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Product Items List
            if (state.publicCatalogProducts.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = WarmSurfaceContainerLowest),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay productos en el catálogo público.",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            } else {
                items(state.publicCatalogProducts) { prod ->
                    PublicProductRow(
                        product = prod,
                        onDeleteClick = { viewModel.deletePublicProduct(prod.id) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

    // Modal to Add New Product to Public Catalog
    if (showAddProductModal) {
        AlertDialog(
            onDismissRequest = { showAddProductModal = false },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Storefront, contentDescription = null, tint = MarketEmerald)
                    Text("Nuevo Producto Público", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Nombre del Producto") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = categoryInput,
                        onValueChange = { categoryInput = it },
                        label = { Text("Categoría (ej: Víveres, Charcutería)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = descriptionInput,
                        onValueChange = { descriptionInput = it },
                        label = { Text("Descripción / Detalle público") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = priceInput,
                        onValueChange = { priceInput = it },
                        label = { Text("Precio Ref (USD)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = imageUrlInput,
                        onValueChange = { imageUrlInput = it },
                        label = { Text("URL de la Imagen") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val price = priceInput.toDoubleOrNull() ?: 0.0
                        viewModel.addPublicProduct(
                            name = nameInput,
                            category = categoryInput.ifBlank { "General" },
                            description = descriptionInput,
                            priceUsd = price,
                            imageUrl = imageUrlInput.ifBlank { "https://lh3.googleusercontent.com/aida-public/AB6AXuCZR0bBDbOzE-PeKm88w4Kg1cTW1zguQDmCmm2Ri-VrXFUCXMtgzgFtbaaeVmz-O6sR2gk4OoVs5msDdIdGPEoh8PXpRcL0klEXBOoLqnSgwOAAYqoFYN0phRHfh55Fc--BcSzH-nwvvmVp-iNVWaRdriJbJAg2Gd9uYVL2cwowheGnPSbN8R3nlhXf-MAXvsRnWMmcr2l2c7xvZMlBN647cRbmS9JP3aFd4CbQXkFROsOAHecsbFEPGA" }
                        )
                        showAddProductModal = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MarketEmerald)
                ) {
                    Text("Guardar y Publicar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddProductModal = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun PublicProductRow(
    product: PublicProduct,
    onDeleteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = WarmSurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
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
                    Text(
                        text = product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Box(
                        modifier = Modifier
                            .background(WarmSurfaceContainer, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = product.category,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = MarketEmerald
                        )
                    }
                }
                Text(
                    text = product.description,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                Text(
                    text = "$${String.format("%.2f", product.priceUsd)} USD",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MarketEmerald,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .background(AlertRedContainer, RoundedCornerShape(10.dp))
                    .size(34.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar del catálogo",
                    tint = AlertRed,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
