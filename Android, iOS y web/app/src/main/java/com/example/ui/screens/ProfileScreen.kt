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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.AlertRed
import com.example.ui.theme.AlertRedContainer
import com.example.ui.theme.EmeraldFixed
import com.example.ui.theme.MarigoldContainer
import com.example.ui.theme.MarketEmerald
import com.example.ui.theme.MarketEmeraldContainer
import com.example.ui.theme.OnAlertRedContainer
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.WarmSurfaceContainer
import com.example.ui.theme.WarmSurfaceContainerLowest
import com.example.viewmodel.AppUiState
import com.example.viewmodel.KiosquitoViewModel
import com.example.viewmodel.MainTab

@Composable
fun ProfileScreen(
    state: AppUiState,
    viewModel: KiosquitoViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showEditBodegaModal by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // Back button
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
                        Text("Volver", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // 1. Hero Profile Card (Emerald warm Latin-American vibe)
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MarketEmerald),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(MarketEmeraldContainer, MarketEmerald)
                                )
                            )
                            .padding(18.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Avatar with camera action
                            Box(contentAlignment = Alignment.BottomEnd) {
                                AsyncImage(
                                    model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAw867L85QpIkuAslsAGIRJZ5xkGpo9zlpFlCm2xdKwFI655Zwr5jW7hFNCWwNw82RSnD4lKng3tfz8nSPdcf7YpeDmaZm4jEb3o53VjRvL11gTRvTJEY_QVKLIyO1fZWlGweeAg905z2frhBoIYMe_mG8BsTnaj59AdkGD3EzFwx9kqwzhwQKXB7fwMQ8cL_5zj9dDCHDw3J2vwg13mkt4BAdbinqos6vL3xrqseG4lBbrsREPaWN3ng",
                                    contentDescription = "Marco Valderrama",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(76.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(MarigoldContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CameraAlt,
                                        contentDescription = "Cambiar Foto",
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }

                            // Info
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = state.storeOwner,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Verificado",
                                        tint = EmeraldFixed,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Text(
                                    text = "Dueño / Administrador",
                                    fontSize = 12.sp,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.padding(top = 2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = EmeraldFixed,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Text(
                                        text = "${state.storeName} • ${state.storeLocation}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = EmeraldFixed
                                    )
                                }
                            }

                            // Cloud Sync Pill
                            Row(
                                modifier = Modifier
                                    .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(50))
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(EmeraldFixed, CircleShape)
                                )
                                Text(
                                    text = "Kiosquito Cloud & Supabase al día",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // 2. Mi Negocio & Cuenta
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmSurfaceContainerLowest),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "MI NEGOCIO & CUENTA",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                        )

                        ProfileMenuItem(
                            icon = Icons.Default.Storefront,
                            iconTint = MarketEmerald,
                            title = "Perfil de la Bodega",
                            subtitle = "RIF, dirección, logo y sello fiscal",
                            tag = state.storeRif,
                            onClick = { showEditBodegaModal = true }
                        )

                        ProfileMenuItem(
                            icon = Icons.Default.ManageAccounts,
                            iconTint = MaterialTheme.colorScheme.tertiary,
                            title = "Configuración de Cuenta",
                            subtitle = "Correo y cambio de contraseña",
                            onClick = {}
                        )

                        ProfileMenuItem(
                            icon = Icons.Default.Security,
                            iconTint = MaterialTheme.colorScheme.secondary,
                            title = "Privacidad y Seguridad",
                            subtitle = "Sesiones activas y claves de acceso",
                            onClick = {}
                        )

                        ProfileMenuItem(
                            icon = Icons.Default.Palette,
                            iconTint = MarketEmerald,
                            title = "Apariencia y Tema",
                            subtitle = "Modo Claro, idioma e interfaz",
                            tag = "Claro • ES",
                            onClick = {}
                        )

                        ProfileMenuItem(
                            icon = Icons.Default.NotificationsActive,
                            iconTint = MaterialTheme.colorScheme.tertiary,
                            title = "Notificaciones y Alertas",
                            subtitle = "Beep de caja, alertas de Fiao y ventas",
                            onClick = {}
                        )
                    }
                }
            }

            // 3. Soporte y Garantías
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmSurfaceContainerLowest),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "SOPORTE Y GARANTÍAS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                        )

                        ProfileMenuItem(
                            icon = Icons.Default.SupportAgent,
                            iconTint = MarketEmerald,
                            title = "Centro de Ayuda & Asistencia",
                            subtitle = "Guía rápida de cobro y WhatsApp directo",
                            onClick = {}
                        )

                        ProfileMenuItem(
                            icon = Icons.Default.Gavel,
                            iconTint = MaterialTheme.colorScheme.onSurfaceVariant,
                            title = "Términos y Condiciones",
                            subtitle = "Protección de datos fiscales y locales",
                            onClick = {}
                        )
                    }
                }
            }

            // 4. Logout Button
            item {
                Button(
                    onClick = { /* logout */ },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AlertRedContainer,
                        contentColor = OnAlertRedContainer
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Cerrar Sesión en esta Caja", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Kiosquito POS v2.4.1 (Build 2026-CARACAS)",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    if (showEditBodegaModal) {
        AlertDialog(
            onDismissRequest = { showEditBodegaModal = false },
            title = { Text("Perfil de la Bodega") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Nombre: ${state.storeName}", fontWeight = FontWeight.Bold)
                    Text("RIF: ${state.storeRif}")
                    Text("Dirección: ${state.storeLocation}")
                    Text("Teléfono de Caja: ${state.storePhone}")
                }
            },
            confirmButton = {
                Button(
                    onClick = { showEditBodegaModal = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MarketEmerald)
                ) {
                    Text("Listo")
                }
            }
        )
    }
}

@Composable
private fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    tag: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 6.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(WarmSurfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (tag != null) {
                Box(
                    modifier = Modifier
                        .background(WarmSurfaceContainer, RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = tag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MarketEmerald
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
