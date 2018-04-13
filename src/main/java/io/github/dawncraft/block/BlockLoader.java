package io.github.dawncraft.block;

import io.github.dawncraft.api.block.*;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.fluid.FluidLoader;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Register Blocks.
 *
 * @author QingChenW
 */
public class BlockLoader
{
    // Energy
    public static Block fluidPetroleum = new BlockFluidClassic(FluidLoader.fluidPetroleum, Material.water)
            .setUnlocalizedName("fluidPetroleum");
    
    public static Block electricCable = new BlockElectricCable().setUnlocalizedName("electricityCable")
            .setCreativeTab(CreativeTabsLoader.tabEnergy);
    
    public static Block energyGeneratorHeat = new BlockEnergyGenerator(BlockEnergyGenerator.EnergyGeneratorType.HEAT)
            .setUnlocalizedName("heatGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy);
    public static Block energyGeneratorFluid = new BlockEnergyGenerator(BlockEnergyGenerator.EnergyGeneratorType.FLUID)
            .setUnlocalizedName("fluidGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy);
    public static Block energyGeneratorSolar = new BlockEnergyGenerator(BlockEnergyGenerator.EnergyGeneratorType.SOLAR)
            .setUnlocalizedName("solarGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy);
    public static Block energyGeneratorWind = new BlockEnergyGenerator(BlockEnergyGenerator.EnergyGeneratorType.WIND)
            .setUnlocalizedName("windGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy);
    public static Block energyGeneratorNuclear = new BlockEnergyGenerator(
            BlockEnergyGenerator.EnergyGeneratorType.NUCLEAR).setUnlocalizedName("nuclearGenerator")
            .setCreativeTab(CreativeTabsLoader.tabEnergy);
    public static Block energyGeneratorMagic = new BlockEnergyGenerator(BlockEnergyGenerator.EnergyGeneratorType.MAGIC)
            .setUnlocalizedName("magicGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy);
    
    // Magnet
    public static Block magnetOre = new BlockOreBase().setUnlocalizedName("magnetOre")
            .setCreativeTab(CreativeTabsLoader.tabMagnetism);
    public static Block magnetBlock = new Block(Material.iron).setUnlocalizedName("magnetBlock")
            .setCreativeTab(CreativeTabsLoader.tabMagnetism).setHardness(5.0f).setResistance(10.0f)
            .setStepSound(Block.soundTypePiston);
    public static Block magnetDoor = new BlockMagnetDoor().setUnlocalizedName("magnetDoor")
            .setCreativeTab(CreativeTabsLoader.tabMagnetism);
    public static Block magnetRail = new BlockMagnetRail().setUnlocalizedName("magnetRail")
            .setCreativeTab(CreativeTabsLoader.tabMagnetism);
    
    // Machine
    public static Block copperOre = new BlockOreBase().setUnlocalizedName("copperOre")
            .setCreativeTab(CreativeTabsLoader.tabMachine);
    public static Block copperBlock = new Block(Material.iron).setUnlocalizedName("copperBlock")
            .setCreativeTab(CreativeTabsLoader.tabMachine).setHardness(5.0f).setResistance(10.0f)
            .setStepSound(Block.soundTypePiston);
    public static Block machineFurnace = new BlockMachineFurnace().setUnlocalizedName("machineFurnace")
            .setCreativeTab(CreativeTabsLoader.tabMachine);
    
    // Computer
    public static Block simpleComputer = new BlockComputerCase(BlockComputerCase.ComputerCaseType.SIMPLE)
            .setUnlocalizedName("simpleComputer").setCreativeTab(CreativeTabsLoader.tabComputer);
    public static Block advancedComputer = new BlockComputerCase(BlockComputerCase.ComputerCaseType.ADVANCED)
            .setUnlocalizedName("advancedComputer").setCreativeTab(CreativeTabsLoader.tabComputer);
    public static Block superComputer = new BlockComputerCase(BlockComputerCase.ComputerCaseType.SUPER)
            .setUnlocalizedName("superComputer").setCreativeTab(CreativeTabsLoader.tabComputer);
    
    // Materials
    
    // Furniture
    public static Block woodTable = new BlockFurnitureTable(Material.wood, Block.soundTypeWood)
            .setUnlocalizedName("woodTable").setCreativeTab(CreativeTabsLoader.tabFurniture);
    public static Block stoneTable = new BlockFurnitureTable(Material.rock, Block.soundTypeStone)
            .setUnlocalizedName("stoneTable").setCreativeTab(CreativeTabsLoader.tabFurniture);
    public static Block woodChair = new BlockFurnitureChair(Material.wood, Block.soundTypeWood)
            .setUnlocalizedName("woodChair").setCreativeTab(CreativeTabsLoader.tabFurniture);
    public static Block stoneChair = new BlockFurnitureChair(Material.rock, Block.soundTypeStone)
            .setUnlocalizedName("stoneChair").setCreativeTab(CreativeTabsLoader.tabFurniture);
    public static Block superChest = new BlockFurnitureSuperChest().setUnlocalizedName("superChest")
            .setCreativeTab(CreativeTabsLoader.tabFurniture);

    // Food
    
    // Magic
    public static Block magicOre = new BlockOreBase(1, 2)
    {
        @Override
        public Item getMineral()
        {
            return ItemLoader.magicDust;
        }
    }.setDroppedExp(5, 7).setUnlocalizedName("magicOre").setCreativeTab(CreativeTabsLoader.tabMagic);
    
    // Guns
    
    // ColourEgg
    public static Block dawnPortal = new BlockDawnPortal().setUnlocalizedName("dawnPortal");
    public static Block skull = new BlockDawnSkull().setUnlocalizedName("skull");

    public BlockLoader(FMLPreInitializationEvent event)
    {
        // Energy
        register(fluidPetroleum, "fluid_petroleum");

        register(electricCable, "electric_cable");

        register(energyGeneratorHeat, "heat_generator");
        //        register(energyGeneratorFluid, "fluid_generator");
        //        register(energyGeneratorSolar, "solar_generator");
        //        register(energyGeneratorWind, "wind_generator");
        //        register(energyGeneratorNuclear, "nuclear_generator");
        //        register(energyGeneratorMagic, "magic_generator");
        // Magnet
        register(magnetOre, "magnet_ore");
        magnetBlock.setHarvestLevel("ItemPickaxe", 1);
        register(magnetBlock, "magnet_block");
        register(magnetDoor, ItemLoader.magnetDoor, "magnet_door");
        register(magnetRail, "magnet_rail");
        
        // Machine
        register(copperOre, "copper_ore");
        register(copperBlock, "copper_block");
        register(machineFurnace, "iron_furnace");
        
        // Computer
        register(simpleComputer, "simple_computer");
        register(advancedComputer, "advanced_computer");
        register(superComputer, "super_computer");

        // Materials
        
        // Furniture
        register(woodTable, "wood_table");
        register(stoneTable, "stone_table");
        register(woodChair, "wood_chair");
        register(stoneChair, "stone_chair");
        register(superChest, "super_chest");
        
        // Food
        
        // Magic
        register(magicOre, "magic_ore");
        
        // Guns
        
        // ColourEgg
        register(dawnPortal, (Class) null, "dawn_portal");
        register(skull, ItemLoader.skull, "skull");
    }

    /**
     * Register a block with a string id and a default block item.
     *
     * @param block The block to be registered
     * @param name The block's string id
     */
    private static void register(Block block, String name)
    {
        GameRegistry.registerBlock(block, name);
    }

    /**
     * Register a block with a string id and a block item which has no arguments.
     *
     * @param block The block to be registered
     * @param itemClass The block item's class
     * @param name The block's string id
     */
    private static void register(Block block, Class<? extends ItemBlock> itemClass, String name)
    {
        GameRegistry.registerBlock(block, itemClass, name);
    }

    /**
     * Register a block with a string id and a block item which has some arguments.
     *
     * @param block The block to be registered
     * @param itemClass The block item's class
     * @param name The block's string id
     * @param args The block item's constructor arguments
     */
    private static void register(Block block, Class<? extends ItemBlock> itemClass, String name, Object... args)
    {
        GameRegistry.registerBlock(block, itemClass, name, args);
    }

    /**
     * Register a block with a string id and a block item which is existed.
     * <br>Not recommend, use {@link #register(Block, Class, String)} instead.<br/>
     *
     * @param block The block to be registered
     * @param item The block item
     * @param name The block's string id
     */
    @Deprecated
    private static void register(Block block, Item item, String name)
    {
        GameRegistry.registerBlock(block.setRegistryName(name), (Class<? extends ItemBlock>) null);
        GameData.getBlockItemMap().put(block, item);
    }
}
