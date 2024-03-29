package com.magnesnasamice.cnana.foundation.datagen;

import com.magnesnasamice.cnana.foundation.block.ModBlocks;
import com.magnesnasamice.cnana.foundation.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class DatagenModelProvider extends FabricModelProvider {
    public DatagenModelProvider(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator gen) {
        gen.registerSimpleCubeAll(ModBlocks.URANIUM_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator gen) {
        gen.register(ModItems.HAZMAT_HELMET, Models.GENERATED);
        gen.register(ModItems.HAZMAT_CHESTPLATE, Models.GENERATED);
        gen.register(ModItems.HAZMAT_LEGGINS, Models.GENERATED);
        gen.register(ModItems.HAZMAT_BOOTS, Models.GENERATED);
        gen.register(ModItems.HAZMAT_BREATHE_FILTER, Models.GENERATED);

        gen.register(ModItems.COLD_WATER_BUCKET, Models.GENERATED);

        gen.register(ModItems.URANIUM, Models.GENERATED);
    }
}
