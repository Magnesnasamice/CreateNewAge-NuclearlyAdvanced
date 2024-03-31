package com.magnesnasamice.cnana.foundation.util;

import com.magnesnasamice.cnana.foundation.block.ModBlocks;
import com.magnesnasamice.cnana.foundation.data.PlayerData;
import com.magnesnasamice.cnana.foundation.item.ModItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;

public class RadiationUtil {

    public static final int radiationBlockRange = 7;

    public static final List<Block> radiatingBlocks = Arrays.asList(
            ModBlocks.URANIUM_ORE
    );
    public static final List<Item> radiatingItems = Arrays.asList(
            ModItems.URANIUM
    );

    public static void setRadiation(PlayerData playerState, int amount) {
        playerState.radiationPercent = amount;
    }

    public static float getRadiationLevelPercent(ServerPlayerEntity player, ServerWorld serverWorld) {
        if (!areRadiatingBlocksNearby(player, serverWorld) && !areRadiatingItemsNearby(player, serverWorld)) return 0;
        else {
//            if (RadiationUtil.hasRadiatingItemsInInventory(player)) {
//                return 0.86f;
//            }
            /* sources:
                inventory - distance 0
                blocks - ?
                items - distanceTo (in player class)
             */

            //--------------------------//
            //------- ItemEntity -------//
            //--------------------------//

            // list item entities
            List<ItemEntity> items = (List<ItemEntity>) serverWorld.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), EntityPredicates.VALID_ENTITY);
            List<Double> itemDistanceList = new ArrayList<>(); // save radiating items in range

            // go through every item and check if they are in max radiation distance (7)
            for (ItemEntity e : items) {
                if (radiatingItems.contains(e.getStack().getItem()) && player.distanceTo(e) <= radiationBlockRange)
                    itemDistanceList.add((double) player.distanceTo(e));
            }

            //----------------------//
            //------- Blocks -------//
            //----------------------//

            BlockPos playerPos = player.getBlockPos();

            BlockPos firstRadiusBoxCorner = playerPos.add(radiationBlockRange, radiationBlockRange, radiationBlockRange);
            BlockPos secondRadiusBoxCorner = playerPos.add(-1 * radiationBlockRange, -1 * radiationBlockRange, -1 * radiationBlockRange);
            Iterable<BlockPos> cubeResult = BlockPos.iterate(firstRadiusBoxCorner, secondRadiusBoxCorner);

            List<Double> blockDistanceList = new ArrayList<>(); // save radiating blocks in range
            // go through every BlockPos that cube collided with

            for (BlockPos pos : cubeResult) {
                // list may contain BlockPos outside of radius,
                // so we remove if the distance to player is more than 7
                if (getBlockPosDistance(playerPos, pos) <= radiationBlockRange && radiatingBlocks.contains(serverWorld.getBlockState(pos).getBlock()))
                    blockDistanceList.add(getBlockPosDistance(playerPos, pos));
            }

            //------------------------------------------------//
            //------- Get average radiation percentage -------//
            //------------------------------------------------//

            // average item radiation
            // return getAverageRadiationPercent(itemDistanceList, blockDistanceList);

            // get the closest item or block from both arrays, compare them and round up to 2 floating points (0.00f)
            double distance = getClosestFromList(blockDistanceList, itemDistanceList);

            // there's a bug (sometimes when not in distance it records
            // 24th level of radiation)
            if (distance == 0) {
                return 0;
            }

            // 7 blocks of distance = 0%
            // 1 blocks of distance = 100%
            // total 7 blocks, so (100% / 7) ~ 14.28
            return (float) ((100.0 - (Math.round(distance * 14.29f) * 100.0) / 100.0) / 100.0);
        }
    }

    //------- util -------//
    public static double getBlockPosDistance(BlockPos pos1, BlockPos pos2) {
        double deltaX = pos1.getX() - pos2.getX();
        double deltaY = pos1.getY() - pos2.getY();
        double deltaZ = pos1.getZ() - pos2.getZ();

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }

    public static double getClosestFromList(List<Double> blockDistanceList, List<Double> itemDistanceList) {
        double distance;
        if (blockDistanceList.isEmpty() && !itemDistanceList.isEmpty()) {
            distance = Math.round(Collections.min(itemDistanceList) * 100.0) / 100.0;
        } else if (!blockDistanceList.isEmpty() && itemDistanceList.isEmpty()) {
            distance = Math.round(Collections.min(blockDistanceList) * 100.0) / 100.0;
        } else if (blockDistanceList.isEmpty()) {
            distance = 0;
        } else {
            distance = Math.round(min(Collections.min(itemDistanceList), Collections.min(blockDistanceList)) * 100.0) / 100.0;
        }
        return distance;
    }

    public static double getAverageRadiationPercent(List<Double> itemsDistanceList, List<Double> blocksDistanceList) {
        // average items
        int count = 0;
        double sum = 0;
        for (Double distance : itemsDistanceList) {
            sum += distance;
            count++;
        }
        double avgItems = Math.round((sum/count) * 100.0) / 100.0;
        // System.out.println("avg items: " + avgItems);

        // average blocks
        count = 0;
        sum = 0;
        for (Double distance : blocksDistanceList) {
            sum += distance;
            count++;
        }
        double avgBlocks = Math.round((sum/count) * 100.0) / 100.0;
        // System.out.println("avg blocks: " + avgBlocks);

        // if no items nearby
        if (Double.isNaN(avgItems)) {
            return avgBlocks;
        }
        // if no blocks nearby
        else if (Double.isNaN(avgBlocks)) {
            return avgItems;
        }
        return (avgItems+avgBlocks)/2;
    }



    /// OTHER ///


    public static boolean areRadiatingItemsNearby(ServerPlayerEntity player, ServerWorld serverWorld) {
        // get all loaded ItemEntities
        List<ItemEntity> items = (List<ItemEntity>) serverWorld.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), EntityPredicates.VALID_ENTITY);

        // iterate to check if item entity is inside predefined radiatingItems list
        for (ItemEntity e : items) {
            if (radiatingItems.contains(e.getStack().getItem()) &&
                    player.distanceTo(e) <= RadiationUtil.radiationBlockRange)
                return true;
            break;
        }
        return false;
    }

    public static boolean areRadiatingBlocksNearby(ServerPlayerEntity player, ServerWorld serverWorld) {
        return BlockPos.stream(player.getBoundingBox().expand(RadiationUtil.radiationBlockRange))
                .map(serverWorld::getBlockState).filter(state -> {
                    // iterate to check if block is inside predefined radiatingBlocks list
                    for (Block b : radiatingBlocks) {
                        if (state.isOf(b)) return true;
                        break;
                    }
                    return false;
                }).toArray().length > 0; // if > 0 radiating blocks
    }

    public static boolean hasRadiatingItemsInInventory(ServerPlayerEntity player) {
        // check main inventory (9x3)
        for (ItemStack is : player.getInventory().main) {
            if (radiatingItems.contains(is.getItem()) || radiatingBlocks.contains(Block.getBlockFromItem(is.getItem()))) return true;
            // System.out.println("there is " + is.getItem());
            break;
        }
        // check offhand
        for (ItemStack is : player.getInventory().offHand) {
            if (radiatingItems.contains(is.getItem()) || radiatingBlocks.contains(Block.getBlockFromItem(is.getItem()))) return true;
            // System.out.println("there is " + is.getItem());
            break;
        }
        // System.out.println("there is none");
        return false;

    }
}
