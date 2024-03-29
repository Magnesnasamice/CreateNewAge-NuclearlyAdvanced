package com.magnesnasamice.cnana.foundation.network;

import com.magnesnasamice.cnana.foundation.data.PlayerData;
import com.magnesnasamice.cnana.foundation.event.custom.RadiationEventHandler;
import com.magnesnasamice.cnana.foundation.network.packets.RadiationUpdateC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;

public class ModPackets {
    public static PlayerData playerData = new PlayerData();

    // client -> server
    public static void registerC2SPackets() {
        // send update packet
        ServerPlayNetworking.registerGlobalReceiver(RadiationEventHandler.RADIATION_UPDATE, RadiationUpdateC2SPacket::receive);
    }

    // server -> client
    public static void registerS2CPackets() {
        // on received update packet (CLIENT SIDE)
        ClientPlayNetworking.registerGlobalReceiver(RadiationEventHandler.RADIATION_UPDATE, (client, handler, buf, responseSender) -> {
            playerData.radiationPercent = buf.readInt();
//            client.execute(() -> {
//                client.player.sendMessage(Text.literal("Player specific radiation: " + playerData.radiationPercent));
//            });
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
