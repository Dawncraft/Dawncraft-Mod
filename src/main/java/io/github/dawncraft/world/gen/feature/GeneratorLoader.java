package io.github.dawncraft.world.gen.feature;

import java.util.Random;

import io.github.dawncraft.block.BlockLoader;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Register ore generators and worlds.
 *
 * @author QingChenW
 */
public class GeneratorLoader
{
    private BlockPos lastOrePos;
    
    public static WorldGenerator magnetOreGenerator = new WorldGenMinable(BlockLoader.magnetOre.getDefaultState(), 6)
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
                        BlockPos blockpos = new BlockPos(position.getX() + rand.nextInt(16), 1 + rand.nextInt(62), position.getZ() + rand.nextInt(16));
                        super.generate(worldIn, rand, blockpos);
                    }
                }
            }
            return true;
        }
    };
    
    public static void initGenerators()
    {
        // registerGenerator

        registerEvent(new GeneratorLoader());
    }
    
    @SubscribeEvent
    public void onOreGenPost(OreGenEvent.Post event)
    {
        if (!event.pos.equals(this.lastOrePos))
        {
            this.lastOrePos = event.pos;
            magnetOreGenerator.generate(event.world, event.rand, event.pos);
        }
    }
    
    @SubscribeEvent
    public void onOreGenGenerateMinable(OreGenEvent.GenerateMinable event)
    {
        
    }
    
    private static void registerEvent(Object target)
    {
        MinecraftForge.TERRAIN_GEN_BUS.register(target);
        MinecraftForge.ORE_GEN_BUS.register(target);
    }

    private static void registerGenerator(IWorldGenerator generator, int weight)
    {
        GameRegistry.registerWorldGenerator(generator, weight);
    }
}
