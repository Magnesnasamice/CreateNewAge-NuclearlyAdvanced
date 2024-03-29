package com.magnesnasamice.cnana.foundation.item.armor;

import com.google.common.collect.ImmutableMap;
import com.magnesnasamice.cnana.foundation.effect.ModEffects;
import com.magnesnasamice.cnana.foundation.item.ModItems;
import com.magnesnasamice.cnana.foundation.item.trinket.HazmatBreatheFilter;
import dev.emi.trinkets.api.SlotGroup;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Map;

public class ModArmorItem extends ArmorItem {
    private static final Map<ArmorMaterial, StatusEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, StatusEffectInstance>())
                    .put(ModArmorMaterials.HAZMAT, new StatusEffectInstance(ModEffects.RADIOACTIVE, 20, 0,
                            false, false, true)).build();

    public ModArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            if(entity instanceof PlayerEntity player && hasFullArmor(player)) {
                // enable status for custom armor
                evaluateArmorEffects(player);
            }
        }
    }

    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<ArmorMaterial, StatusEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            StatusEffectInstance mapStatusEffect = entry.getValue();

            if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
            }
        }
    }

    private boolean hasFullArmor(PlayerEntity player) {
        return !player.getInventory().getArmorStack(0).isEmpty() && !player.getInventory().getArmorStack(1).isEmpty()
                && !player.getInventory().getArmorStack(2).isEmpty() && !player.getInventory().getArmorStack(3).isEmpty();
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, PlayerEntity player) {
        for (ItemStack armorStack: player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmorStack(0).getItem());
        ArmorItem chestplate = ((ArmorItem) player.getInventory().getArmorStack(1).getItem());
        ArmorItem leggins = ((ArmorItem) player.getInventory().getArmorStack(2).getItem());
        ArmorItem boots = ((ArmorItem) player.getInventory().getArmorStack(3).getItem());

        // TrinketComponent tc = TrinketsApi.getTrinketComponent(player).get();
        // tc.isEquipped(ModItems.HAZMAT_BREATHE_FILTER);

        return helmet.getMaterial() == material && chestplate.getMaterial() == material
                && leggins.getMaterial() == material && boots.getMaterial() == material;
    }

    private void addStatusEffectForMaterial(PlayerEntity player, ArmorMaterial mapArmorMaterial, StatusEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasStatusEffect(mapStatusEffect.getEffectType());

        if(hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
            player.addStatusEffect(new StatusEffectInstance(mapStatusEffect));
        }
    }
}