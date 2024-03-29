package com.magnesnasamice.cnana.foundation.block;

import com.magnesnasamice.cnana.foundation.Init;
import com.magnesnasamice.cnana.foundation.block.custom.ReactorControlPanelBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block REACTOR_CONTROL_PANEL = registerBlock("reactor_control_panel",
            new ReactorControlPanelBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque().strength(4.0F).requiresTool()));
    public static final Block URANIUM_ORE = registerBlock("uranium_ore",
            new Block(FabricBlockSettings.create().nonOpaque().strength(15.0F, 60.0F).dropsNothing().requiresTool())); // indestructible like bedrock



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Init.MOD_ID, name), block);
    }
    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(Init.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }
    public static void register() {}
}