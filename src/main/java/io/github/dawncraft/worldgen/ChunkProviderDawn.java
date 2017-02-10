package io.github.dawncraft.worldgen;

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
		public Chunk provideChunk(BlockPos blockPosIn)
		{
			return null;
		}
	    
	    public Chunk provideChunk(int x, int z)
	    {
	        ChunkPrimer chunkprimer = new ChunkPrimer();
	        
            chunkprimer.setBlockState(8, 97, 8, Blocks.bedrock.getDefaultState());
            
            spawnIsland(8, 97, 8, chunkprimer);

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

	    private void spawnIsland(int x, int y, int z, ChunkPrimer chunkprimer)
	    {
            chunkprimer.setBlockState(x, y+1, z, Blocks.sand.getDefaultState());
            chunkprimer.setBlockState(x, y+2, z, Blocks.dirt.getDefaultState());
            chunkprimer.setBlockState(x+1, y+1, z, Blocks.dirt.getDefaultState());
            chunkprimer.setBlockState(x, y+1, z+1, Blocks.dirt.getDefaultState());
            chunkprimer.setBlockState(x-1, y+1, z, Blocks.dirt.getDefaultState());
            chunkprimer.setBlockState(x, y+1, z-1, Blocks.dirt.getDefaultState());
            
            chunkprimer.setBlockState(x, y+2, z, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x+1, y+2, z, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x+2, y+2, z, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x+1, y+2, z+1, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x, y+2, z+2, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x, y+2, z+1, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x-1, y+2, z+1, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x-1, y+2, z, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x-2, y+2, z, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x-1, y+2, z-1, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x, y+2, z-1, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x, y+2, z-2, Blocks.grass.getDefaultState());
            chunkprimer.setBlockState(x+1, y+2, z-1, Blocks.grass.getDefaultState());
		}

		public boolean chunkExists(int p_73149_1_, int p_73149_2_)
	    {
	        return true;
	    }

	    public void populate(IChunkProvider provider, int x, int z)
	    {
	      
	    }
	    
	    public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_)
	    {
	        return false;
	    }
	    
	    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
	    {
	        return true;
	    }

	    public void saveExtraData() {}

	    public boolean unloadQueuedChunks()
	    {
	        return false;
	    }

	    public boolean canSave()
	    {
	        return true;
	    }

	    public String makeString()
	    {
	        return "VoidLevelSource";
	    }

	    public List getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
	    {
	        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(pos);
	        return biomegenbase.getSpawnableList(creatureType);
	    }

	    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position)
	    {
	        return null;
	    }
	    
	    public int getLoadedChunkCount()
	    {
	        return 0;
	    }

	    public void recreateStructures(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {}
}
