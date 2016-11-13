package com.github.wdawning.dawncraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.wdawning.dawncraft.dawncraft;

public class BlockLoader
{
    // Material
    public static final Material MACHINE = new MaterialMachine();
    // Furniture
    public static Block wchest = new BlockFurnitureSuperChest();
    // magnet
    public static Block magnetOre = new BlockMagnetOre();
    public static Block magnetBlock = new BlockMagnetBlock();
    // public static Block magnetDoor = new BlockMagnetDoor();
    // energy
    public static Block electricityPipe = new BlockElectricityCable();
    
    public static Block electricityGeneratorHeat = new BlockElectricGenerator("heatGenerator", 0);
    public static Block electricityGeneratorFluid = new BlockElectricGenerator("fluidGenerator", 1);
    public static Block electricityGeneratorSolar = new BlockElectricGenerator("solarGenerator", 2);
    public static Block electricityGeneratorWind = new BlockElectricGenerator("windGenerator", 3);
    public static Block electricityGeneratorNuclear = new BlockElectricGenerator("nuclearGenerator", 4);
    public static Block electricityGeneratorMagic = new BlockElectricGenerator("magicGenerator", 5);
    
    public static Block fluidPetroleum = new BlockFluidPetroleum();
    // machine
    public static Block machineFurnace = new BlockMachineEleFurnace();
    // computer
    public static Block simpleComputer = new BlockComputerCase.SimpleComputer();
    public static Block highComputer = new BlockComputerCase.HighComputer();
    public static Block proComputer = new BlockComputerCase.ProComputer();
    public static Block superComputer = new BlockComputerCase.SuperComputer();
    
    // color egg
    public static Block dawnPortal = new BlockDawnPortal();
    
    public BlockLoader(FMLPreInitializationEvent event)
    {
        // Furniture
        register(wchest, "super_chest");
        // magnet
        register(magnetOre, "magnet_ore");
        register(magnetBlock, "magnet_block");
        // register(magnetDoor, "magnet_door");
        // energy
        register(electricityPipe, "electric_pipe");
        
        register(electricityGeneratorHeat, "heat_generator");
        register(electricityGeneratorFluid, "fluid_generator");
        register(electricityGeneratorSolar, "solar_generator");
        register(electricityGeneratorWind, "wind_generator");
        register(electricityGeneratorNuclear, "nuclear_generator");
        register(electricityGeneratorMagic, "magic_generator");
        
        register(fluidPetroleum, "petroleum");
        // machine
        register(machineFurnace, "iron_furnace");
        // computer
        register(simpleComputer, "simple_computer");
        register(highComputer, "high_computer");
        register(proComputer, "pro_computer");
        register(superComputer, "super_computer");
        // color egg
        registerWithoutItem(dawnPortal, "dawn_portal");
    }
    
    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        // Furniture
        registerRender(wchest, "super_chest");
        // magnet
        registerRender(magnetOre, "magnet_ore");
        registerRender(magnetBlock, "magnet_block");
        // registerRender(magnetDoor, "magnet_door");
        // energy
        registerRender(electricityPipe, "electric_pipe");
        
        registerRender(electricityGeneratorHeat, "heat_generator");
        registerRender(electricityGeneratorFluid, "fluid_generator");
        registerRender(electricityGeneratorSolar, "solar_generator");
        registerRender(electricityGeneratorWind, "wind_generator");
        registerRender(electricityGeneratorNuclear, "nuclear_generator");
        registerRender(electricityGeneratorMagic, "magic_generator");
        
        // registerRender(fluidPetroleum, "petroleum");
        // machine
        registerRender(machineFurnace, "iron_furnace");
        // computer
        registerRender(simpleComputer, "simple_computer");
        registerRender(highComputer, "high_computer");
        registerRender(proComputer, "pro_computer");
        registerRender(superComputer, "super_computer");
        // color egg
        // registerRender(dawnPortal, "dawn_portal");
    }
    
    private static void register(Block block, String name)
    {
        GameRegistry.registerBlock(block, name);
    }
    
    private static void registerWithoutItem(Block block, String name)
    {
        GameRegistry.registerBlock(block, null, name);
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
                new ModelResourceLocation(dawncraft.MODID + ":" + name, "inventory"));
    }
    
    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block, int meta, String name)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(dawncraft.MODID + ":" + name, "inventory"));
    }
}
