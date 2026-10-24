// Copyright (c) 2026 ELG-GNP PRIDE TECHNOLOGY (@EDC-ETERNAL LEGACY GROUP). All Rights Reserved.
package com.yokaihub.agent.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

/**
 * Controls Day (Light), Night (Dark), or System-synced appearance across all 3 Yokai Themes.
 */
enum class AppearanceMode(val displayName: String) {
    SYSTEM("System Auto"),
    LIGHT("Day (Light)"),
    DARK("Night (Dark)")
}

/**
 * The 3 Switchable Japan Yokai Minimalist Theme Variants.
 * Follows strict 90% neutral background/surface/text restraint and 10% purposeful accent discipline.
 */
enum class YokaiThemeVariant(
    val displayName: String,
    val japaneseTitle: String,
    val description: String,
    val logoSlug: String
) {
    CYBER_SHRINE(
        displayName = "Cyber Shrine",
        japaneseTitle = "電脳神社",
        description = "Shrine Obsidian & Kitsune Cyan with Torii Crimson alerts",
        logoSlug = "cyber_shrine"
    ),
    YIN_YANG(
        displayName = "Yin-Yang Monochrome",
        japaneseTitle = "陰陽道",
        description = "Pure Sumi Ink & Spirit White high-contrast duality",
        logoSlug = "yin_yang"
    ),
    BLOOD_MOON_TORII(
        displayName = "Blood Moon Torii",
        japaneseTitle = "血月鳥居",
        description = "Charcoal Ash & Warm Washi with Torii Vermilion & Kitsune Gold",
        logoSlug = "blood_moon"
    )
}

/**
 * Semantic color tokens for YOKAI HUB (Nexus GO Edition).
 */
@Immutable
data class YokaiColorPalette(
    val variant: YokaiThemeVariant,
    val isDark: Boolean,
    val background: Color,
    val surface: Color,
    val card: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val primaryAccent: Color,
    val onPrimaryAccent: Color,
    val secondaryAccent: Color,
    val onSecondaryAccent: Color,
    val border: Color
)

// ============================================================================
// 1. CYBER_SHRINE (Default Japan Yokai Theme)
// ============================================================================
val CyberShrineDarkPalette = YokaiColorPalette(
    variant = YokaiThemeVariant.CYBER_SHRINE,
    isDark = true,
    background = Color(0xFF0A0B0E),      // Shrine Obsidian
    surface = Color(0xFF12141A),         // Deep Shrine Surface
    card = Color(0xFF181B22),            // Enclave Card
    primaryText = Color(0xFFF3F4F6),     // Crisp Washi White
    secondaryText = Color(0xFF9CA3AF),   // Muted Stone Gray
    primaryAccent = Color(0xFF00E5FF),   // Kitsune Cyan
    onPrimaryAccent = Color(0xFF0A0B0E),
    secondaryAccent = Color(0xFFFF2A54), // Torii Crimson
    onSecondaryAccent = Color(0xFFFFFFFF),
    border = Color(0xFF262936)           // Subtle Shrine Hairline
)

val CyberShrineLightPalette = YokaiColorPalette(
    variant = YokaiThemeVariant.CYBER_SHRINE,
    isDark = false,
    background = Color(0xFFF9FAFB),      // Shrine Washi
    surface = Color(0xFFFFFFFF),         // Pure Surface
    card = Color(0xFFF3F4F6),            // Soft Washi Card
    primaryText = Color(0xFF111827),     // Deep Sumi Text
    secondaryText = Color(0xFF4B5563),   // Balanced Slate Text
    primaryAccent = Color(0xFF007A8C),   // Deep Kitsune Cyan
    onPrimaryAccent = Color(0xFFFFFFFF),
    secondaryAccent = Color(0xFFD91E41), // Deep Torii Crimson
    onSecondaryAccent = Color(0xFFFFFFFF),
    border = Color(0xFFE5E7EB)           // Crisp Light Hairline
)

// ============================================================================
// 2. YIN_YANG (Monochrome Black & White Yokai Theme)
// ============================================================================
val YinYangDarkPalette = YokaiColorPalette(
    variant = YokaiThemeVariant.YIN_YANG,
    isDark = true,
    background = Color(0xFF000000),      // Pitch Sumi Ink
    surface = Color(0xFF0D0D0D),         // Obsidian Ink Surface
    card = Color(0xFF141414),            // Monochrome Card
    primaryText = Color(0xFFFFFFFF),     // Pure Yang White
    secondaryText = Color(0xFFA1A1AA),   // Ash Silver
    primaryAccent = Color(0xFFFFFFFF),   // Pure Yang White
    onPrimaryAccent = Color(0xFF000000), // High-contrast Black on White
    secondaryAccent = Color(0xFFE4E4E7), // Spirit Silver
    onSecondaryAccent = Color(0xFF000000),
    border = Color(0xFF27272A)           // Graphite Hairline
)

val YinYangLightPalette = YokaiColorPalette(
    variant = YokaiThemeVariant.YIN_YANG,
    isDark = false,
    background = Color(0xFFFFFFFF),      // Pure Spirit White
    surface = Color(0xFFF8F8F8),         // Alabaster Surface
    card = Color(0xFFF1F1F1),            // Paper Card
    primaryText = Color(0xFF000000),     // Pure Yin Black
    secondaryText = Color(0xFF52525B),   // Charcoal Gray
    primaryAccent = Color(0xFF000000),   // Pure Yin Black
    onPrimaryAccent = Color(0xFFFFFFFF), // High-contrast White on Black
    secondaryAccent = Color(0xFF3F3F46), // Deep Zinc
    onSecondaryAccent = Color(0xFFFFFFFF),
    border = Color(0xFFE4E4E7)           // Fine Silver Border
)

// ============================================================================
// 3. BLOOD_MOON_TORII (Kitsune Vermilion & Ash Theme)
// ============================================================================
val BloodMoonToriiDarkPalette = YokaiColorPalette(
    variant = YokaiThemeVariant.BLOOD_MOON_TORII,
    isDark = true,
    background = Color(0xFF0F0D0D),      // Charcoal Ash
    surface = Color(0xFF181515),         // Smoldering Temple Surface
    card = Color(0xFF211C1C),            // Dark Ember Card
    primaryText = Color(0xFFFAF8F5),     // Warm Washi White
    secondaryText = Color(0xFFA8A29E),   // Warm Stone Ash
    primaryAccent = Color(0xFFFF4D2D),   // Torii Vermilion
    onPrimaryAccent = Color(0xFF0F0D0D),
    secondaryAccent = Color(0xFFC5A059), // Kitsune Gold
    onSecondaryAccent = Color(0xFF0F0D0D),
    border = Color(0xFF2E2727)           // Warm Ash Hairline
)

val BloodMoonToriiLightPalette = YokaiColorPalette(
    variant = YokaiThemeVariant.BLOOD_MOON_TORII,
    isDark = false,
    background = Color(0xFFFAF8F5),      // Warm Washi Ivory
    surface = Color(0xFFFFFFFF),         // Pure Ivory Surface
    card = Color(0xFFF3EFEA),            // Parchment Card
    primaryText = Color(0xFF1C1917),     // Deep Espresso Ink
    secondaryText = Color(0xFF57534E),   // Umber Ash
    primaryAccent = Color(0xFFC83218),   // Shrine Vermilion
    onPrimaryAccent = Color(0xFFFFFFFF),
    secondaryAccent = Color(0xFF9A7B38), // Antique Kitsune Gold
    onSecondaryAccent = Color(0xFFFFFFFF),
    border = Color(0xFFE7E2DA)           // Warm Sand Border
)

/**
 * CompositionLocal providing instant access to the active [YokaiColorPalette] across all composables.
 */
val LocalYokaiColors = staticCompositionLocalOf { CyberShrineDarkPalette }

/**
 * Resolves the exact [YokaiColorPalette] for a given [YokaiThemeVariant] and dark/light state.
 */
fun resolveYokaiColorPalette(
    variant: YokaiThemeVariant,
    isDark: Boolean
): YokaiColorPalette {
    return when (variant) {
        YokaiThemeVariant.CYBER_SHRINE -> if (isDark) CyberShrineDarkPalette else CyberShrineLightPalette
        YokaiThemeVariant.YIN_YANG -> if (isDark) YinYangDarkPalette else YinYangLightPalette
        YokaiThemeVariant.BLOOD_MOON_TORII -> if (isDark) BloodMoonToriiDarkPalette else BloodMoonToriiLightPalette
    }
}

/**
 * Converts a [YokaiColorPalette] into a Material 3 [ColorScheme].
 */
private fun YokaiColorPalette.toMaterialColorScheme(): ColorScheme {
    return if (isDark) {
        darkColorScheme(
            primary = primaryAccent,
            onPrimary = onPrimaryAccent,
            primaryContainer = card,
            onPrimaryContainer = primaryText,
            secondary = secondaryAccent,
            onSecondary = onSecondaryAccent,
            secondaryContainer = card,
            onSecondaryContainer = primaryText,
            tertiary = secondaryAccent,
            background = background,
            onBackground = primaryText,
            surface = surface,
            onSurface = primaryText,
            surfaceVariant = card,
            onSurfaceVariant = secondaryText,
            outline = border,
            outlineVariant = border,
            error = secondaryAccent,
            onError = onSecondaryAccent
        )
    } else {
        lightColorScheme(
            primary = primaryAccent,
            onPrimary = onPrimaryAccent,
            primaryContainer = card,
            onPrimaryContainer = primaryText,
            secondary = secondaryAccent,
            onSecondary = onSecondaryAccent,
            secondaryContainer = card,
            onSecondaryContainer = primaryText,
            tertiary = secondaryAccent,
            background = background,
            onBackground = primaryText,
            surface = surface,
            onSurface = primaryText,
            surfaceVariant = card,
            onSurfaceVariant = secondaryText,
            outline = border,
            outlineVariant = border,
            error = secondaryAccent,
            onError = onSecondaryAccent
        )
    }
}

/**
 * Minimalist geometric typography tailored for YOKAI HUB (Nexus GO Edition).
 */
val YokaiTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 3.5.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 2.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.15.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.2.sp
    )
)

/**
 * Root Theme Engine for YOKAI HUB (Nexus GO Edition).
 */
@Composable
fun YokaiHubTheme(
    themeVariant: YokaiThemeVariant = YokaiThemeVariant.CYBER_SHRINE,
    appearanceMode: AppearanceMode = AppearanceMode.DARK,
    content: @Composable () -> Unit
) {
    val systemIsDark = isSystemInDarkTheme()
    val useDarkTheme = when (appearanceMode) {
        AppearanceMode.SYSTEM -> systemIsDark
        AppearanceMode.LIGHT -> false
        AppearanceMode.DARK -> true
    }

    val yokaiPalette = resolveYokaiColorPalette(
        variant = themeVariant,
        isDark = useDarkTheme
    )
    val materialColorScheme = yokaiPalette.toMaterialColorScheme()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            window.statusBarColor = yokaiPalette.background.toArgb()
            window.navigationBarColor = yokaiPalette.background.toArgb()
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !useDarkTheme
            insetsController.isAppearanceLightNavigationBars = !useDarkTheme
        }
    }

    CompositionLocalProvider(LocalYokaiColors provides yokaiPalette) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = YokaiTypography,
            content = content
        )
    }
}
