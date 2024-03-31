package com.magnesnasamice.cnana.foundation.event.custom;

import com.magnesnasamice.cnana.foundation.item.ModItems;
import com.magnesnasamice.cnana.foundation.util.PlayerInterface;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class HazmatBreathingEventHandler {
    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            // prevent checking tick events before world loading
            if (server.getWorld(World.OVERWORLD) == null) return;

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                // check if player has hazmat helmet on
                boolean hasHazmatHelmet = player.getInventory().getArmorStack(3).getItem().equals(ModItems.HAZMAT_HELMET);
                if (hasHazmatHelmet) {
                    // if player cannot be cast to PlayerInterface -> cancel
                    if (!(player instanceof PlayerInterface breathingPlayer)) return;

                    // needed to for mixin (always true bc if statement)
                    breathingPlayer.setHazmatOn(true);

                    // check if player has equipped HazmatBreatheFilter
                    TrinketComponent tc = TrinketsApi.getTrinketComponent(player).get();
                    boolean hasBreatheFilterEquipped = tc.isEquipped(ModItems.HAZMAT_BREATHE_FILTER);

                    // depending if the player has breatheFilter on -> allow or deny breathing
                    breathingPlayer.setShouldBreathe(hasBreatheFilterEquipped);
                }
            }
        });
    }
}