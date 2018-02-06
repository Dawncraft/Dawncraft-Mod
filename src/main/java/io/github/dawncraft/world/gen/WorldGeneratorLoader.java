package io.github.dawncraft.world.gen;

import java.util.Random;

import io.github.dawncraft.block.BlockLoader;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Register ore generators and worlds.
 *
 * @author QingChenW
 */
//TODO 天域
public class WorldGeneratorLoader
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
            return false;
        }
    };

    public WorldGeneratorLoader(FMLInitializationEvent event)
    {
        MinecraftForge.TERRAIN_GEN_BUS.register(this);
        MinecraftForge.ORE_GEN_BUS.register(this);

        this.registerWorld(23, WorldProviderDawn.class);
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

    /**
     * Register a world.
     *
     * @param id The world's id
     * @param provider The world's provider
     */
    public void registerWorld(int id, Class<? extends WorldProvider> provider)
    {
        DimensionManager.registerProviderType(id, provider, true);
        DimensionManager.registerDimension(id, id);
    }
}
