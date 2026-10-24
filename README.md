# YOKAI HUB — Nexus GO Edition (`妖怪ハブ`)

**Zero-Preset BYOK AI Workspace & Custom MCP Client for Android**

YOKAI HUB (Nexus GO Edition) is a minimalist, high-craft Android AI workspace (`com.yokaihub.agent`) engineered around a strict **Zero-Preset Bring-Your-Own-Key (BYOK)** architecture and **Custom Model Context Protocol (MCP)** server bridges.

---

## Core Architectural Pillars
1. **Zero-Preset BYOK Security:** No pre-bundled API keys or locked endpoints. Credentials are encrypted on-device via `EncryptedSharedPreferences` (`androidx.security:security-crypto`).
2. **3 Switchable Japan Yokai Themes (Light & Dark Modes):**
   - **Cyber Shrine (`CYBER_SHRINE` — Default):** Shrine Obsidian (`#0A0B0E`) / Washi (`#F9FAFB`) with Kitsune Cyan (`#00E5FF` / `#007A8C`) & Torii Crimson (`#FF2A54` / `#D91E41`).
   - **Yin-Yang Monochrome (`YIN_YANG`):** Pitch Sumi Black (`#000000`) & Spirit White (`#FFFFFF`) high-contrast duality.
   - **Blood Moon Torii (`BLOOD_MOON_TORII`):** Charcoal Ash (`#0F0D0D`) / Warm Washi Ivory (`#FAF8F5`) with Torii Vermilion (`#FF4D2D` / `#C83218`) & Kitsune Gold (`#C5A059`).
3. **Chat-First Single-Activity Workspace:** Zero bottom navigation bar (`NavigationBar`). Uses a slide-out navigation drawer, top model selector pill, and bottom composer.
4. **Two-Phase Kinetic Splash Choreography ("The Phantom Forge"):** 15s kinetic awakening + 7s settled brand lockup with instant 1-tap skip.

---

## 5-Stage Iterative Development Roadmap (`Phase 1: P1_S1 – P1_S5`)
- **Stage 1 (`P1_S1`):** Core Android/Gradle Baseline, 3 Yokai Themes, Dynamic Logo Loader + 6-Layer Canvas Emblem Fallback, Interface #1 Splash Screen, and `MainActivity` entry point.
- **Stage 2 (`P1_S2`):** Root PNG Logo Asset Normalization into `app/src/main/res/drawable/` & Interface #2 Main Chat Workspace + Slide-Out Navigation Drawer.
- **Stage 3 (`P1_S3`):** Interface #3 Settings, Personalization & AgentRouter BYOK Engine.
- **Stage 4 (`P1_S4`):** Interface #4 Plugins Hub & Custom MCP Server Connector.
- **Stage 5 (`P1_S5`):** Final Wiring, GitHub OAuth Integration, End-to-End Simulation Audit & Release APK Pipeline.
