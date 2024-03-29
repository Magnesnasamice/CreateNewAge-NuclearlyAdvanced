package com.magnesnasamice.cnana.foundation.data;

import com.magnesnasamice.cnana.foundation.Init;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class StateDataManager extends PersistentState {
    public Integer radiationPercent = 0;
    public HashMap<UUID, PlayerData> players = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt(Init.MOD_ID + "radiation_percent", radiationPercent);

        NbtCompound playersNbt = new NbtCompound();
        players.forEach((uuid, playerData) -> {
            NbtCompound playerNbt = new NbtCompound();
            playerNbt.putInt(Init.MOD_ID + "radiation_percent", playerData.radiationPercent);
            playersNbt.put(uuid.toString(), playerNbt);
        });
        nbt.put("players", playersNbt);
        return nbt;
    }

    public static StateDataManager createFromNbt(NbtCompound nbt) {
        StateDataManager state = new StateDataManager();
        state.radiationPercent = nbt.getInt(Init.MOD_ID + "radiation_percent");

        NbtCompound playersNbt = nbt.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerData playerData = new PlayerData();
            playerData.radiationPercent = playersNbt.getCompound(key).getInt(Init.MOD_ID + "radiation_percent");

            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, playerData);
        });
        return state;
    }

    public static StateDataManager getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        StateDataManager state = persistentStateManager.getOrCreate(StateDataManager::createFromNbt, StateDataManager::new, Init.MOD_ID);
        state.markDirty();

        return state;
    }

    public static PlayerData getPlayerState(LivingEntity player) {
        StateDataManager serverState = getServerState(player.getServer());
        PlayerData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());

        return playerState;
    }
}
