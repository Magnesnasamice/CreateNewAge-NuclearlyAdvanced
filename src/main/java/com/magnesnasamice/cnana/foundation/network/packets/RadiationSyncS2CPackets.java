package com.magnesnasamice.cnana.foundation.network.packets;

import com.magnesnasamice.cnana.foundation.data.PlayerData;
import com.magnesnasamice.cnana.foundation.event.custom.RadiationEventHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.text.Text;

public class RadiationSyncS2CPackets {

    public static PlayerData playerData = new PlayerData();

    public static void registerSyncPackets() {
        // on received update packet (CLIENT SIDE)
        ClientPlayNetworking.registerGlobalReceiver(RadiationEventHandler.RADIATION_UPDATE, (client, handler, buf, responseSender) -> {
            playerData.radiationPercent = buf.readInt();
            client.execute(() -> {
                // client.player.sendMessage(Text.literal("Player specific radiation: " + playerData.radiationPercent));
            });
        });

        // on received sync packet (CLIENT SIDE)
        ClientPlayNetworking.registerGlobalReceiver(RadiationEventHandler.RADIATION_INIT_SYNC, (client, handler, buf, responseSender) -> {
            playerData.radiationPercent = buf.readInt();
//            client.execute(() -> {
//                client.player.sendMessage(Text.literal("Init radiation: " + playerData.radiationPercent));
//            });
        });
    }
}