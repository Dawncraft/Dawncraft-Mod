package io.github.dawncraft.worldgen;

import java.util.Random;

import io.github.dawncraft.block.BlockLoader;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Register ore generators.
 * 
 * @author QingChenW
 */
public class WorldOreGenerator
{
    public static WorldGenMinable magnetOreGenerator = new WorldGenMinable(BlockLoader.magnetOre.getDefaultState(), 8)
    {
        @Override
        public boolean generate(World worldIn, Random rand, BlockPos position)
        {
            if (TerrainGen.generateOre(worldIn, rand, this, position, OreGenEvent.GenerateMinable.EventType.CUSTOM))
            {
                if (worldIn.provider.getDimensionId() == 0)
                {
                    for (int i = 0; i < 8; ++i)
                    {
                        BlockPos blockpos = new BlockPos(position.getX() * 16 + rand.nextInt(16), 1 + rand.nextInt(62), position.getZ() * 16 + rand.nextInt(16));
                        super.generate(worldIn, rand, blockpos);
                    }
                }
            }
            return false;
        }
    };
    
    public WorldOreGenerator(FMLInitializationEvent event)
    {
        MinecraftForge.ORE_GEN_BUS.register(this);
    }
    
    private BlockPos pos;
    @SubscribeEvent
    public void onOreGenPost(OreGenEvent.Post event)
    {
        if (!event.pos.equals(this.pos))
        {
            this.pos = event.pos;
            magnetOreGenerator.generate(event.world, event.rand, event.pos);
        }
    }
    
    @SubscribeEvent
    public void onOreGenGenerateMinable(OreGenEvent.GenerateMinable event)
    {
        
    }
}
