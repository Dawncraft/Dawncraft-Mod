package io.github.dawncraft.entity;

import io.github.dawncraft.block.BlockFurnitureChair;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntitySittable extends Entity
{
    public int blockPosX;
    public int blockPosY;
    public int blockPosZ;
    
    public EntitySittable(World worldIn)
    {
        super(worldIn);
        this.noClip = true;
        this.height = 0.01F;
        this.width = 0.01F;
    }
    
    public EntitySittable(World worldIn, double x, double y, double z, double y0ffset)
    {
        this(worldIn);
        this.blockPosX = (int) x;
        this.blockPosY = (int) y;
        this.blockPosZ = (int) z;
        this.setPosition(x + 0.5D, y + y0ffset, z + 0.5D);
    }
    
    @Override
    protected void entityInit()
    {
    }
    
    @Override
    public void onEntityUpdate()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.riddenByEntity == null || !(this.worldObj.getBlockState(new BlockPos(this.blockPosX, this.blockPosY, this.blockPosZ)).getBlock() instanceof BlockFurnitureChair))
            {
                this.setDead();
                this.worldObj.updateComparatorOutputLevel(this.getPosition(), this.worldObj.getBlockState(this.getPosition()).getBlock());
            }
        }
    }
    
    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund)
    {
    }
    
    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound)
    {
    }
}
