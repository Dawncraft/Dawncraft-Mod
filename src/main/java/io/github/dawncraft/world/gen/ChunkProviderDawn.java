package io.github.dawncraft.world.gen;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.List;

public class ChunkProviderDawn implements IChunkProvider
{
    private World worldObj;
    
    public ChunkProviderDawn(World worldObj, long seed)
    {
        this.worldObj = worldObj;
    }
    
    @Override
    public Chunk provideChunk(BlockPos blockPos)
    {
        return null;
    }
    
    @Override
    public Chunk provideChunk(int x, int z)
    {
        ChunkPrimer chunkprimer = new ChunkPrimer();
        
        chunkprimer.setBlockState(8, 97, 8, Blocks.bedrock.getDefaultState());
        
        this.spawnIsland(8, 97, 8, chunkprimer);

        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
        
        chunk.generateSkylightMap();
        BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();
        for (int h = 0; h < abyte.length; ++h)
        {
            abyte[h] = (byte)abiomegenbase[h].biomeID;
        }
        chunk.generateSkylightMap();
        
        return chunk;
    }

    private void spawnIsland(int x, int y, int z, ChunkPrimer chunkPrimer)
    {
        chunkPrimer.setBlockState(x, y+1, z, Blocks.sand.getDefaultState());
        chunkPrimer.setBlockState(x, y+2, z, Blocks.dirt.getDefaultState());
        chunkPrimer.setBlockState(x+1, y+1, z, Blocks.dirt.getDefaultState());
        chunkPrimer.setBlockState(x, y+1, z+1, Blocks.dirt.getDefaultState());
        chunkPrimer.setBlockState(x-1, y+1, z, Blocks.dirt.getDefaultState());
        chunkPrimer.setBlockState(x, y+1, z-1, Blocks.dirt.getDefaultState());
        
        chunkPrimer.setBlockState(x, y+2, z, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x+1, y+2, z, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x+2, y+2, z, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x+1, y+2, z+1, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x, y+2, z+2, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x, y+2, z+1, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x-1, y+2, z+1, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x-1, y+2, z, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x-2, y+2, z, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x-1, y+2, z-1, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x, y+2, z-1, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x, y+2, z-2, Blocks.grass.getDefaultState());
        chunkPrimer.setBlockState(x+1, y+2, z-1, Blocks.grass.getDefaultState());
    }

    @Override
    public boolean chunkExists(int x, int z)
    {
        return true;
    }

    @Override
    public void populate(IChunkProvider provider, int x, int z)
    {
        
    }

    @Override
    public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunk, int x, int z)
    {
        return false;
    }
    
    @Override
    public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback)
    {
        return true;
    }

    @Override
    public void saveExtraData() {}

    @Override
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    @Override
    public boolean canSave()
    {
        return true;
    }

    @Override
    public String makeString()
    {
        return "VoidLevelSource";
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(pos);
        return biomegenbase.getSpawnableList(creatureType);
    }

    @Override
    public BlockPos getStrongholdGen(World world, String structureName, BlockPos position)
    {
        return null;
    }
    
    @Override
    public int getLoadedChunkCount()
    {
        return 0;
    }

    @Override
    public void recreateStructures(Chunk chunk, int x, int z) {}
}
