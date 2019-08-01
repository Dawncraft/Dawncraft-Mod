package io.github.dawncraft.block;

import io.github.dawncraft.Dawncraft;
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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

/**
 * Register some blocks.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
@ObjectHolder(Dawncraft.MODID)
public class BlockInit
{
    // Building blocks
    public static final Block MAGNET_ORE = null;
    public static final Block MAGNET_BLOCK = null;

    public static final Block COPPER_ORE = null;
    public static final Block COPPER_BLOCK = null;

    // Decorations
    public static final Block MAGNET_CHEST = null;

    // Redstone
    public static final Block MAGNET_DOOR = null;
    public static final Block MAGNET_RAIL = null;

    // Material/Misc
    public static final Block PETROLEUM = null;

    // Science

    // Energy
    public static final Block ELECTRIC_CABLE = null;

    public static final Block HEAT_GENERATOR = null;
    public static final Block FLUID_GENERATOR = null;
    public static final Block SOLAR_GENERATOR = null;
    public static final Block WIND_GENERATOR = null;
    public static final Block NUCLEAR_GENERATOR = null;
    public static final Block MAGIC_GENERATOR = null;

    // Machine
    public static final Block MACHINE_FURNACE = null;

    // Computer
    public static final Block SIMPLE_COMPUTER = null;
    public static final Block ADVANCED_COMPUTER = null;
    public static final Block PROFESSIONAL_COMPUTER = null;

    // Furniture
    public static final Block WOOD_TABLE = null;
    public static final Block STONE_TABLE = null;
    public static final Block WOOD_CHAIR = null;
    public static final Block STONE_CHAIR = null;
    public static final Block ALARM_CLOCK = null;

    // Cuisine

    // Weapons

    // Magic
    public static final Block MAGIC_ORE = null;

    // ColourEgg
    public static final Block SUPER_CHEST = null;
    public static final Block DAWN_PORTAL = null;
    public static final Block SKULL = null;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
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
        registerBlock(new BlockFluidClassic(FluidInit.PETROLEUM, Material.WATER).setTranslationKey("petroleum"), "petroleum");

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
        registerBlock(new BlockComputerCase(BlockComputerCase.EnumCaseType.PROFESSIONAL).setTranslationKey("professionalComputer").setCreativeTab(CreativeTabsLoader.tabComputer), "professional_computer");

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
                return ItemInit.MAGIC_DUST;
            }
        }.setDroppedExp(5, 7).setTranslationKey("magicOre").setCreativeTab(CreativeTabsLoader.tabMagic), "magic_ore");

        // ColourEgg
        registerBlock(new BlockFurnitureSuperChest().setTranslationKey("superChest").setCreativeTab(CreativeTabsLoader.tabColourEgg), "super_chest");
        registerBlock(new BlockDawnPortal().setTranslationKey("dawnPortal"), "dawn_portal");
        registerBlock(new BlockSkullDawn().setTranslationKey("skull"), "skull");
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
