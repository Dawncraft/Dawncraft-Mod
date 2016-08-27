package com.github.wdawning.dawncraft.worldgen;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class WorldTeleporterDawn extends Teleporter
{
    private final WorldServer worldServerInstance;

	public WorldTeleporterDawn(WorldServer worldIn)
    {
        super(worldIn);
        this.worldServerInstance = worldIn;
    }
    
    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
    {
        return false;
    }
    
    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
/*        int x = MathHelper.floor_double(entityIn.posX);
        int y = MathHelper.floor_double(entityIn.posY) - 1;
        int z = MathHelper.floor_double(entityIn.posZ);
        
        int x1, z1;
        for (int j = -2; j <= 2; ++j)
        {
            for (int k = -2; k <= 2; ++k)
            {
            	x1 = x + j;
            	z1 = z + k;
                if(worldServerInstance.getBlockState(new BlockPos(x1, y, z1)) == Blocks.air.getDefaultState())
                {
                    this.worldServerInstance.setBlockState(new BlockPos(x1, y, z1), Blocks.obsidian.getDefaultState());
                }
            }
        }*/
    }
    
    @Override
    public boolean makePortal(Entity p_85188_1_)
    {
        return false;
    }
    
    @Override
    public void removeStalePortalLocations(long p_85189_1_)
    {
    	
    }
}
