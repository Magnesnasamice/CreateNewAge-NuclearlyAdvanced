package com.magnesnasamice.cnana.foundation.event.custom;

import com.magnesnasamice.cnana.foundation.Init;
import com.magnesnasamice.cnana.foundation.data.PlayerData;
import com.magnesnasamice.cnana.foundation.data.StateDataManager;
import com.magnesnasamice.cnana.foundation.network.ModPackets;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RadiationEventHandler {
    private static int elapsedTicks = 0;


    // when radiation changed -> update
    public static final Identifier RADIATION_UPDATE = new Identifier(Init.MOD_ID, "radiation_update");
    // on client load world -> init sync
    public static final Identifier RADIATION_INIT_SYNC = new Identifier(Init.MOD_ID, "radiation_init_sync");



    public static void register() {
        // check radiation packet
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // prevent packets to send before being in a world
            if (client.world == null) return;

            if (elapsedTicks % 10 == 0) {
                // send update packet every 0.5s
                ClientPlayNetworking.send(RADIATION_UPDATE, PacketByteBufs.create());
            }

            elapsedTicks++;
        });

        // ON PLAYER JOINED WORLD -> SYNC
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerData playerState = StateDataManager.getPlayerState(handler.getPlayer());
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(playerState.radiationPercent);
            server.execute(() -> {
                ServerPlayNetworking.send(handler.getPlayer(), RADIATION_INIT_SYNC, data); // initial sync
            });
        });

//        // decrease radiation (SERVER ONLY) (NEEDS SYNC)
//        ServerTickEvents.START_SERVER_TICK.register(server -> {
//            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
//                IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
//                // RadiationUtil.removeRadiation(dataPlayer, 1);
//                // player.sendMessage(Text.literal("Decreased radiation by 1"));
//            }
//        });
    }
}
