package com.magnesnasamice.cnana.foundation.network.packets;

import com.magnesnasamice.cnana.foundation.block.ModBlocks;
import com.magnesnasamice.cnana.foundation.data.PlayerData;
import com.magnesnasamice.cnana.foundation.data.StateDataManager;
import com.magnesnasamice.cnana.foundation.effect.ModEffects;
import com.magnesnasamice.cnana.foundation.event.custom.RadiationEventHandler;
import com.magnesnasamice.cnana.foundation.item.ModItems;
import com.magnesnasamice.cnana.foundation.util.RadiationUtil;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class RadiationUpdateC2SPacket {
    private static final int radiationBlockRange = 7;
    private static final List<Block> radiatingBlocks = Collections.singletonList(
            ModBlocks.URANIUM_ORE
    );
    private static final List<Item> radiatingItems = Collections.singletonList(
            ModItems.URANIUM
    );

    private static final String RADIATION_TRANSLATION = "message.cnana.radiation_show";

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        ServerWorld world = player.getServerWorld();

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


        boolean hasHazmat = player.hasStatusEffect(ModEffects.RADIOACTIVE);
        if ((areRadiatingBlocksNearby(player, world) || areRadiatingItemsNearby(player, world) || hasRadiatingItemsInInventory(player)) && !hasHazmat) {
            // TODO render more radiation bar
            RadiationUtil.increaseRadiation(playerState, 1);
        } else {
            RadiationUtil.decreaseRadiation(playerState, 1);
            // if player doesn't have helmet - add overlay similar to vignette
            // render less radiation bar
        }
        //player.sendMessage(Text.literal("radiation: " + playerState.radiationPercent)); // current radiation

        // show radiation as message
        // TODO get closest radiation source
        // player.sendMessage(Text.translatable(RADIATION_TRANSLATION, getRadiationLevelPercent(player, world) * 24));


        PacketByteBuf data = PacketByteBufs.create();
        // data.writeInt(serverState.radiationPercent); // saving to server
        data.writeInt(playerState.radiationPercent); // saving to player

        ServerPlayerEntity playerEntity = server.getPlayerManager().getPlayer(player.getUuid());
        server.execute(() -> {
            // update radiation
            ServerPlayNetworking.send(playerEntity, RadiationEventHandler.RADIATION_UPDATE, data);
        });
    }

    private static boolean areRadiatingItemsNearby(ServerPlayerEntity player, ServerWorld serverWorld) {
        // list item entities
        List<ItemEntity> items = (List<ItemEntity>) serverWorld.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), EntityPredicates.VALID_ENTITY);

        // iterate to check if item entity is inside predefined radiatingItems list
        for (ItemEntity e : items) {
            if (radiatingItems.contains(e.getStack().getItem()) &&
                    player.distanceTo(e) <= radiationBlockRange)
                return true;
            break;
        }
        return false;
    }

    private static boolean areRadiatingBlocksNearby(ServerPlayerEntity player, ServerWorld serverWorld) {
        return BlockPos.stream(player.getBoundingBox().expand(radiationBlockRange))
                .map(serverWorld::getBlockState).filter(state -> {
                    // iterate to check if block is inside predefined radiatingBlocks list
                    for (Block b : radiatingBlocks) {
                        if (state.isOf(b)) return true;
                        break;
                    }
                    return false;
                }).toArray().length > 0; // if > 0 radiating blocks
    }

    private static float getRadiationLevelPercent(ServerPlayerEntity player, ServerWorld serverWorld) {
        if (!areRadiatingBlocksNearby(player, serverWorld) && !areRadiatingItemsNearby(player, serverWorld)) return 0;
        else {
            /* sources:
                inventory - distance 0
                blocks - ?
                items - distanceTo (in player class)
             */


//            ChunkManager manager = serverWorld.getChunkManager();
//            ChunkPos playerChunk = player.getChunkPos();
//            ArrayList<Chunk> chunksAroundPlayer = new ArrayList<>();

//            int x = playerChunk.x - 16;
//            int z = playerChunk.z - 16;
//            for (int i = 0; i <= 2; i++) { // left-right
//                for (int j = 0; j <= 2; j++) { // forward-back
//                    chunksAroundPlayer.add((Chunk) manager.getChunk(x, z));
//                    z += 16; // go to another chunk right
//                }
//                z = playerChunk.z - 16; // reset z
//                x += 16; // go to another chunk down
//            }
//
//            if (done == 20) {
//                for (Chunk c : chunksAroundPlayer) {
//                    System.out.println(c);
//                }
//            }
//            done++;
        }
        return 0;
    }

    private static boolean hasRadiatingItemsInInventory(ServerPlayerEntity player) {
        // check main inventory (9x3)
        for (ItemStack is : player.getInventory().main) {
            if (radiatingItems.contains(is.getItem()) || radiatingBlocks.contains(is.getItem())) return true;
            break;
        }
        // check offhand
        for (ItemStack is : player.getInventory().offHand) {
            if (radiatingItems.contains(is.getItem()) || radiatingBlocks.contains(is.getItem())) return true;
            break;
        }
        return false;
    }
}