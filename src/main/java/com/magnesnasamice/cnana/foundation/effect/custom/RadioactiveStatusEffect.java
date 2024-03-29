package com.magnesnasamice.cnana.foundation.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class RadioactiveStatusEffect extends StatusEffect {
    public RadioactiveStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xffd200);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
    }
}