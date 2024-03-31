package com.magnesnasamice.cnana.foundation.damage;

import com.magnesnasamice.cnana.foundation.Init;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> RADIATION_DAMAGE_TYPE =RegistryKey.of(RegistryKeys.DAMAGE_TYPE,
            new Identifier(Init.MOD_ID, "radiation_damage_type"));

    public static DamageSource get(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }

    public static void register() {}
}
