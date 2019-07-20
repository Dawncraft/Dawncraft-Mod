package io.github.dawncraft.world.gen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.MapGenStructure;

public class ChunkGeneratorDawn implements IChunkGenerator
{
    private final World world;
    private final Random rand;
    private boolean mapFeaturesEnabled;
    private final Map<String, MapGenStructure> structureGenerators = new HashMap<>();

    public ChunkGeneratorDawn(World world, long seed, boolean generateStructures)
    {
        this.world = world;
        this.rand = new Random(seed);
        this.mapFeaturesEnabled = generateStructures;
    }

    @Override
    public Chunk generateChunk(int x, int z)
    {
        ChunkPrimer chunkPrimer = new ChunkPrimer();

        chunkPrimer.setBlockState(8, 97, 8, Blocks.BEDROCK.getDefaultState());
        this.spawnIsland(8, 97, 8, chunkPrimer);

        Chunk chunk = new Chunk(this.world, chunkPrimer, x, z);
        Biome[] abiome = this.world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();
        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = (byte) Biome.getIdForBiome(abiome[i]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private void spawnIsland(int x, int y, int z, ChunkPrimer chunkPrimer)
    {
        chunkPrimer.setBlockState(x, y+1, z, Blocks.SAND.getDefaultState());
        chunkPrimer.setBlockState(x, y+2, z, Blocks.DIRT.getDefaultState());
        chunkPrimer.setBlockState(x+1, y+1, z, Blocks.DIRT.getDefaultState());
        chunkPrimer.setBlockState(x, y+1, z+1, Blocks.DIRT.getDefaultState());
        chunkPrimer.setBlockState(x-1, y+1, z, Blocks.DIRT.getDefaultState());
        chunkPrimer.setBlockState(x, y+1, z-1, Blocks.DIRT.getDefaultState());

        chunkPrimer.setBlockState(x, y+2, z, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x+1, y+2, z, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x+2, y+2, z, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x+1, y+2, z+1, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x, y+2, z+2, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x, y+2, z+1, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x-1, y+2, z+1, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x-1, y+2, z, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x-2, y+2, z, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x-1, y+2, z-1, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x, y+2, z-1, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x, y+2, z-2, Blocks.GRASS.getDefaultState());
        chunkPrimer.setBlockState(x+1, y+2, z-1, Blocks.GRASS.getDefaultState());
    }

    @Override
    public void populate(int x, int z)
    {

    }

    @Override
    public boolean generateStructures(Chunk chunk, int x, int z)
    {
        return false;
    }

    @Override
    public void recreateStructures(Chunk chunk, int x, int z)
    {
        for (MapGenStructure mapgenstructure : this.structureGenerators.values())
        {
            mapgenstructure.generate(this.world, x, z, null);
        }
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        Biome biome = this.world.getBiome(pos);

        if (this.mapFeaturesEnabled)
        {

        }

        return biome.getSpawnableList(creatureType);
    }

    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
        MapGenStructure mapgenstructure = this.structureGenerators.get(structureName);
        return mapgenstructure != null ? mapgenstructure.getNearestStructurePos(worldIn, position, findUnexplored) : null;
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
        MapGenStructure mapgenstructure = this.structureGenerators.get(structureName);
        return mapgenstructure != null ? mapgenstructure.isInsideStructure(pos) : false;
    }
}
