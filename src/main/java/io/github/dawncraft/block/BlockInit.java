package io.github.dawncraft.block;

import io.github.dawncraft.api.block.BlockFurniture.EnumMaterialType;
import io.github.dawncraft.api.block.BlockOre;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.fluid.FluidInit;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Register some blocks.
 *
 * @author QingChenW
 */
public class BlockInit
{
    // Energy
    public static Block fluidPetroleum = null;

    public static Block electricCable = null;

    public static Block energyGeneratorHeat = null;
    public static Block energyGeneratorFluid = null;
    public static Block energyGeneratorSolar = null;
    public static Block energyGeneratorWind = null;
    public static Block energyGeneratorNuclear = null;
    public static Block energyGeneratorMagic = null;

    // Magnet
    public static Block magnetOre = null;
    public static Block magnetBlock = null;
    public static Block magnetDoor = null;
    public static Block magnetRail = null;
    public static Block magnetChest = null;

    // Machine
    public static Block copperOre = null;
    public static Block copperBlock = null;
    public static Block machineFurnace = null;

    // Computer
    public static Block simpleComputer = null;
    public static Block advancedComputer = null;
    public static Block superComputer = null;

    // Materials

    // Furniture
    public static Block woodTable = null;
    public static Block stoneTable = null;
    public static Block woodChair = null;
    public static Block stoneChair = null;
    public static Block alarmClock = null;

    // War

    // Magic
    public static Block magicOre = null;

    // Guns

    // ColourEgg
    public static Block superChest = null;
    public static Block dawnPortal = null;
    public static Block skull = null;

    public static void initBlocks()
    {
        // Building blocks
        Block block = new BlockOre().setTranslationKey("magnetOre").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        block.setHarvestLevel("pickaxe", 1);
        registerBlock(block, "magnet_ore");
        registerBlock(new Block(Material.IRON).setTranslationKey("magnetBlock").setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(5.0f).setResistance(10.0f).setSoundType(SoundType.METAL), "magnet_block");

        registerBlock(new BlockOre().setTranslationKey("copperOre").setCreativeTab(CreativeTabsLoader.tabMachine), "copper_ore");
        registerBlock(new Block(Material.IRON).setTranslationKey("copperBlock").setCreativeTab(CreativeTabsLoader.tabMachine).setHardness(5.0f).setResistance(10.0f).setSoundType(SoundType.METAL), "copper_block");

        // Decorations
        registerBlock(new BlockMagnetChest().setTranslationKey("magnetChest").setCreativeTab(CreativeTabs.DECORATIONS), "magnet_chest");

        // Redstone
        registerBlock(new BlockMagnetDoor().setTranslationKey("magnetDoor").setCreativeTab(CreativeTabs.REDSTONE), "magnet_door");
        registerBlock(new BlockMagnetRail().setTranslationKey("magnetRail").setCreativeTab(CreativeTabs.REDSTONE), "magnet_rail");

        // Material/Misc
        registerBlock(new BlockFluidClassic(FluidInit.PETROLEUM, Material.WATER).setTranslationKey("fluidPetroleum"), "fluid_petroleum");

        // Science

        // Energy
        registerBlock(new BlockElectricCable().setTranslationKey("electricityCable").setCreativeTab(CreativeTabsLoader.tabEnergy), "electric_cable");
        registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.HEAT).setTranslationKey("heatGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy), "heat_generator");
        registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.FLUID).setTranslationKey("fluidGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy), "fluid_generator");
        registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.SOLAR).setTranslationKey("solarGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy), "solar_generator");
        registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.WIND).setTranslationKey("windGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy), "wind_generator");
        registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.NUCLEAR).setTranslationKey("nuclearGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy), "nuclear_generator");
        registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.MAGIC).setTranslationKey("magicGenerator").setCreativeTab(CreativeTabsLoader.tabEnergy), "magic_generator");

        // Machine
        registerBlock(new BlockMachineFurnace().setTranslationKey("machineFurnace").setCreativeTab(CreativeTabsLoader.tabMachine), "iron_furnace");

        // Computer
        registerBlock(new BlockComputerCase(BlockComputerCase.EnumCaseType.SIMPLE).setTranslationKey("simpleComputer").setCreativeTab(CreativeTabsLoader.tabComputer), "simple_computer");
        registerBlock(new BlockComputerCase(BlockComputerCase.EnumCaseType.ADVANCED).setTranslationKey("advancedComputer").setCreativeTab(CreativeTabsLoader.tabComputer), "advanced_computer");
        registerBlock(new BlockComputerCase(BlockComputerCase.EnumCaseType.PROFESSIONAL).setTranslationKey("superComputer").setCreativeTab(CreativeTabsLoader.tabComputer), "super_computer");

        // Furniture
        registerBlock(new BlockFurnitureTable(EnumMaterialType.WOOD).setTranslationKey("woodTable").setCreativeTab(CreativeTabsLoader.tabFurniture), "wood_table");
        registerBlock(new BlockFurnitureTable(EnumMaterialType.STONE).setTranslationKey("stoneTable").setCreativeTab(CreativeTabsLoader.tabFurniture), "stone_table");
        registerBlock(new BlockFurnitureChair(EnumMaterialType.WOOD).setTranslationKey("woodChair").setCreativeTab(CreativeTabsLoader.tabFurniture), "wood_chair");
        registerBlock(new BlockFurnitureChair(EnumMaterialType.STONE).setTranslationKey("stoneChair").setCreativeTab(CreativeTabsLoader.tabFurniture), "stone_chair");
        registerBlock(new BlockFurnitureAlarmClock().setTranslationKey("alarmClock").setCreativeTab(CreativeTabsLoader.tabFurniture), "alarm_clock");

        // Cuisine

        // Weapons

        // Magic
        registerBlock(new BlockOre(1, 2)
        {
            @Override
            public Item getMineral()
            {
                return ItemInit.magicDust;
            }
        }.setDroppedExp(5, 7).setTranslationKey("magicOre").setCreativeTab(CreativeTabsLoader.tabMagic), "magic_ore");

        // ColourEgg
        registerBlock(new BlockFurnitureSuperChest().setTranslationKey("superChest").setCreativeTab(CreativeTabsLoader.tabColourEgg), "super_chest");
        registerBlock(new BlockDawnPortal().setTranslationKey("dawnPortal"), "dawn_portal");
        registerBlock(new BlockDawnSkull().setTranslationKey("skull"), "skull");
    }

    /**
     * Register a block with a string id.
     *
     * @param block The block to register
     * @param name The block's string id
     */
    private static void registerBlock(Block block, String name)
    {
        ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
    }
}
