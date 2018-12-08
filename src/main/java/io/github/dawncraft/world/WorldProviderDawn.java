package io.github.dawncraft.world;

import io.github.dawncraft.world.gen.ChunkProviderDawn;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderDawn extends WorldProvider
{
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
    public String getWelcomeMessage()
    {
        return "Entering the Dawn world";
    }
    
    @Override
    public String getDepartMessage()
    {
        return "Leaving the Dawn world";
    }

    @Override
    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new WorldChunkManager(this.worldObj);
    }
    
    @Override
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderDawn(this.worldObj, this.worldObj.getSeed());
    }

}
