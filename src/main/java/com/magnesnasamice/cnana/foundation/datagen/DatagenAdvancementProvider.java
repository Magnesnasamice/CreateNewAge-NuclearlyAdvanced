package com.magnesnasamice.cnana.foundation.datagen;

import com.magnesnasamice.cnana.foundation.Init;
import com.magnesnasamice.cnana.foundation.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class DatagenAdvancementProvider extends FabricAdvancementProvider {
    public DatagenAdvancementProvider(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Advancement FULL_HAZMAT_EQUIPPED = Advancement.Builder.create()
                .display(
                        ModItems.HAZMAT_HELMET,
                        Text.translatable("advancements.cnana.full_hazmat_equipped.title"),
                        Text.translatable("advancements.cnana.full_hazmat_equipped.description"),
                        new Identifier(""),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_hazmat", InventoryChangedCriterion.Conditions.items(
                        ModItems.HAZMAT_HELMET,
                        ModItems.HAZMAT_CHESTPLATE,
                        ModItems.HAZMAT_LEGGINS,
                        ModItems.HAZMAT_BOOTS))
                .build(consumer, Init.MOD_ID+"/full_hazmat_equipped");
    }
}