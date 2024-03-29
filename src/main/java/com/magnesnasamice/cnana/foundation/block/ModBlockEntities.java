package com.magnesnasamice.cnana.foundation.block;

import com.magnesnasamice.cnana.foundation.Init;
import com.magnesnasamice.cnana.foundation.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<ReactorControlPanelBlockEntity> REACTOR_CONTROL_PANEL = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(Init.MOD_ID, "reactor_control_panel_block_entity"),
            FabricBlockEntityTypeBuilder.create(ReactorControlPanelBlockEntity::new, ModBlocks.REACTOR_CONTROL_PANEL)
                    .build()
    );

    public static void register() {}
}
