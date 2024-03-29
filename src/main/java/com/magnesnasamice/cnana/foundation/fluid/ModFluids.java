package com.magnesnasamice.cnana.foundation.fluid;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import com.magnesnasamice.cnana.foundation.Init;
import com.magnesnasamice.cnana.foundation.fluid.custom.ColdWater;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluids {
    public static FlowableFluid STILL_COLD_WATER;
    public static FlowableFluid FLOWING_COLD_WATER;
    public static Block COLD_WATER_BLOCK;

    public static void register() {
        STILL_COLD_WATER = Registry.register(Registries.FLUID,
                new Identifier(Init.MOD_ID, "cold_water"), new ColdWater.Still());
        FLOWING_COLD_WATER = Registry.register(Registries.FLUID,
                new Identifier(Init.MOD_ID, "flowing_cold_water"), new ColdWater.Flowing());
        COLD_WATER_BLOCK = Registry.register(Registries.BLOCK,
                new Identifier(Init.MOD_ID, "cold_water_block"),
                new FluidBlock(ModFluids.STILL_COLD_WATER, FabricBlockSettings.copyOf(Blocks.WATER)));
    }
}