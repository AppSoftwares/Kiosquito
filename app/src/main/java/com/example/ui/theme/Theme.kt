package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = EmeraldFixed,
    onPrimary = OnEmeraldFixed,
    primaryContainer = MarketEmerald,
    onPrimaryContainer = OnMarketEmeraldContainer,
    secondary = SecondaryFixedDim,
    onSecondary = OnSecondaryFixed,
    secondaryContainer = MarigoldContainer,
    onSecondaryContainer = OnMarigoldContainer,
    tertiary = TertiaryFixed,
    onTertiary = OnTertiaryFixed,
    background = BodegaDarkSlate,
    onBackground = WarmSurface,
    surface = Color(0xFF192231),
    onSurface = Color(0xFFECF1FF),
    surfaceVariant = Color(0xFF263143),
    onSurfaceVariant = BodegaOutlineVariant,
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF93000A),
  )

private val LightColorScheme =
  lightColorScheme(
    primary = MarketEmerald,
    onPrimary = OnMarketEmerald,
    primaryContainer = MarketEmeraldContainer,
    onPrimaryContainer = OnMarketEmeraldContainer,
    secondary = MarigoldGold,
    onSecondary = Color.White,
    secondaryContainer = MarigoldContainer,
    onSecondaryContainer = OnMarigoldContainer,
    tertiary = WholesaleViolet,
    onTertiary = Color.White,
    tertiaryContainer = WholesaleContainer,
    onTertiaryContainer = Color(0xFFFFFBFB),
    background = WarmSurface,
    onBackground = BodegaDarkSlate,
    surface = WarmSurface,
    onSurface = BodegaDarkSlate,
    surfaceVariant = WarmSurfaceContainerHighest,
    onSurfaceVariant = BodegaMutedSlate,
    outline = BodegaOutline,
    outlineVariant = BodegaOutlineVariant,
    error = AlertRed,
    errorContainer = AlertRedContainer,
    onErrorContainer = OnAlertRedContainer,
  )


@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
