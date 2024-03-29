package com.magnesnasamice.cnana.foundation.block.entity;

import com.magnesnasamice.cnana.foundation.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ReactorControlPanelBlockEntity extends BlockEntity {
    private int number;

    public ReactorControlPanelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REACTOR_CONTROL_PANEL, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("cnana.number", number);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        number = nbt.getInt("cnana.number");
    }

    public static void tick(World world, BlockPos pos, BlockState state) {
        // tick
    }
}