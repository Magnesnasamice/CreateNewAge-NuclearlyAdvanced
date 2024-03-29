package com.magnesnasamice.cnana.foundation.client;

import com.magnesnasamice.cnana.foundation.Init;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;

public class RadiationHudOverlay implements HudRenderCallback {
    private static final Identifier EMPTY_RADIATION = new Identifier(Init.MOD_ID,
            "textures/gui/empty_radiation.png");
    private static final Identifier FULL_RADIATION = new Identifier(Init.MOD_ID,
            "textures/gui/full_radiation.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null) return;

        int sw = mc.getWindow().getScaledWidth();
        int sh = mc.getWindow().getScaledHeight();

        int x = sw / 2;
        int y = sh / 2;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        drawContext.drawTexture(FULL_RADIATION, x, y, 10, 23, 201, 20);

//        if (mc.player != null) {
//            int radiation = ((IEntityDataSaver) mc.player).getPersistentData().getInt("radiation_level");
//            if (radiation == 1) {
//                drawContext.drawTexture(EMPTY_RADIATION, x, y, 11, 20, 201, 20);
//            } else if (radiation == 24) {
//                drawContext.drawTexture(FULL_RADIATION, x, y, 201, 20, 201, 20);
//            }
//            else {
//                drawContext.drawTexture(FULL_RADIATION, x, y, radiation * 7 + 4, 20, 201, 20);
//            }
//        }
    }
}