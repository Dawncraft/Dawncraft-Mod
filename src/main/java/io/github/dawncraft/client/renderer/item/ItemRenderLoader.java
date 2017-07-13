package io.github.dawncraft.client.renderer.item;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.fluid.FluidLoader;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Register items' inventory model.(Include ItemBlock)
 * 
 * @author QingChenW
 */
public class ItemRenderLoader
{
    public ItemRenderLoader(FMLPreInitializationEvent event)
    {
        // Energy
        registerRender(ItemLoader.bucketPetroleum);
        
        registerRender(BlockLoader.fluidPetroleum);
       
        registerRender(BlockLoader.electricCable);
        
        registerRender(BlockLoader.energyGeneratorHeat);
        registerRender(BlockLoader.energyGeneratorFluid);
        registerRender(BlockLoader.energyGeneratorSolar);
        registerRender(BlockLoader.energyGeneratorWind);
        registerRender(BlockLoader.energyGeneratorNuclear);
        registerRender(BlockLoader.energyGeneratorMagic);
        
        registerFluidRender((BlockFluidBase) BlockLoader.fluidPetroleum);
        // Magnet
        registerRender(ItemLoader.magnet);
        registerRender(ItemLoader.magnetIngot);
        registerRender(ItemLoader.magnetStick);
        registerRender(ItemLoader.magnetBall);
        
        registerRender(ItemLoader.magnetSword);
        registerRender(ItemLoader.magnetAxe);
        registerRender(ItemLoader.magnetPickaxe);
        registerRender(ItemLoader.magnetSpade);
        registerRender(ItemLoader.magnetHoe);
        registerRender(ItemLoader.magnetHelmet);
        registerRender(ItemLoader.magnetChestplate);
        registerRender(ItemLoader.magnetLeggings);
        registerRender(ItemLoader.magnetBoots);
        
        registerRender(BlockLoader.magnetOre);
        registerRender(BlockLoader.magnetBlock);
        
        // Machine
        registerRender(BlockLoader.machineFurnace);
        
        // Computer
        registerRender(ItemLoader.simpleCPU);
        registerRender(ItemLoader.proCPU);
        registerRender(ItemLoader.superCPU);
        
        registerRender(BlockLoader.simpleComputer);
        registerRender(BlockLoader.proComputer);
        registerRender(BlockLoader.superComputer);
        
        // Materials
        
        // Furniture
        registerRender(BlockLoader.superChest);
        
        // Food
        registerRender(ItemLoader.faeces);
        registerRender(ItemLoader.cakeEgg);
        
        // Magic
        registerRender(ItemLoader.magicBook);
        registerRender(ItemLoader.metalEssence);
        registerRender(ItemLoader.woodEssence);
        registerRender(ItemLoader.waterEssence);
        registerRender(ItemLoader.fireEssence);
        registerRender(ItemLoader.dirtEssence);
        
        registerRender(BlockLoader.magicOre);
        
        // Flans
        registerRender(ItemLoader.flanAK47);
        registerRender(ItemLoader.flanRPG);
        registerRender(ItemLoader.flanRPGRocket);
        
        // ColourEgg
        registerRender(ItemLoader.gerHeart);
        registerRender(ItemLoader.brainDead);
        registerRender(ItemLoader.funny);
        
        registerRender(ItemLoader.chinese);
        registerRender(ItemLoader.dj);
        
        registerRender(ItemLoader.goldiamondSword); 
        registerRender(ItemLoader.mjolnir);
    }
    
    /**
     * Register a item's inventory model.
     * 
     * @param item item's name-id
     */
    private static void registerRender(Item item)
    {
        String name = GameData.getItemRegistry().getNameForObject(item).toString();
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));
    }
    
    /**
     * Register a block's inventory model.
     * 
     * @param block block's name-id
     */
    private static void registerRender(Block block)
    {
        Item item = Item.getItemFromBlock(block);
        String name = GameData.getBlockRegistry().getNameForObject(block).toString();
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));
    }
    
    /**
     * Register a fluid's inventory model and it's model.
     * 
     * @param blockFluid 
     * @param blockStateName 
     */
    public static void registerFluidRender(BlockFluidBase blockFluid)
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
        ModelLoader.setCustomStateMapper(blockFluid, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(name, "fluid");
            }
        });
    }
}
