package com.github.wdawning.dawncraft.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.block.BlockLoader;

public class FluidRenderLoader
{
    public FluidRenderLoader(FMLPreInitializationEvent event)
    {
        registerFluidRender((BlockFluidBase) BlockLoader.fluidPetroleum, "fluid_petroleum");
        
        event.getModLog().info("The registy of fluid render is finished. ");
    }
    
    public static void registerFluidRender(BlockFluidBase blockFluid, String blockStateName)
    {
        final String location = dawncraft.MODID + ":" + blockStateName;
        final Item itemFluid = Item.getItemFromBlock(blockFluid);
        ModelLoader.setCustomMeshDefinition(itemFluid, new ItemMeshDefinition()
        {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                return new ModelResourceLocation(location, "fluid");
            }
        });
        ModelLoader.setCustomStateMapper(blockFluid, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(location, "fluid");
            }
        });
    }
}
