package com.magnesnasamice.cnana.foundation.client;

import com.magnesnasamice.cnana.foundation.Init;
import com.magnesnasamice.cnana.foundation.item.ModItems;
import com.magnesnasamice.cnana.foundation.util.RadiationUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class RadiationVignetteOverlay implements HudRenderCallback {
    private static final Identifier RADIATION_VIGNETTE = new Identifier(Init.MOD_ID,
            "textures/gui/radiation_vignette.png");
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;
        if (player == null) return;

        if (!player.getInventory().getArmorStack(3).getItem().equals(ModItems.HAZMAT_HELMET)) {
            renderRadiationVignette(mc, drawContext);
            // TODO re-render HUD to not have Z-problems with hotbar
        }
    }

    private static void renderRadiationVignette(MinecraftClient mc, DrawContext ctx) {
        MinecraftServer server = mc.player.getServer();
        if (server == null) return;

        System.out.println("render");



        ////// NAPRAW RENDEROWANIE VIGNETY

        float radiation = (float) RadiationUtil.getRadiationLevelPercent(
                mc.player.getServer().getPlayerManager().getPlayer(mc.player.getUuid()),
                mc.player.getServer().getOverworld());
        mc.player.sendMessage(Text.literal("" + radiation));
        ctx.setShaderColor(1, 1, 1, radiation);
        ctx.drawTexture(
                RADIATION_VIGNETTE,
                0, 0,
                0, 0,
                1920, 1080,
                1280, 720
        );
        ctx.setShaderColor(1, 1, 1, 1);
    }
}
