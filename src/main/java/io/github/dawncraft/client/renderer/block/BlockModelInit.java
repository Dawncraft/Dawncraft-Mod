package io.github.dawncraft.client.renderer.block;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.core.client.DawnClientHooks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Register custom blocks' model.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID, value = Side.CLIENT)
public class BlockModelInit
{
    @SubscribeEvent
    public static void registerBlockModels(ModelRegistryEvent event)
    {
        registerFluidModel(BlockInit.PETROLEUM);

        registerStateMapper(BlockInit.MAGNET_DOOR, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        registerStateMapper(BlockInit.ALARM_CLOCK, new StateMap.Builder().ignore(BlockHorizontal.FACING).build());

        registerBuiltIn(BlockInit.MAGNET_CHEST, "minecraft:blocks/planks_oak");
        registerBuiltIn(BlockInit.SUPER_CHEST, "minecraft:blocks/planks_oak");
        registerBuiltIn(BlockInit.SKULL, "minecraft:blocks/soul_sand");
    }

    @SubscribeEvent
    public static void blockColors(ColorHandlerEvent.Block event)
    {

    }

    /**
     * Register a fluid's model.
     *
     * @param block Fluid block to register
     */
    private static void registerFluidModel(Block block)
    {
        if (!(block instanceof BlockFluidBase)) return;
        BlockFluidBase blockFluid = (BlockFluidBase) block;
        final String name = blockFluid.getRegistryName().toString();
        registerStateMapper(blockFluid, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(name, "fluid");
            }
        });
    }

    /**
     * Register a block state mapper.
     *
     * @param block block to register
     * @param mapper block's state mapper
     */
    private static void registerStateMapper(Block block, IStateMapper mapper)
    {
        ModelLoader.setCustomStateMapper(block, mapper);
    }

    /**
     * Register a builtin block.
     *
     * @param block builtin block to register
     * @param iconName the name of texture
     */
    private static void registerBuiltIn(Block block, String iconName)
    {
        DawnClientHooks.registerBuiltInBlocks(block);
        DawnClientHooks.registerBlockParticle(block, iconName);
    }
}
