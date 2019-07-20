package io.github.dawncraft.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMagnetDoor extends TileEntity
{
    private String UUID;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        if (!StringUtils.isNullOrEmpty(this.UUID))
            compound.setString("UUID", this.UUID);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("UUID", 8))
            this.UUID = compound.getString("UUID");
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos blockPos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    public boolean isLocked()
    {
        return !StringUtils.isNullOrEmpty(this.getUUID());
    }

    public String getUUID()
    {
        return this.UUID;
    }

    public void setUUID(String uuid)
    {
        this.UUID = uuid;
    }
}
