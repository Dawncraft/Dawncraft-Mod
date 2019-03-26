package io.github.dawncraft.client.renderer.block;

import io.github.dawncraft.block.BlockFurnitureAlarmClock;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.block.BlockMagnetDoor;
import io.github.dawncraft.core.client.DawnClientHooks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.registry.GameData;

/**
 * Register custom blocks' model.
 *
 * @author QingChenW
 */
public class BlockRenderLoader
{
    public static void initBlockRender()
    {
        registerFieldModel((BlockFluidBase) BlockLoader.fluidPetroleum);
        
        registerStateMapper(BlockLoader.magnetDoor, new StateMap.Builder().ignore(BlockMagnetDoor.POWERED).build());
        registerStateMapper(BlockLoader.alarmClock, new StateMap.Builder().ignore(BlockFurnitureAlarmClock.FACING).build());

        registerBuiltIn(BlockLoader.magnetChest, "minecraft:blocks/planks_oak");
        registerBuiltIn(BlockLoader.superChest, "minecraft:blocks/planks_oak");
        registerBuiltIn(BlockLoader.skull, "minecraft:blocks/soul_sand");
    }
    
    /**
     * Register a fluid's model.
     *
     * @param blockFluid Fluid block to register
     */
    private static void registerFieldModel(BlockFluidBase blockFluid)
    {
        Item itemFluid = Item.getItemFromBlock(blockFluid);
        final String name = GameData.getBlockRegistry().getNameForObject(blockFluid).toString();
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
