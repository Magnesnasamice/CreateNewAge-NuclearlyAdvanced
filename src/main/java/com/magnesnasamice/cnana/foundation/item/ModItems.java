package com.magnesnasamice.cnana.foundation.item;

import com.magnesnasamice.cnana.foundation.Init;
import com.magnesnasamice.cnana.foundation.fluid.ModFluids;
import com.magnesnasamice.cnana.foundation.item.armor.ModArmorItem;
import com.magnesnasamice.cnana.foundation.item.armor.ModArmorMaterials;
import com.magnesnasamice.cnana.foundation.item.trinket.HazmatBreatheFilter;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // NORMAL
    public static final Item URANIUM = registerItem("uranium",
            new Item(new FabricItemSettings()));

    // ADVANCED


    // FLUIDS
    public static Item COLD_WATER_BUCKET = Registry.register(Registries.ITEM,
            new Identifier(Init.MOD_ID, "cold_water_bucket"),
                new BucketItem(ModFluids.STILL_COLD_WATER, new FabricItemSettings().maxCount(1)));


    // ARMOR
    public static final Item HAZMAT_HELMET = registerItem("hazmat_helmet",
            new ModArmorItem(ModArmorMaterials.HAZMAT, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item HAZMAT_CHESTPLATE = registerItem("hazmat_chestplate",
            new ModArmorItem(ModArmorMaterials.HAZMAT, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item HAZMAT_LEGGINS = registerItem("hazmat_leggins",
            new ModArmorItem(ModArmorMaterials.HAZMAT, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item HAZMAT_BOOTS = registerItem("hazmat_boots",
            new ModArmorItem(ModArmorMaterials.HAZMAT, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    // TRINKETS
    public static final Item HAZMAT_BREATHE_FILTER = registerItem("hazmat_breathe_filter",
            new HazmatBreatheFilter(new FabricItemSettings().maxCount(1)));



    // UTIL
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Init.MOD_ID, name), item);
    }
    public static void register() {}
}