package com.magnesnasamice.cnana.foundation.item.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class HazmatBreatheFilter extends TrinketItem {
    public HazmatBreatheFilter(Settings settings) {
        super(settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        // modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uuid, "cna_nuclearly_advanced:movement_speed", 0.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        // SlotAttributes.addSlotModifier(modifiers, "head/face", uuid, 1, EntityAttributeModifier.Operation.ADDITION);

        return super.getModifiers(stack, slot, entity, uuid);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.cnana.hazmat_breathe_filter.tooltip"));
    }
}