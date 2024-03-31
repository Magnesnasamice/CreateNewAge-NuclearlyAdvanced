package com.magnesnasamice.cnana.foundation.client;

import com.magnesnasamice.cnana.foundation.Init;
import com.magnesnasamice.cnana.foundation.data.StateDataManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class RadiationBarOverlay implements HudRenderCallback {
    private static final Identifier EMPTY_RADIATION = new Identifier(Init.MOD_ID,
            "textures/gui/empty_radiation.png");
    private static final Identifier FULL_RADIATION = new Identifier(Init.MOD_ID,
            "textures/gui/full_radiation.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.player.getServer() == null) return;

        // get radiation percent
        int radiation = Math.round(24f * StateDataManager.getPlayerState(mc.player).radiationPercent);

        int sw = mc.getWindow().getScaledWidth();
        int sh = mc.getWindow().getScaledHeight();

        int x = sw / 2;
        int y = sh / 2;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        drawContext.drawTexture(EMPTY_RADIATION, x, y, 0, 0, 201, 20, 201, 20);

        // mc.inGameHud.renderMountJumpBar();
        
        if (radiation == 0) {
            drawContext.drawTexture(FULL_RADIATION, x, y, 0, 0, 13, 20, 201, 20);
        } else if (radiation == 24) {
            drawContext.drawTexture(FULL_RADIATION, x, y, 0, 0, 201, 20, 201, 20);
        }
        else {
            drawContext.drawTexture(FULL_RADIATION, x, y, 0, 0, 13 + radiation * 8, 20, 201, 20);
        }
    }
}