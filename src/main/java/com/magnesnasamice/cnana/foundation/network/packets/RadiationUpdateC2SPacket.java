package com.magnesnasamice.cnana.foundation.network.packets;

import com.magnesnasamice.cnana.foundation.damage.ModDamageTypes;
import com.magnesnasamice.cnana.foundation.data.PlayerData;
import com.magnesnasamice.cnana.foundation.data.StateDataManager;
import com.magnesnasamice.cnana.foundation.effect.ModEffects;
import com.magnesnasamice.cnana.foundation.event.custom.RadiationEventHandler;
import com.magnesnasamice.cnana.foundation.util.RadiationUtil;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

public class RadiationUpdateC2SPacket {
    private static int calls = 0;

    private static final String RADIATION_TRANSLATION = "message.cnana.radiation_show";

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        ServerWorld serverWorld = player.getServerWorld();

        //----------------------------------------//
        //------- THIS HAPPENS SERVER ONLY -------//
        //----------------------------------------//

        //------- update persistent data ------- //
        if (server == null) return;

        // save data to server
        // StateDataManager serverState = StateDataManager.getServerState(server);
        // serverState.radiationPercent += 1;

        // save data to individual player
        PlayerData playerState = StateDataManager.getPlayerState(player);
        float currentRadiation = RadiationUtil.getRadiationLevelPercent(player, serverWorld);

        boolean hasHazmat = player.hasStatusEffect(ModEffects.RADIOACTIVE);
        if ((RadiationUtil.areRadiatingBlocksNearby(player, serverWorld) || RadiationUtil.areRadiatingItemsNearby(player, serverWorld) || RadiationUtil.hasRadiatingItemsInInventory(player)) && !hasHazmat) {
            RadiationUtil.setRadiation(playerState, Math.round(24 * currentRadiation));
            // this packet is received every 0.5s
            // max damage = 6 (3 hearts)
            // damage once/every 1s
            if (calls % 2 == 0) {
                player.damage(ModDamageTypes.get(serverWorld, ModDamageTypes.RADIATION_DAMAGE_TYPE), (6f * currentRadiation));

                // damage the hazmat
//                for (ItemStack is : player.getInventory().armor) {
//                    is.damage(2, Random.create(), player); // damage every hazmat item (except mask)
//                }
            }
        } else {
            RadiationUtil.setRadiation(playerState, Math.round(24 * currentRadiation));
        }
        // player.sendMessage(Text.translatable(RADIATION_TRANSLATION, getRadiationLevelPercent(player, world) * 24));


        PacketByteBuf data = PacketByteBufs.create();
        // data.writeInt(serverState.radiationPercent); // saving to server
        data.writeInt(playerState.radiationPercent); // saving to player

        // ServerPlayerEntity selectedPlayer = server.getPlayerManager().getPlayer(player.getUuid());
        server.execute(() -> {
            // update radiation
            ServerPlayNetworking.send(player, RadiationEventHandler.RADIATION_UPDATE, data);
        });

        calls++;
    }
}