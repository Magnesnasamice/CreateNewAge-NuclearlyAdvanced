package com.magnesnasamice.cnana.foundation.effect;

import com.magnesnasamice.cnana.foundation.Init;
import com.magnesnasamice.cnana.foundation.effect.custom.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final StatusEffect RADIOACTIVE = new RadioactiveStatusEffect();

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Init.MOD_ID, "radioactive"), RADIOACTIVE);
    }
}