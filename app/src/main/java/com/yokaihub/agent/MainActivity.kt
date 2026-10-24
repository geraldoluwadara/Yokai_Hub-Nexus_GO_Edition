// Copyright (c) 2026 ELG-GNP PRIDE TECHNOLOGY (@EDC-ETERNAL LEGACY GROUP). All Rights Reserved.
package com.yokaihub.agent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.SettingsBrightness
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yokaihub.agent.ui.components.YokaiBrandLogo
import com.yokaihub.agent.ui.components.YokaiBrandWordmark
import com.yokaihub.agent.ui.components.rememberDynamicDrawableResId
import com.yokaihub.agent.ui.screens.splash.YokaiSplashScreen
import com.yokaihub.agent.ui.theme.AppearanceMode
import com.yokaihub.agent.ui.theme.LocalYokaiColors
import com.yokaihub.agent.ui.theme.YokaiHubTheme
import com.yokaihub.agent.ui.theme.YokaiThemeVariant

/**
 * Main Entry Point for YOKAI HUB (Nexus GO Edition).
 *
 * Strictly contains NO bottom navigation bar (`NavigationBar`).
 * Launches directly into Interface #1 (`YokaiSplashScreen`) and transitions smoothly via `Crossfade`
 * into the Stage 1 Workspace verification container (`YokaiChatScreen` foundation + live 3-Theme &
 * Day/Night switcher + Replay Boot Splash trigger).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentThemeVariant by rememberSaveable {
                mutableStateOf(YokaiThemeVariant.CYBER_SHRINE)
            }
            var currentAppearanceMode by rememberSaveable {
                mutableStateOf(AppearanceMode.DARK)
            }
            var showBootSplash by rememberSaveable {
                mutableStateOf(true)
            }

            YokaiHubTheme(
                themeVariant = currentThemeVariant,
                appearanceMode = currentAppearanceMode
            ) {
                val palette = LocalYokaiColors.current

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = palette.background
                ) {
                    Crossfade(
                        targetState = showBootSplash,
                        animationSpec = tween(durationMillis = 650),
                        label = "SplashToWorkspaceCrossfade"
                    ) { isSplashActive ->
                        if (isSplashActive) {
                            YokaiSplashScreen(
                                awakeningDurationMs = 15_000L,
                                settleDurationMs = 7_000L,
                                onSplashCompleted = {
                                    showBootSplash = false
                                }
                            )
                        } else {
                            StageOneWorkspacePreviewContainer(
                                activeTheme = currentThemeVariant,
                                onThemeSelected = { currentThemeVariant = it },
                                activeAppearance = currentAppearanceMode,
                                onAppearanceSelected = { currentAppearanceMode = it },
                                onReplaySplash = { showBootSplash = true }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Stage 1 Verification & Theme Engine Preview Container for `YokaiChatScreen` (Zero Bottom Navigation Bar).
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StageOneWorkspacePreviewContainer(
    activeTheme: YokaiThemeVariant,
    onThemeSelected: (YokaiThemeVariant) -> Unit,
    activeAppearance: AppearanceMode,
    onAppearanceSelected: (AppearanceMode) -> Unit,
    onReplaySplash: () -> Unit
) {
    val palette = LocalYokaiColors.current
    val customDrawableId = rememberDynamicDrawableResId(
        listOf(
            "yokai_logo_${activeTheme.logoSlug}_transparent",
            "yokai_logo_${activeTheme.logoSlug}",
            "yokai_logo_transparent",
            "yokai_logo"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.background)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Header Lockup
        YokaiBrandLogo(
            size = 96.dp,
            animatedPulse = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        YokaiBrandWordmark()

        Spacer(modifier = Modifier.height(24.dp))

        // Replay Splash Screen Action Button
        Button(
            onClick = onReplaySplash,
            colors = ButtonDefaults.buttonColors(
                containerColor = palette.primaryAccent,
                contentColor = palette.onPrimaryAccent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Outlined.Refresh,
                contentDescription = "Replay Boot Splash",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "REPLAY BOOT SPLASH (15s + 7s)",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Card 1: 3 Japan Yokai Themes Selector
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = palette.card),
            border = BorderStroke(1.dp, palette.border)
        ) {
            Column(
                modifier = Modifier.padding(18.dp)
            ) {
                Text(
                    text = "JAPAN YOKAI 3-THEME ENGINE",
                    color = palette.primaryAccent,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "90% Neutral Minimalist Restraint • 10% Purposeful Yokai Accent",
                    color = palette.secondaryText,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(14.dp))

                YokaiThemeVariant.entries.forEach { variant ->
                    val isSelected = variant == activeTheme
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) palette.surface else Color.Transparent)
                            .border(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) palette.primaryAccent else palette.border,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { onThemeSelected(variant) }
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = variant.displayName,
                                    color = palette.primaryText,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = variant.japaneseTitle,
                                    color = palette.primaryAccent,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = variant.description,
                                color = palette.secondaryText,
                                fontSize = 12.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) palette.primaryAccent else palette.border
                                )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 2: Day / Night / System Mode Switcher
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = palette.card),
            border = BorderStroke(1.dp, palette.border)
        ) {
            Column(
                modifier = Modifier.padding(18.dp)
            ) {
                Text(
                    text = "APPEARANCE MODE (DAY / NIGHT)",
                    color = palette.primaryAccent,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AppearanceMode.entries.forEach { mode ->
                        val selected = mode == activeAppearance
                        val icon = when (mode) {
                            AppearanceMode.SYSTEM -> Icons.Outlined.SettingsBrightness
                            AppearanceMode.LIGHT -> Icons.Outlined.LightMode
                            AppearanceMode.DARK -> Icons.Outlined.DarkMode
                        }
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (selected) palette.primaryAccent else palette.surface
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (selected) palette.primaryAccent else palette.border,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { onAppearanceSelected(mode) }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = mode.displayName,
                                tint = if (selected) palette.onPrimaryAccent else palette.primaryText,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = mode.displayName,
                                color = if (selected) palette.onPrimaryAccent else palette.primaryText,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 3: Stage 1 & Logo Drawable Asset Status Inspector
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = palette.card),
            border = BorderStroke(1.dp, palette.border)
        ) {
            Column(
                modifier = Modifier.padding(18.dp)
            ) {
                Text(
                    text = "STAGE 1 DIAGNOSTICS & LOGO ASSET STATUS",
                    color = palette.secondaryAccent,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.2.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (customDrawableId != 0) {
                        "• Active Logo Renderer: Uploaded PNG Drawable (resId=$customDrawableId)"
                    } else {
                        "• Active Logo Renderer: 6-Layer Jetpack Compose Canvas Fallback (Waiting for PNG migration to res/drawable/ in Stage 2)"
                    },
                    color = palette.primaryText,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                HorizontalDivider(color = palette.border)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• Navigation Architecture: Zero Bottom Navigation Bar (Chat-First Workspace Container Ready for Stage 2)",
                    color = palette.secondaryText,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp
                )
            }
        }
    }
}
