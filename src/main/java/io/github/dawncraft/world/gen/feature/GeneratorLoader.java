package io.github.dawncraft.world.gen.feature;

import java.util.Random;

import io.github.dawncraft.block.BlockInit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
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

    public static WorldGenerator magnetOreGenerator = new WorldGenMinable(BlockInit.MAGIC_ORE.getDefaultState(), 6)
    {
        @Override
        public boolean generate(World world, Random rand, BlockPos position)
        {
            if (TerrainGen.generateOre(world, rand, this, position, OreGenEvent.GenerateMinable.EventType.CUSTOM))
            {
                if (world.provider.getDimensionType() == DimensionType.OVERWORLD)
                {
                    for (int i = 0; i < 8; ++i)
                    {
                        BlockPos blockpos = new BlockPos(position.getX() + rand.nextInt(16), 1 + rand.nextInt(62), position.getZ() + rand.nextInt(16));
                        super.generate(world, rand, blockpos);
                    }
                }
            }
            return true;
        }
    };

    public static void initGenerators()
    {
        registerEvent(new GeneratorLoader());
    }

    @SubscribeEvent
    public void onOreGenPost(OreGenEvent.Post event)
    {
        if (!event.getPos().equals(this.lastOrePos))
        {
            this.lastOrePos = event.getPos();
            magnetOreGenerator.generate(event.getWorld(), event.getRand(), event.getPos());
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
