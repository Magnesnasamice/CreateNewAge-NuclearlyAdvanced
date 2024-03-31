package com.magnesnasamice.cnana.foundation.network;

import com.magnesnasamice.cnana.foundation.event.custom.RadiationEventHandler;
import com.magnesnasamice.cnana.foundation.network.packets.RadiationSyncS2CPackets;
import com.magnesnasamice.cnana.foundation.network.packets.RadiationUpdateC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModPackets {

    // client -> server
    public static void registerC2SPackets() {
        // send update packet
        ServerPlayNetworking.registerGlobalReceiver(RadiationEventHandler.RADIATION_UPDATE, RadiationUpdateC2SPacket::receive);
    }

    // server -> client
    public static void registerS2CPackets() {
        RadiationSyncS2CPackets.registerSyncPackets();
    }
}
