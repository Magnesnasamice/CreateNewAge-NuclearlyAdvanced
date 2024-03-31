package com.magnesnasamice.cnana.foundation;

import com.magnesnasamice.cnana.foundation.client.RadiationBarOverlay;
import com.magnesnasamice.cnana.foundation.client.RadiationVignetteOverlay;
import com.magnesnasamice.cnana.foundation.network.ModPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import com.magnesnasamice.cnana.foundation.fluid.ModFluids;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class InitClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // add transparency and color to cold water
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_COLD_WATER, ModFluids.FLOWING_COLD_WATER,
                new SimpleFluidRenderHandler(
                        new Identifier("minecraft:block/water_still"),
                        new Identifier("minecraft:block/water_flow"),
                        0xA12389DA
                ));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.STILL_COLD_WATER, ModFluids.FLOWING_COLD_WATER);

        // Radiation bar overlay
        HudRenderCallback.EVENT.register(new RadiationBarOverlay());

        // Radiation hazard vignette
        HudRenderCallback.EVENT.register(new RadiationVignetteOverlay());

        ModPackets.registerS2CPackets(); // server to client packets
    }
}
