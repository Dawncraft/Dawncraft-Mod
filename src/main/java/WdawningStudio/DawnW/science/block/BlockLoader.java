package WdawningStudio.DawnW.science.block;

import WdawningStudio.DawnW.science.science;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLoader
{
	//Furniture
    public static Block wchest = new BlockWchest();
	//magnet
    public static Block magnetOre = new BlockMagnetOre();
    public static Block magnetBlock = new BlockMagnetBlock();
//    public static Block magnetDoor = new BlockMagnetDoor();
    //energy
    public static Block electricityFurnace = new BlockElectricityFurnace();
    
    public static Block fluidPetroleum = new BlockFluidPetroleum();
    //machine
    public static Block machineFurnace = new BlockMachineFurnace();
    //computer
    public static Block simpleComputer = new BlockComputer.SimpleComputer();
	public static Block highComputer = new BlockComputer.HighComputer();
    public static Block proComputer = new BlockComputer.ProComputer();
    public static Block superComputer = new BlockComputer.SuperComputer();
    
    //color egg
    
    
    public BlockLoader(FMLPreInitializationEvent event)
    {
    	//Furniture
        register(wchest, "super_chest"); 
    	//magnet
        register(magnetOre, "magnet_ore"); 
        register(magnetBlock, "magnet_block");
 //       register(magnetDoor, "magnet_door"); 
        //energy
        register(fluidPetroleum, "petroleum");
        register(electricityFurnace, "ele_furnace");
        //machine
        register(machineFurnace, "iron_furnace");
        //computer
        register(simpleComputer, "simple_computer");
        register(highComputer, "high_computer");
		register(proComputer, "pro_computer");
        register(superComputer, "super_computer");
        //color egg
        
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
    	//Furniture
        registerRender(wchest, "super_chest"); 
    	//magnet
        registerRender(magnetOre, "magnet_ore");
        registerRender(magnetBlock, "magnet_block");
 //       registerRender(magnetDoor, "magnet_door"); 
        //energy
        registerRender(fluidPetroleum, "petroleum");
        registerRender(electricityFurnace, "ele_furnace");
        //machine
        registerRender(machineFurnace, "iron_furnace");
        //computer
        registerRender(simpleComputer, "simple_computer");
        registerRender(highComputer, "high_computer"); 
		registerRender(proComputer, "pro_computer");
        registerRender(superComputer, "super_computer");
        //color egg
        
    }

    private static void register(Block block, String name)
    {
        GameRegistry.registerBlock(block, name);
    }

    @SuppressWarnings("unchecked")
    private static void register(Block block, ItemBlock itemBlock, String name)
    {
        GameRegistry.registerBlock(block, null, name);
        GameRegistry.registerItem(itemBlock, name);
        GameData.getBlockItemMap().put(block, itemBlock);
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block, String name)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0,
        		new ModelResourceLocation(science.MODID + ":" + name, "inventory"));
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block, int meta, String name)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(science.MODID + ":" + name, "inventory"));
    }
}