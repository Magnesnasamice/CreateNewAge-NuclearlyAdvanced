package com.magnesnasamice.cnana.foundation.mixin;

import com.magnesnasamice.cnana.foundation.util.PlayerInterface;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class HazmatHelmetSuffocatingMixin implements PlayerInterface {
    @Shadow protected abstract int getNextAirUnderwater(int air);

    private boolean shouldBreathe = false;
    private boolean hazmatOn = false;

    @Override
    public void setShouldBreathe(boolean s) {
        this.shouldBreathe = s;
    }

    @Override
    public void setHazmatOn(boolean h) {
        this.hazmatOn = h;
    }

    @Override
    public boolean shouldPlayerBreathe() {
        return this.shouldBreathe;
    }

    //    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;canBreatheInWater()Z"))
//    private boolean injected(LivingEntity instance) {
//        return this.shouldBreathe || instance.canBreatheInWater();
//    }

//    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getAir()I"))
//    private int injected(LivingEntity instance) {
//        if (hazmatOn)
//            // if player has breatheFilter -> set air to 300,
//            // otherwise return LivingEntity value
//            return this.shouldBreathe ? 300 : instance.getAir();
//        return instance.getAir();
//    }

    // line 377
    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setAir(I)V"))
    private void injected(LivingEntity instance, int air) {
        if (this.hazmatOn)
            // if player has mask (and isn't underwater) -> let him breathe
            instance.setAir((this.shouldBreathe && !instance.isSubmergedInWater()) ? getNextAirUnderwater(instance.getMaxAir()) : getNextAirUnderwater(instance.getAir()));
        getNextAirUnderwater(instance.getAir());

        if (instance.getAir() == -20) {
            instance.setAir(0);

            instance.damage(instance.getDamageSources().drown(), 2.0F);
        }
    }
}
