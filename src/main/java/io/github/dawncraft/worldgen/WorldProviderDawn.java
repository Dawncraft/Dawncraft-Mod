package io.github.dawncraft.worldgen;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderDawn extends WorldProvider
{
    public void registerWorldChunkManager()
    {
       this.worldChunkMgr = new WorldChunkManager(worldObj);
       this.dimensionId = 23;
    }

	@Override
	public String getDimensionName()
	{
        return "Dawnworld";
	}
	
	@Override
	public String getInternalNameSuffix()
	{
        return "_dawn";
	}
	
	@Override
    public boolean isSurfaceWorld()
    {
        return false;
    }
	
	@Override
    public boolean canRespawnHere()
    {
        return false;
    }
	
	@Override
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderDawn(this.worldObj, this.worldObj.getSeed());
    }

}
