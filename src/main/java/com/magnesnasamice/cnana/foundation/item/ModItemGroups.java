package com.magnesnasamice.cnana.foundation.item;

import com.magnesnasamice.cnana.foundation.Init;
import com.magnesnasamice.cnana.foundation.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup CREATIVE_TAB = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Init.MOD_ID, "creative_tab"),
            FabricItemGroup.builder().
                    displayName(Text.translatable("itemgroup.creative_tab"))
                    .icon(() -> new ItemStack(ModItems.HAZMAT_HELMET)).entries(((displayContext, entries) -> {
                        entries.add(ModBlocks.REACTOR_CONTROL_PANEL);
                        entries.add(ModItems.URANIUM);
                        entries.add(ModBlocks.URANIUM_ORE);

                        entries.add(ModItems.HAZMAT_HELMET);
                        entries.add(ModItems.HAZMAT_CHESTPLATE);
                        entries.add(ModItems.HAZMAT_LEGGINS);
                        entries.add(ModItems.HAZMAT_BOOTS);
                        entries.add(ModItems.HAZMAT_BREATHE_FILTER);

                        entries.add(ModItems.COLD_WATER_BUCKET);
                    })).build());

    public static void register() {}
}