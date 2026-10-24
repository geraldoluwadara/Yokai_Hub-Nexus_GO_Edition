// Copyright (c) 2026 ELG-GNP PRIDE TECHNOLOGY (@EDC-ETERNAL LEGACY GROUP). All Rights Reserved.
package com.yokaihub.agent.ui.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yokaihub.agent.ui.components.YokaiBrandLockup
import com.yokaihub.agent.ui.components.YokaiBrandLogo
import com.yokaihub.agent.ui.theme.LocalYokaiColors
import com.yokaihub.agent.ui.theme.YokaiColorPalette
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val BOOT_MILESTONES = listOf(
    "Awakening Yokai Spirit Core...",
    "Mounting Encrypted BYOK Vault...",
    "Calibrating AgentRouter Protocol Adapters...",
    "Binding Custom MCP Server Bridges...",
    "Synchronizing Cyber-Shrine Workspace..."
)

/**
 * Interface #1: "THE PHANTOM FORGE" (Ghost Meets Production Tools) Boot-Up & Splash Screen.
 *
 * Implements the strict Two-Phase Boot Choreography:
 * - **Phase 1: Kinetic Yokai Awakening** (`awakeningDurationMs` = 15,000ms default, configurable 10–30s):
 *   1. Outer clockwise Production Tool Blueprint Ring etched with 4 cardinal developer tool micro-glyphs:
 *      Code Brackets `{ }`, Terminal Prompt `>_`, Git Branch Fork Node, and Cryptographic Key Teeth.
 *   2. Inner counter-rotating Onmyōdō Yin-Yang & Torii sacred geometry seal.
 *   3. 8 Hitodama (spirit flame) particles orbiting and spiraling inward into the pulsing `YokaiBrandLogo`.
 *   4. Authentic 3-second system milestone ticker with a 1.5.dp minimalist linear progress indicator.
 *   5. Discreet `"Tap anywhere to skip"` hint at the bottom.
 *
 * - **Phase 2: Settled Brand Lockup** (`settleDurationMs` = 7,000ms default, configurable 5–10s):
 *   All kinetic rings, particles, tickers, and hints fade smoothly to 0f alpha, leaving ONLY the settled
 *   `YOKAI HUB` logo, bold title, and `妖怪ハブ • Autonomous AI & MCP Workspace` subtitle in pure stillness
 *   before automatically invoking [onSplashCompleted].
 *
 * - **Instant 1-Tap Dismiss:**
 *   Tapping anywhere on the screen during Phase 1 or Phase 2 immediately triggers [onSplashCompleted].
 */
@Composable
fun YokaiSplashScreen(
    awakeningDurationMs: Long = 15_000L, // Configurable 10_000L..30_000L
    settleDurationMs: Long = 7_000L,     // Configurable 5_000L..10_000L
    onSplashCompleted: () -> Unit
) {
    val palette = LocalYokaiColors.current
    val coercedAwakeningMs = awakeningDurationMs.coerceIn(10_000L, 30_000L)
    val coercedSettleMs = settleDurationMs.coerceIn(5_000L, 10_000L)

    var isSettledPhase by remember { mutableStateOf(false) }
    var currentMilestoneIndex by remember { mutableIntStateOf(0) }
    var awakeningProgress by remember { mutableFloatStateOf(0f) }

    // Two-Phase Boot Timer Coroutine
    LaunchedEffect(coercedAwakeningMs, coercedSettleMs) {
        val tickIntervalMs = 50L
        var elapsedMs = 0L
        while (elapsedMs < coercedAwakeningMs) {
            delay(tickIntervalMs)
            elapsedMs += tickIntervalMs
            awakeningProgress = (elapsedMs.toFloat() / coercedAwakeningMs.toFloat()).coerceIn(0f, 1f)
            val stepDuration = (coercedAwakeningMs / BOOT_MILESTONES.size).coerceAtLeast(1L)
            currentMilestoneIndex = ((elapsedMs / stepDuration).toInt())
                .coerceIn(0, BOOT_MILESTONES.lastIndex)
        }
        // Enter Phase 2: Settled Brand Lockup (7 seconds default)
        isSettledPhase = true
        delay(coercedSettleMs)
        onSplashCompleted()
    }

    // Smooth fade out of kinetic Phase 1 elements when entering Phase 2
    val kineticAlpha by animateFloatAsState(
        targetValue = if (isSettledPhase) 0f else 1f,
        animationSpec = tween(durationMillis = 900),
        label = "kineticAlpha"
    )

    // Continuous 60fps animations for Phase 1
    val infiniteTransition = rememberInfiniteTransition(label = "PhantomForgeTransition")

    // Outer Production Tool Ring — Clockwise rotation (0° -> 360°)
    val outerRingRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14_000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "outerRingRotation"
    )

    // Inner Onmyōdō Spirit Seal — Counter-Clockwise rotation (360° -> 0°)
    val innerSealRotation by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9_500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "innerSealRotation"
    )

    // Hitodama Spirit-Fire Particle Convergence cycle (0f -> 1f)
    val particleOrbitPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3_600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particleOrbitPhase"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.background)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onSplashCompleted()
            },
        contentAlignment = Alignment.Center
    ) {
        // ====================================================================
        // PHASE 1: KINETIC YOKAI AWAKENING ("THE PHANTOM FORGE")
        // ====================================================================
        if (kineticAlpha > 0.01f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(kineticAlpha),
                contentAlignment = Alignment.Center
            ) {
                // Kinetic Canvas: Outer Production Tool Ring + Inner Yin-Yang/Torii Seal + Hitodama Orbs
                Canvas(
                    modifier = Modifier.size(340.dp)
                ) {
                    drawPhantomForgeRingsAndParticles(
                        palette = palette,
                        outerRotation = outerRingRotation,
                        innerRotation = innerSealRotation,
                        particlePhase = particleOrbitPhase
                    )
                }

                // Central Pulsing Yokai Brand Logo during Phase 1
                YokaiBrandLogo(
                    size = 124.dp,
                    animatedPulse = true
                )

                // Bottom Status Milestone Ticker + Progress Bar + Skip Hint
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 36.dp, vertical = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "PHANTOM FORGE // BYOK + MCP ENGINE",
                        color = palette.primaryAccent.copy(alpha = 0.8f),
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = BOOT_MILESTONES[currentMilestoneIndex],
                        color = palette.primaryText,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    LinearProgressIndicator(
                        progress = { awakeningProgress },
                        modifier = Modifier
                            .width(220.dp)
                            .height(1.5.dp)
                            .clip(RoundedCornerShape(1.dp)),
                        color = palette.primaryAccent,
                        trackColor = palette.border
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Tap anywhere to skip",
                        color = palette.secondaryText.copy(alpha = 0.72f),
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        // ====================================================================
        // PHASE 2: SETTLED BRAND LOCKUP (7 SECONDS PURE STILLNESS)
        // ====================================================================
        AnimatedVisibility(
            visible = isSettledPhase,
            enter = fadeIn(animationSpec = tween(durationMillis = 900)),
            exit = fadeOut(animationSpec = tween(durationMillis = 400))
        ) {
            YokaiBrandLockup(
                logoSize = 132.dp
            )
        }
    }
}

/**
 * Draws the Phase 1 "Phantom Forge" choreography around the central `YokaiBrandLogo`:
 * 1. Outer Production Tool Blueprint Ring (Clockwise) with `{ }`, `>_`, Git Branch, and Key Teeth glyphs.
 * 2. Inner Onmyōdō Spirit Seal (Counter-Clockwise) with Yin-Yang arcs and subtle Torii gate framing.
 * 3. 8 Hitodama spirit-fire particles spiraling inward from the outer production tool nodes into the core.
 */
private fun DrawScope.drawPhantomForgeRingsAndParticles(
    palette: YokaiColorPalette,
    outerRotation: Float,
    innerRotation: Float,
    particlePhase: Float
) {
    val cx = size.width * 0.5f
    val cy = size.height * 0.5f
    val maxR = size.minDimension * 0.46f
    val innerR = size.minDimension * 0.34f
    val coreR = size.minDimension * 0.18f

    // ========================================================================
    // 1. OUTER PRODUCTION TOOL BLUEPRINT RING (Clockwise Rotation)
    // ========================================================================
    rotate(degrees = outerRotation, pivot = Offset(cx, cy)) {
        // Dashed blueprint ring
        drawCircle(
            color = palette.border,
            radius = maxR,
            center = Offset(cx, cy),
            style = Stroke(
                width = 1.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f)
            )
        )

        // 4 Cardinal Developer Tool Nodes (0° Top, 90° Right, 180° Bottom, 270° Left)
        val cardinalAngles = listOf(-90.0, 0.0, 90.0, 180.0)
        cardinalAngles.forEachIndexed { idx, angleDeg ->
            val rad = Math.toRadians(angleDeg)
            val nx = cx + (maxR * cos(rad)).toFloat()
            val ny = cy + (maxR * sin(rad)).toFloat()
            val nodeRadius = 14.dp.toPx()

            drawCircle(
                color = palette.card,
                radius = nodeRadius,
                center = Offset(nx, ny)
            )
            drawCircle(
                color = palette.primaryAccent.copy(alpha = 0.8f),
                radius = nodeRadius,
                center = Offset(nx, ny),
                style = Stroke(width = 1.5.dp.toPx())
            )

            // Etch micro-glyph inside each cardinal node:
            val g = nodeRadius * 0.45f
            when (idx) {
                0 -> {
                    // Code Brackets { } glyph
                    drawLine(
                        color = palette.primaryAccent,
                        start = Offset(nx - g, ny - g * 0.8f),
                        end = Offset(nx - g * 0.4f, ny - g * 0.8f),
                        strokeWidth = 1.5.dp.toPx()
                    )
                    drawLine(
                        color = palette.primaryAccent,
                        start = Offset(nx - g, ny - g * 0.8f),
                        end = Offset(nx - g, ny + g * 0.8f),
                        strokeWidth = 1.5.dp.toPx()
                    )
                    drawLine(
                        color = palette.primaryAccent,
                        start = Offset(nx + g, ny - g * 0.8f),
                        end = Offset(nx + g, ny + g * 0.8f),
                        strokeWidth = 1.5.dp.toPx()
                    )
                }
                1 -> {
                    // Terminal Prompt >_ glyph
                    drawLine(
                        color = palette.primaryAccent,
                        start = Offset(nx - g * 0.8f, ny - g * 0.6f),
                        end = Offset(nx - g * 0.1f, ny),
                        strokeWidth = 1.6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = palette.primaryAccent,
                        start = Offset(nx - g * 0.1f, ny),
                        end = Offset(nx - g * 0.8f, ny + g * 0.6f),
                        strokeWidth = 1.6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = palette.secondaryAccent,
                        start = Offset(nx + g * 0.1f, ny + g * 0.6f),
                        end = Offset(nx + g * 0.85f, ny + g * 0.6f),
                        strokeWidth = 1.6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
                2 -> {
                    // Git Branch Fork Node glyph
                    drawLine(
                        color = palette.primaryAccent,
                        start = Offset(nx - g * 0.5f, ny - g * 0.7f),
                        end = Offset(nx - g * 0.5f, ny + g * 0.7f),
                        strokeWidth = 1.6.dp.toPx()
                    )
                    drawLine(
                        color = palette.secondaryAccent,
                        start = Offset(nx - g * 0.5f, ny),
                        end = Offset(nx + g * 0.6f, ny - g * 0.6f),
                        strokeWidth = 1.6.dp.toPx()
                    )
                    drawCircle(
                        color = palette.primaryAccent,
                        radius = 2.2.dp.toPx(),
                        center = Offset(nx + g * 0.6f, ny - g * 0.6f)
                    )
                }
                3 -> {
                    // Cryptographic Key Teeth glyph
                    drawCircle(
                        color = palette.secondaryAccent,
                        radius = g * 0.45f,
                        center = Offset(nx - g * 0.45f, ny),
                        style = Stroke(width = 1.5.dp.toPx())
                    )
                    drawLine(
                        color = palette.primaryAccent,
                        start = Offset(nx, ny),
                        end = Offset(nx + g * 0.85f, ny),
                        strokeWidth = 1.6.dp.toPx()
                    )
                    drawLine(
                        color = palette.primaryAccent,
                        start = Offset(nx + g * 0.55f, ny),
                        end = Offset(nx + g * 0.55f, ny + g * 0.55f),
                        strokeWidth = 1.5.dp.toPx()
                    )
                }
            }
        }
    }

    // ========================================================================
    // 2. INNER ONMYODO SPIRIT SEAL (Counter-Clockwise Rotation)
    // ========================================================================
    rotate(degrees = innerRotation, pivot = Offset(cx, cy)) {
        // Yin-Yang dual opposed arcs
        drawArc(
            color = palette.primaryAccent.copy(alpha = 0.65f),
            startAngle = 0f,
            sweepAngle = 145f,
            useCenter = false,
            topLeft = Offset(cx - innerR, cy - innerR),
            size = Size(innerR * 2f, innerR * 2f),
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
        drawArc(
            color = palette.secondaryAccent.copy(alpha = 0.65f),
            startAngle = 180f,
            sweepAngle = 145f,
            useCenter = false,
            topLeft = Offset(cx - innerR, cy - innerR),
            size = Size(innerR * 2f, innerR * 2f),
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )

        // Subtle Torii gate sacred framing pillars inside the spirit seal
        val pillarOffset = innerR * 0.52f
        val lintelY = cy - innerR * 0.62f
        drawLine(
            color = palette.secondaryAccent.copy(alpha = 0.28f),
            start = Offset(cx - pillarOffset * 1.25f, lintelY),
            end = Offset(cx + pillarOffset * 1.25f, lintelY),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = palette.primaryAccent.copy(alpha = 0.22f),
            start = Offset(cx - pillarOffset, lintelY),
            end = Offset(cx - pillarOffset, cy + innerR * 0.62f),
            strokeWidth = 1.2.dp.toPx()
        )
        drawLine(
            color = palette.primaryAccent.copy(alpha = 0.22f),
            start = Offset(cx + pillarOffset, lintelY),
            end = Offset(cx + pillarOffset, cy + innerR * 0.62f),
            strokeWidth = 1.2.dp.toPx()
        )
    }

    // ========================================================================
    // 3. HITODAMA SPIRIT-FIRE PARTICLE CONVERGENCE (8 Orbiting Spirit Orbs)
    // ========================================================================
    val particleCount = 8
    for (i in 0 until particleCount) {
        val normalizedProgress = (particlePhase + (i.toFloat() / particleCount)) % 1f
        // Spiral inward from outer production tool ring (maxR) into the central emblem (coreR)
        val currentRadius = maxR - (maxR - coreR) * normalizedProgress
        val spiralAngle = (i * (2.0 * PI / particleCount)) + (normalizedProgress * 2.4 * PI)
        val px = cx + (currentRadius * cos(spiralAngle)).toFloat()
        val py = cy + (currentRadius * sin(spiralAngle)).toFloat()

        val orbColor = if (i % 2 == 0) palette.primaryAccent else palette.secondaryAccent
        val alphaFade = (sin(normalizedProgress * PI).toFloat()).coerceIn(0.15f, 1f)

        // Outer Hitodama aura
        drawCircle(
            color = orbColor.copy(alpha = alphaFade * 0.28f),
            radius = 6.5.dp.toPx(),
            center = Offset(px, py)
        )
        // Inner Hitodama core flame
        drawCircle(
            color = orbColor.copy(alpha = alphaFade),
            radius = 3.dp.toPx(),
            center = Offset(px, py)
        )
    }
}
