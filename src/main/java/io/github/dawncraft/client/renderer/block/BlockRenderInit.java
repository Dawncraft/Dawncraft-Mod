package io.github.dawncraft.client.renderer.block;

import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.core.client.DawnClientHooks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;

/**
 * Register custom blocks' model.
 *
 * @author QingChenW
 */
public class BlockRenderInit
{
    public static void initBlockRender()
    {
        registerFieldModel((BlockFluidBase) BlockInit.PETROLEUM);

        registerStateMapper(BlockInit.MAGNET_DOOR, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        registerStateMapper(BlockInit.ALARM_CLOCK, new StateMap.Builder().ignore(BlockHorizontal.FACING).build());

        registerBuiltIn(BlockInit.MAGNET_CHEST, "minecraft:blocks/planks_oak");
        registerBuiltIn(BlockInit.SUPER_CHEST, "minecraft:blocks/planks_oak");
        registerBuiltIn(BlockInit.SKULL, "minecraft:blocks/soul_sand");
    }

    /**
     * Register a fluid's model.
     *
     * @param blockFluid Fluid block to register
     */
    private static void registerFieldModel(BlockFluidBase blockFluid)
    {
        Item itemFluid = Item.getItemFromBlock(blockFluid);
        final String name = Block.REGISTRY.getNameForObject(blockFluid).toString();
        ModelLoader.setCustomMeshDefinition(itemFluid, new ItemMeshDefinition()
        {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                return new ModelResourceLocation(name, "fluid");
            }
        });
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
     * Register a BlockStateMapper.
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
