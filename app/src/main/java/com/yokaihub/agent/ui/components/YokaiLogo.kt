// Copyright (c) 2026 ELG-GNP PRIDE TECHNOLOGY (@EDC-ETERNAL LEGACY GROUP). All Rights Reserved.
package com.yokaihub.agent.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yokaihub.agent.ui.theme.LocalYokaiColors
import com.yokaihub.agent.ui.theme.YokaiColorPalette
import com.yokaihub.agent.ui.theme.YokaiThemeVariant
import kotlin.math.cos
import kotlin.math.sin

/**
 * Resolves the drawable resource ID dynamically at runtime without hardcoding R.drawable references
 * that could fail compilation before user PNG assets are migrated to res/drawable/.
 */
@SuppressLint("DiscouragedApi")
@Composable
fun rememberDynamicDrawableResId(candidateNames: List<String>): Int {
    val context = LocalContext.current
    return remember(candidateNames, context) {
        candidateNames.firstNotNullOfOrNull { name ->
            val id = context.resources.getIdentifier(name, "drawable", context.packageName)
            if (id != 0) id else null
        } ?: 0
    }
}

/**
 * Master YOKAI HUB Brand Logo Composable (`YokaiBrandLogo`).
 *
 * Pre-wired to check for theme-specific transparent PNG drawables first, then solid variants:
 * - `yokai_logo_<theme>_transparent`
 * - `yokai_logo_<theme>`
 * - `yokai_logo_transparent` / `yokai_logo` (universal fallback drawables)
 *
 * If `resId == 0` (before PNG migration to res/drawable/), renders the precision 6-layer
 * Jetpack Compose `Canvas` emblem:
 * 1. Outer Hexagonal Hub Shield (75% canvas height security enclave)
 * 2. Crown Torii Arch (*Kasagi* lintel at Y:24%–34% symbolizing API & OAuth 2.0 gateway)
 * 3. Angular Kitsune Spirit Mask (Symmetrical faceplate Y:30%–78%, 45° ears, 28° slanted eyes)
 * 4. Central BYOK Keyhole Nose-Bridge (Cryptographic keyhole cutout at X:50%, Y:58%–74%)
 * 5. Onmyōdō Yin-Yang Sacred Geometry Ring (55%-scale concentric orbital ring)
 * 6. Production Tool Circuit Traces (6 straight traces radiating to hexagon corner MCP nodes)
 */
@Composable
fun YokaiBrandLogo(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    animatedPulse: Boolean = false
) {
    val palette = LocalYokaiColors.current
    val themeSpecificLogoName = when (palette.variant) {
        YokaiThemeVariant.CYBER_SHRINE -> "yokai_logo_cyber_shrine"
        YokaiThemeVariant.YIN_YANG -> "yokai_logo_yin_yang"
        YokaiThemeVariant.BLOOD_MOON_TORII -> "yokai_logo_blood_moon"
    }

    val logoResId = rememberDynamicDrawableResId(
        listOf(
            "${themeSpecificLogoName}_transparent",
            themeSpecificLogoName,
            "yokai_logo_transparent",
            "yokai_logo"
        )
    )

    val infiniteTransition = rememberInfiniteTransition(label = "YokaiLogoPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (animatedPulse) 1.06f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = if (animatedPulse) 0.35f else 0.18f,
        targetValue = if (animatedPulse) 0.85f else 0.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    Box(
        modifier = modifier
            .size(size)
            .graphicsLayer {
                scaleX = pulseScale
                scaleY = pulseScale
            },
        contentAlignment = Alignment.Center
    ) {
        if (logoResId != 0) {
            if (animatedPulse) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = palette.primaryAccent.copy(alpha = glowAlpha * 0.25f),
                        radius = this.size.minDimension * 0.48f
                    )
                }
            }
            Image(
                painter = painterResource(id = logoResId),
                contentDescription = "YOKAI HUB Brand Emblem",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawYokaiSixLayerEmblem(
                    palette = palette,
                    glowAlpha = glowAlpha
                )
            }
        }
    }
}

/**
 * Renders the YOKAI HUB Wordmark (`YOKAI [HUB]` + `妖怪ハブ • Autonomous AI & MCP Workspace`).
 * Checks for `yokai_wordmark_<theme>_transparent` / `yokai_wordmark_<theme>` first,
 * falling back to precision geometric typography.
 */
@Composable
fun YokaiBrandWordmark(
    modifier: Modifier = Modifier
) {
    val palette = LocalYokaiColors.current
    val wordmarkDrawableName = when (palette.variant) {
        YokaiThemeVariant.CYBER_SHRINE -> "yokai_wordmark_cyber_shrine"
        YokaiThemeVariant.YIN_YANG -> "yokai_wordmark_yin_yang"
        YokaiThemeVariant.BLOOD_MOON_TORII -> "yokai_wordmark_blood_moon"
    }
    val wordmarkResId = rememberDynamicDrawableResId(
        listOf("${wordmarkDrawableName}_transparent", wordmarkDrawableName)
    )

    if (wordmarkResId != 0) {
        Image(
            painter = painterResource(id = wordmarkResId),
            contentDescription = "YOKAI HUB Wordmark",
            contentScale = ContentScale.Fit,
            modifier = modifier.height(68.dp)
        )
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = palette.primaryText,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 28.sp,
                            letterSpacing = 3.8.sp
                        )
                    ) {
                        append("YOKAI ")
                    }
                    withStyle(
                        SpanStyle(
                            color = palette.primaryAccent.copy(alpha = 0.65f),
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Light,
                            fontSize = 26.sp,
                            letterSpacing = 2.sp
                        )
                    ) {
                        append("[")
                    }
                    withStyle(
                        SpanStyle(
                            color = palette.primaryAccent,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 28.sp,
                            letterSpacing = 3.8.sp
                        )
                    ) {
                        append("HUB")
                    }
                    withStyle(
                        SpanStyle(
                            color = palette.primaryAccent.copy(alpha = 0.65f),
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Light,
                            fontSize = 26.sp,
                            letterSpacing = 2.sp
                        )
                    ) {
                        append("]")
                    }
                },
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "妖怪ハブ",
                    color = palette.secondaryText,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "•",
                    color = palette.primaryAccent,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Autonomous AI & MCP Workspace",
                    color = palette.secondaryText,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.sp,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}

/**
 * Renders the Phase 2 Settled Brand Lockup (`YokaiBrandLockup`).
 * Checks for `yokai_lockup_<theme>_transparent` / `yokai_lockup_<theme>` first;
 * otherwise renders `YokaiBrandLogo` + `YokaiBrandWordmark`.
 */
@Composable
fun YokaiBrandLockup(
    modifier: Modifier = Modifier,
    logoSize: Dp = 132.dp
) {
    val palette = LocalYokaiColors.current
    val lockupDrawableName = when (palette.variant) {
        YokaiThemeVariant.CYBER_SHRINE -> "yokai_lockup_cyber_shrine"
        YokaiThemeVariant.YIN_YANG -> "yokai_lockup_yin_yang"
        YokaiThemeVariant.BLOOD_MOON_TORII -> "yokai_lockup_blood_moon"
    }
    val lockupResId = rememberDynamicDrawableResId(
        listOf("${lockupDrawableName}_transparent", lockupDrawableName)
    )

    if (lockupResId != 0) {
        Image(
            painter = painterResource(id = lockupResId),
            contentDescription = "YOKAI HUB Official Brand Lockup",
            contentScale = ContentScale.Fit,
            modifier = modifier.size(logoSize * 1.85f)
        )
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            YokaiBrandLogo(
                size = logoSize,
                animatedPulse = false
            )
            Spacer(modifier = Modifier.height(24.dp))
            YokaiBrandWordmark()
        }
    }
}

/**
 * Precision 6-Layer Jetpack Compose Canvas implementation of the YOKAI HUB Master Logo.
 */
private fun DrawScope.drawYokaiSixLayerEmblem(
    palette: YokaiColorPalette,
    glowAlpha: Float
) {
    val w = size.width
    val h = size.height
    val cx = w * 0.5f
    val cy = h * 0.5f
    val hexRadius = h * 0.42f

    // Subtle ambient spirit aura behind the shield
    drawCircle(
        color = palette.primaryAccent.copy(alpha = glowAlpha * 0.16f),
        radius = hexRadius * 1.12f,
        center = Offset(cx, cy)
    )

    // Compute 6 vertical-point hexagon vertices
    val hexVertices = List(6) { index ->
        val angleRad = Math.toRadians((60.0 * index) - 90.0)
        Offset(
            x = cx + (hexRadius * cos(angleRad)).toFloat(),
            y = cy + (hexRadius * sin(angleRad)).toFloat()
        )
    }

    val hexPath = Path().apply {
        hexVertices.forEachIndexed { idx, pt ->
            if (idx == 0) moveTo(pt.x, pt.y) else lineTo(pt.x, pt.y)
        }
        close()
    }

    // ========================================================================
    // LAYER 1: Outer Hexagonal Hub Shield (75%+ canvas height security enclave)
    // ========================================================================
    drawPath(
        path = hexPath,
        color = palette.card,
        style = Fill
    )
    drawPath(
        path = hexPath,
        color = palette.primaryAccent.copy(alpha = 0.85f),
        style = Stroke(width = w * 0.026f, join = StrokeJoin.Miter)
    )

    // Inner bevel hexagon border
    val innerHexRadius = hexRadius * 0.88f
    val innerHexPath = Path().apply {
        for (i in 0 until 6) {
            val angleRad = Math.toRadians((60.0 * i) - 90.0)
            val px = cx + (innerHexRadius * cos(angleRad)).toFloat()
            val py = cy + (innerHexRadius * sin(angleRad)).toFloat()
            if (i == 0) moveTo(px, py) else lineTo(px, py)
        }
        close()
    }
    drawPath(
        path = innerHexPath,
        color = palette.border,
        style = Stroke(width = w * 0.01f)
    )

    // ========================================================================
    // LAYER 5: Onmyōdō Yin-Yang Sacred Geometry Ring (55%-scale orbital ring)
    // ========================================================================
    val yinYangRadius = w * 0.275f
    drawCircle(
        color = palette.primaryAccent.copy(alpha = 0.28f),
        radius = yinYangRadius,
        center = Offset(cx, cy),
        style = Stroke(width = w * 0.014f)
    )
    drawArc(
        color = palette.secondaryAccent.copy(alpha = 0.65f),
        startAngle = -45f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(cx - yinYangRadius, cy - yinYangRadius),
        size = Size(yinYangRadius * 2f, yinYangRadius * 2f),
        style = Stroke(width = w * 0.018f, cap = StrokeCap.Round)
    )

    // ========================================================================
    // LAYER 6: Production Tool Circuit Traces & 6 MCP Corner Nodes
    // ========================================================================
    hexVertices.forEachIndexed { idx, vertex ->
        val startRatio = 0.42f
        val endRatio = 0.84f
        val startOffset = Offset(
            x = cx + (vertex.x - cx) * startRatio,
            y = cy + (vertex.y - cy) * startRatio
        )
        val nodeCenter = Offset(
            x = cx + (vertex.x - cx) * endRatio,
            y = cy + (vertex.y - cy) * endRatio
        )
        val traceColor = if (idx % 2 == 0) palette.primaryAccent else palette.secondaryAccent

        drawLine(
            color = traceColor.copy(alpha = 0.55f),
            start = startOffset,
            end = nodeCenter,
            strokeWidth = w * 0.012f,
            cap = StrokeCap.Round
        )
        drawCircle(
            color = palette.surface,
            radius = w * 0.024f,
            center = nodeCenter
        )
        drawCircle(
            color = traceColor,
            radius = w * 0.024f,
            center = nodeCenter,
            style = Stroke(width = w * 0.012f)
        )
    }

    // ========================================================================
    // LAYER 2: Crown Torii Arch (Kasagi lintel at Y:24%–34% with katana tips)
    // ========================================================================
    val kasagiPath = Path().apply {
        moveTo(w * 0.24f, h * 0.26f)
        lineTo(w * 0.30f, h * 0.285f)
        lineTo(w * 0.70f, h * 0.285f)
        lineTo(w * 0.76f, h * 0.26f)
        lineTo(w * 0.73f, h * 0.315f)
        lineTo(w * 0.27f, h * 0.315f)
        close()
    }
    drawPath(
        path = kasagiPath,
        color = palette.secondaryAccent,
        style = Fill
    )
    // Lower貫 (Nuki) crossbar
    drawLine(
        color = palette.secondaryAccent.copy(alpha = 0.85f),
        start = Offset(w * 0.31f, h * 0.345f),
        end = Offset(w * 0.69f, h * 0.345f),
        strokeWidth = w * 0.018f,
        cap = StrokeCap.Square
    )

    // ========================================================================
    // LAYER 3: Angular Kitsune Spirit Mask (Symmetrical faceplate Y:30%–78%)
    // ========================================================================
    val maskPath = Path().apply {
        // Left swept-back 45-deg Kitsune ear tip
        moveTo(w * 0.27f, h * 0.29f)
        // Left inner ear to brow
        lineTo(w * 0.41f, h * 0.39f)
        // Center forehead crest
        lineTo(w * 0.50f, h * 0.36f)
        // Right inner brow to right ear tip
        lineTo(w * 0.59f, h * 0.39f)
        lineTo(w * 0.73f, h * 0.29f)
        // Right outer ear & upper cheek facet
        lineTo(w * 0.69f, h * 0.49f)
        lineTo(w * 0.72f, h * 0.56f)
        // Tapered fox snout to chin point
        lineTo(w * 0.56f, h * 0.75f)
        lineTo(w * 0.50f, h * 0.79f)
        lineTo(w * 0.44f, h * 0.75f)
        // Left lower cheek facet & outer ear
        lineTo(w * 0.28f, h * 0.56f)
        lineTo(w * 0.31f, h * 0.49f)
        close()
    }

    drawPath(
        path = maskPath,
        color = palette.surface,
        style = Fill
    )
    drawPath(
        path = maskPath,
        color = palette.primaryText.copy(alpha = 0.92f),
        style = Stroke(width = w * 0.02f, join = StrokeJoin.Miter)
    )

    // 28° Inward-Slanted Glowing Triangular Eyes (Y:50%)
    val leftEyePath = Path().apply {
        moveTo(w * 0.34f, h * 0.475f)
        lineTo(w * 0.455f, h * 0.525f)
        lineTo(w * 0.365f, h * 0.54f)
        close()
    }
    val rightEyePath = Path().apply {
        moveTo(w * 0.66f, h * 0.475f)
        lineTo(w * 0.545f, h * 0.525f)
        lineTo(w * 0.635f, h * 0.54f)
        close()
    }
    drawPath(path = leftEyePath, color = palette.primaryAccent, style = Fill)
    drawPath(path = rightEyePath, color = palette.primaryAccent, style = Fill)

    // ========================================================================
    // LAYER 4: Central BYOK Keyhole Nose-Bridge (X:50%, Y:58%–74%)
    // ========================================================================
    val keyholeCircleCenter = Offset(cx, h * 0.615f)
    val keyholeCircleRadius = w * 0.036f
    drawCircle(
        color = palette.primaryAccent,
        radius = keyholeCircleRadius,
        center = keyholeCircleCenter
    )

    val keyholeStemPath = Path().apply {
        moveTo(cx - w * 0.020f, h * 0.625f)
        lineTo(cx + w * 0.020f, h * 0.625f)
        lineTo(cx + w * 0.034f, h * 0.725f)
        lineTo(cx - w * 0.034f, h * 0.725f)
        close()
    }
    drawPath(
        path = keyholeStemPath,
        color = palette.primaryAccent,
        style = Fill
    )

    // Inner dark core of the keyhole for crisp contrast
    drawCircle(
        color = palette.background,
        radius = keyholeCircleRadius * 0.52f,
        center = keyholeCircleCenter
    )
    val innerKeyholeStem = Path().apply {
        moveTo(cx - w * 0.010f, h * 0.625f)
        lineTo(cx + w * 0.010f, h * 0.625f)
        lineTo(cx + w * 0.018f, h * 0.708f)
        lineTo(cx - w * 0.018f, h * 0.708f)
        close()
    }
    drawPath(
        path = innerKeyholeStem,
        color = palette.background,
        style = Fill
    )
}
