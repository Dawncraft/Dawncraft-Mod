package io.github.dawncraft.entity;

import io.github.dawncraft.block.BlockFurnitureChair;
import io.github.dawncraft.config.ConfigLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySittable extends Entity
{
    public BlockPos blockPos;

    public EntitySittable(World world)
    {
        super(world);
        this.noClip = true;
        this.height = 0.01F;
        this.width = 0.01F;
    }

    public EntitySittable(World world, BlockPos pos, double yOffset)
    {
        this(world);
        this.blockPos = pos;
        this.setPosition(pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);
    }

    @Override
    protected void entityInit()
    {
    }

    @Override
    public void onEntityUpdate()
    {
        if (!this.world.isRemote)
        {
            if (this.riddenByEntity != null && this.world.getBlockState(this.blockPos).getBlock() instanceof BlockFurnitureChair)
            {
                if (ConfigLoader.chairHealAmount > 0)
                {
                    if (this.ticksExisted % 20 == 0 && this.riddenByEntity instanceof EntityPlayer)
                    {
                        EntityPlayer player = (EntityPlayer) this.riddenByEntity;
                        player.heal(ConfigLoader.chairHealAmount);
                    }
                }
            }
            else
            {
                this.setDead();
                this.world.updateComparatorOutputLevel(this.getPosition(), this.world.getBlockState(this.getPosition()).getBlock());
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
