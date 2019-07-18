package io.github.dawncraft.block;

import io.github.dawncraft.api.block.BlockFurniture.EnumMaterialType;
import io.github.dawncraft.api.block.BlockOre;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.fluid.FluidLoader;
import io.github.dawncraft.item.ItemInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;

/**
 * Register Blocks.
 *
 * @author QingChenW
 */
public class BlockLoader
{
    // Energy
    public static Block fluidPetroleum = new BlockFluidClassic(FluidLoader.fluidPetroleum, Material.WATER).setTranslationKey("fluidPetroleum");

    public static Block electricCable = new BlockElectricCable().setTranslationKey("electricityCable").setCreativeTab(CreativeTabsLoader.tabEnergy);

    public static Block energyGeneratorHeat = new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.HEAT).setTranslationKey("heatGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy);
    public static Block energyGeneratorFluid = new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.FLUID).setTranslationKey("fluidGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy);
    public static Block energyGeneratorSolar = new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.SOLAR).setTranslationKey("solarGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy);
    public static Block energyGeneratorWind = new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.WIND).setTranslationKey("windGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy);
    public static Block energyGeneratorNuclear = new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.NUCLEAR).setTranslationKey("nuclearGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy);
    public static Block energyGeneratorMagic = new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.MAGIC).setTranslationKey("magicGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy);

    // Magnet
    public static Block magnetOre = new BlockOre().setTranslationKey("magnetOre").setCreativeTab(CreativeTabsLoader.tabMagnet);
    public static Block magnetBlock = new Block(Material.IRON).setTranslationKey("magnetBlock").setCreativeTab(CreativeTabsLoader.tabMagnet).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston);
    public static Block magnetDoor = new BlockMagnetDoor().setTranslationKey("magnetDoor").setCreativeTab(CreativeTabsLoader.tabMagnet);
    public static Block magnetRail = new BlockMagnetRail().setTranslationKey("magnetRail").setCreativeTab(CreativeTabsLoader.tabMagnet);
    public static Block magnetChest = new BlockMagnetChest().setTranslationKey("magnetChest").setCreativeTab(CreativeTabsLoader.tabMagnet);

    // Machine
    public static Block copperOre = new BlockOre().setTranslationKey("copperOre").setCreativeTab(CreativeTabsLoader.tabMachine);
    public static Block copperBlock = new Block(Material.IRON).setTranslationKey("copperBlock").setCreativeTab(CreativeTabsLoader.tabMachine).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston);
    public static Block machineFurnace = new BlockMachineFurnace().setTranslationKey("machineFurnace").setCreativeTab(CreativeTabsLoader.tabMachine);

    // Computer
    public static Block simpleComputer = new BlockComputerCase(BlockComputerCase.EnumCaseType.SIMPLE).setTranslationKey("simpleComputer").setCreativeTab(CreativeTabsLoader.tabComputer);
    public static Block advancedComputer = new BlockComputerCase(BlockComputerCase.EnumCaseType.ADVANCED).setTranslationKey("advancedComputer").setCreativeTab(CreativeTabsLoader.tabComputer);
    public static Block superComputer = new BlockComputerCase(BlockComputerCase.EnumCaseType.PROFESSIONAL).setTranslationKey("superComputer").setCreativeTab(CreativeTabsLoader.tabComputer);

    // Materials

    // Furniture
    public static Block woodTable = new BlockFurnitureTable(EnumMaterialType.WOOD).setTranslationKey("woodTable").setCreativeTab(CreativeTabsLoader.tabFurniture);
    public static Block stoneTable = new BlockFurnitureTable(EnumMaterialType.STONE).setTranslationKey("stoneTable").setCreativeTab(CreativeTabsLoader.tabFurniture);
    public static Block woodChair = new BlockFurnitureChair(EnumMaterialType.WOOD).setTranslationKey("woodChair").setCreativeTab(CreativeTabsLoader.tabFurniture);
    public static Block stoneChair = new BlockFurnitureChair(EnumMaterialType.STONE).setTranslationKey("stoneChair").setCreativeTab(CreativeTabsLoader.tabFurniture);
    public static Block alarmClock = new BlockFurnitureAlarmClock().setTranslationKey("alarmClock").setCreativeTab(CreativeTabsLoader.tabFurniture);

    // War

    // Magic
    public static Block magicOre = new BlockOre(1, 2)
    {
        @Override
        public Item getMineral()
        {
            return ItemInitializer.magicDust;
        }
    }.setDroppedExp(5, 7).setTranslationKey("magicOre").setCreativeTab(CreativeTabsLoader.tabMagic);

    // Guns

    // ColourEgg
    public static Block superChest = new BlockFurnitureSuperChest().setTranslationKey("superChest").setCreativeTab(CreativeTabsLoader.tabColourEgg);
    public static Block dawnPortal = new BlockDawnPortal().setTranslationKey("dawnPortal");
    public static Block skull = new BlockDawnSkull().setTranslationKey("skull");

    public static void initBlocks()
    {
        // Energy
        registerBlock(fluidPetroleum, "fluid_petroleum");

        registerBlock(electricCable, "electric_cable");

        registerBlock(energyGeneratorHeat, "heat_generator");
        //        registerBlock(energyGeneratorFluid, "fluid_generator");
        //        registerBlock(energyGeneratorSolar, "solar_generator");
        //        registerBlock(energyGeneratorWind, "wind_generator");
        //        registerBlock(energyGeneratorNuclear, "nuclear_generator");
        //        registerBlock(energyGeneratorMagic, "magic_generator");
        // Magnet
        registerBlock(magnetOre, "magnet_ore");
        magnetBlock.setHarvestLevel("pickaxe", 1);
        registerBlock(magnetBlock, "magnet_block");
        registerBlock(magnetDoor, ItemInitializer.magnetDoor, "magnet_door");
        registerBlock(magnetRail, "magnet_rail");
        registerBlock(magnetChest, "magnet_chest");

        // Machine
        registerBlock(copperOre, "copper_ore");
        registerBlock(copperBlock, "copper_block");
        registerBlock(machineFurnace, "iron_furnace");

        // Computer
        registerBlock(simpleComputer, "simple_computer");
        registerBlock(advancedComputer, "advanced_computer");
        registerBlock(superComputer, "super_computer");

        // Science

        // Furniture
        registerBlock(woodTable, "wood_table");
        registerBlock(stoneTable, "stone_table");
        registerBlock(woodChair, "wood_chair");
        registerBlock(stoneChair, "stone_chair");
        registerBlock(alarmClock, "alarm_clock");

        // Cuisine

        // War

        // Magic
        registerBlock(magicOre, "magic_ore");

        // ColourEgg
        registerBlock(superChest, "super_chest");
        registerBlock(dawnPortal, (Class) null, "dawn_portal");
        registerBlock(skull, ItemInitializer.skull, "skull");
    }

    /**
     * Register a block with a string id and a default block item.
     *
     * @param block The block to be registered
     * @param name The block's string id
     */
    private static void registerBlock(Block block, String name)
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
    private static void registerBlock(Block block, Class<? extends ItemBlock> itemClass, String name)
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
    private static void registerBlock(Block block, Class<? extends ItemBlock> itemClass, String name, Object... args)
    {
        GameRegistry.registerBlock(block, itemClass, name, args);
    }

    /**
     * Register a block with a string id and a block item which is existed.
     * <br>Not recommend, use {@link #registerBlock(Block, Class, String)} instead.<br/>
     *
     * @param block The block to be registered
     * @param item The block item
     * @param name The block's string id
     */
    @Deprecated
    private static void registerBlock(Block block, Item item, String name)
    {
        registerBlock(block, (Class<? extends ItemBlock>) null, name);
        GameData.getBlockItemMap().put(block, item);
    }
}
