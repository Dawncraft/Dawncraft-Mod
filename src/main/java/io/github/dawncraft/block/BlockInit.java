package io.github.dawncraft.block;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.block.BlockFurniture.EnumMaterialType;
import io.github.dawncraft.api.block.BlockOre;
import io.github.dawncraft.api.block.BlockSkull;
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

/**
 * Register some blocks.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class BlockInit
{
    // Building blocks
    public static Block COPPER_ORE;
    public static Block COPPER_BLOCK;

    // Decorations
    

    // Redstone
    

    // Material/Misc
    public static Block PETROLEUM;

    // Science

    // Energy
    public static Block ELECTRIC_CABLE;

    public static Block HEAT_GENERATOR;
    public static Block FLUID_GENERATOR;
    public static Block WIND_GENERATOR;
    public static Block SOLAR_GENERATOR;
    public static Block NUCLEAR_GENERATOR;
    public static Block MAGIC_GENERATOR;

    // Machine
    public static Block MACHINE_FURNACE;

    // Computer
    public static Block SIMPLE_COMPUTER;
    public static Block ADVANCED_COMPUTER;
    public static Block PROFESSIONAL_COMPUTER;

    // Furniture
    public static Block WOOD_TABLE;
    public static Block STONE_TABLE;
    public static Block WOOD_CHAIR;
    public static Block STONE_CHAIR;
    public static Block ALARM_CLOCK;

    // Cuisine

    // Weapons

    // Magic
    public static Block MAGIC_ORE;

    // ColourEgg
    public static Block SUPER_CHEST;
    public static Block DAWN_PORTAL;
    public static BlockSkull SKULL;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        // Building blocks
        COPPER_ORE = registerBlock(new BlockOre().setTranslationKey("copperOre").setCreativeTab(CreativeTabs.BUILDING_BLOCKS), "copper_ore");
        COPPER_BLOCK = registerBlock(new Block(Material.IRON).setTranslationKey("copperBlock").setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(5.0f).setResistance(10.0f).setSoundType(SoundType.METAL), "copper_block");

        

        // Decorations

        // Redstone


        // Transportation

        // Material/Misc
        PETROLEUM = registerBlock(new BlockFluidClassic(FluidInit.PETROLEUM, Material.WATER).setTranslationKey("petroleum"), "petroleum");
        FluidInit.PETROLEUM.setBlock(PETROLEUM);

        // Science

        // Energy
        ELECTRIC_CABLE = registerBlock(new BlockElectricCable().setTranslationKey("electricityCable").setCreativeTab(CreativeTabsLoader.ENERGY), "electric_cable");
        HEAT_GENERATOR = registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.HEAT).setTranslationKey("heatGenerator"), "heat_generator");
        FLUID_GENERATOR = registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.FLUID).setTranslationKey("fluidGenerator"), "fluid_generator");
        WIND_GENERATOR = registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.WIND).setTranslationKey("windGenerator"), "wind_generator");
        SOLAR_GENERATOR = registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.SOLAR).setTranslationKey("solarGenerator"), "solar_generator");
        NUCLEAR_GENERATOR = registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.NUCLEAR).setTranslationKey("nuclearGenerator"), "nuclear_generator");
        MAGIC_GENERATOR = registerBlock(new BlockEnergyGenerator(BlockEnergyGenerator.EnumGeneratorType.MAGIC).setTranslationKey("magicGenerator"), "magic_generator");

        // Machine
        MACHINE_FURNACE = registerBlock(new BlockMachineFurnace().setTranslationKey("machineFurnace"), "machine_furnace");

        // Computer
        SIMPLE_COMPUTER = registerBlock(new BlockComputerCase(BlockComputerCase.EnumCaseType.SIMPLE).setTranslationKey("simpleComputer"), "simple_computer");
        ADVANCED_COMPUTER = registerBlock(new BlockComputerCase(BlockComputerCase.EnumCaseType.ADVANCED).setTranslationKey("advancedComputer"), "advanced_computer");
        PROFESSIONAL_COMPUTER = registerBlock(new BlockComputerCase(BlockComputerCase.EnumCaseType.PROFESSIONAL).setTranslationKey("professionalComputer"), "professional_computer");

        // Furniture
        WOOD_TABLE = registerBlock(new BlockFurnitureTable(EnumMaterialType.WOOD).setTranslationKey("woodTable"), "wood_table");
        STONE_TABLE = registerBlock(new BlockFurnitureTable(EnumMaterialType.STONE).setTranslationKey("stoneTable"), "stone_table");
        WOOD_CHAIR = registerBlock(new BlockFurnitureChair(EnumMaterialType.WOOD).setTranslationKey("woodChair"), "wood_chair");
        STONE_CHAIR = registerBlock(new BlockFurnitureChair(EnumMaterialType.STONE).setTranslationKey("stoneChair"), "stone_chair");
        ALARM_CLOCK = registerBlock(new BlockFurnitureAlarmClock().setTranslationKey("alarmClock").setCreativeTab(CreativeTabsLoader.FURNITURE), "alarm_clock");

        // Cuisine

        // Weapons

        // Magic
        MAGIC_ORE = registerBlock(new BlockOre(1, 2)
        {
            @Override
            public Item getMineral()
            {
                return ItemInit.MAGIC_DUST;
            }
        }.setDroppedExp(5, 7).setTranslationKey("magicOre").setCreativeTab(CreativeTabsLoader.MAGIC), "magic_ore");

        // ColourEgg
        SUPER_CHEST = registerBlock(new BlockFurnitureSuperChest().setTranslationKey("superChest").setCreativeTab(CreativeTabsLoader.COLOUR_EGG), "super_chest");
        DAWN_PORTAL = registerBlock(new BlockDawnPortal().setTranslationKey("dawnPortal"), "dawn_portal");
        SKULL = (BlockSkull) registerBlock(new BlockSkullDawn().setTranslationKey("skull"), "skull");
    }

    /**
     * Register a block with a string id.
     *
     * @param block The block to register
     * @param name The block's string id
     */
    private static Block registerBlock(Block block, String name)
    {
        ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
        return block;
    }
}
