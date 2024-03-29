package com.magnesnasamice.cnana.foundation.event.custom;

import com.magnesnasamice.cnana.foundation.item.ModItems;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class HazmatBreathingEventHandler {
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // prevent packets to send before being in a world
            if (client.world == null) return;

            if (client.player.getInventory().getArmorStack(0).getItem().equals(ModItems.HAZMAT_HELMET)) { // check if hazmat helmet is head armor slot
                // this is checking if player has equipped HazmatBreatheFilter
                TrinketComponent tc = TrinketsApi.getTrinketComponent(client.player).get();
                boolean hasBreatheFilterEquipped = tc.isEquipped(ModItems.HAZMAT_BREATHE_FILTER);

                if (hasBreatheFilterEquipped) {
                    // allow player to breathe
                } else {
                    // make player suffocate (like underwater)
                }
            }
        });
    }
}
