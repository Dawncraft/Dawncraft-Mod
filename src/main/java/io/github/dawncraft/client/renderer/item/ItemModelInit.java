package io.github.dawncraft.client.renderer.item;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.item.ISkullType;
import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.client.renderer.tileentity.TileEntityRendererInit;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Register items' inventory model.(Include ItemBlock)
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID, value = Side.CLIENT)
public class ItemModelInit
{
    @SubscribeEvent
    public static void registerItemModels(ModelRegistryEvent event)
    {
        // Building blocks
        registerBlock(BlockInit.MAGIC_ORE);
        registerBlock(BlockInit.MAGNET_BLOCK);

        registerBlock(BlockInit.COPPER_ORE);
        registerBlock(BlockInit.COPPER_BLOCK);

        // Decorations
        registerBlock(BlockInit.MAGNET_CHEST);

        // Redstone
        registerItem(ItemInit.MAGNET_DOOR);
        registerBlock(BlockInit.MAGNET_RAIL);
        registerItem(ItemInit.MAGNET_CARD);

        // Materials/Misc
        registerItem(ItemInit.MAGNET);
        registerItem(ItemInit.MAGNET_INGOT);
        registerItem(ItemInit.MAGNET_STICK);
        registerItem(ItemInit.MAGNET_BALL);

        registerItem(ItemInit.COPPER_INGOT);

        // Tools
        registerItem(ItemInit.WOOD_HAMMER);
        registerItem(ItemInit.STONE_HAMMER);
        registerItem(ItemInit.IRON_HAMMER);
        registerItem(ItemInit.GOLD_HAMMER);
        registerItem(ItemInit.DIAMOND_HAMMER);
        registerItem(ItemInit.MAGNET_AXE);
        registerItem(ItemInit.MAGNET_PICKAXE);
        registerItem(ItemInit.MAGNET_HAMMER);
        registerItem(ItemInit.MAGNET_SHOVEL);
        registerItem(ItemInit.MAGNET_HOE);

        // Compat
        registerItem(ItemInit.WOOD_WAND);
        registerItem(ItemInit.STONE_WAND);
        registerItem(ItemInit.IRON_WAND);
        registerItem(ItemInit.GOLD_WAND);
        registerItem(ItemInit.DIAMOND_WAND);
        registerItem(ItemInit.MAGNET_SWORD);
        registerItem(ItemInit.MAGNET_WAND);
        registerItem(ItemInit.MAGNET_HELMET);
        registerItem(ItemInit.MAGNET_CHESTPLATE);
        registerItem(ItemInit.MAGNET_LEGGINGS);
        registerItem(ItemInit.MAGNET_BOOTS);

        // Science

        // Energy
        registerBlock(BlockInit.ELECTRIC_CABLE);

        registerBlock(BlockInit.HEAT_GENERATOR);
        registerBlock(BlockInit.FLUID_GENERATOR);
        registerBlock(BlockInit.WIND_GENERATOR);
        registerBlock(BlockInit.SOLAR_GENERATOR);
        registerBlock(BlockInit.NUCLEAR_GENERATOR);
        registerBlock(BlockInit.MAGIC_GENERATOR);

        // Machine
        registerBlock(BlockInit.MACHINE_FURNACE);

        // Computer
        registerItem(ItemInit.SIMPLE_CPU);
        registerItem(ItemInit.ADVANCED_CPU);
        registerItem(ItemInit.PROFESSIONAL_CPU);

        registerBlock(BlockInit.SIMPLE_COMPUTER);
        registerBlock(BlockInit.ADVANCED_COMPUTER);
        registerBlock(BlockInit.PROFESSIONAL_COMPUTER);

        // Furniture
        registerBlock(BlockInit.WOOD_TABLE);
        registerBlock(BlockInit.STONE_TABLE);
        registerBlock(BlockInit.WOOD_CHAIR);
        registerBlock(BlockInit.STONE_CHAIR);
        registerBlock(BlockInit.ALARM_CLOCK);

        // Cuisine
        registerItem(ItemInit.TUMBLER);
        registerItem(ItemInit.FROG);
        registerItem(ItemInit.HONEY);
        registerItem(ItemInit.COOKED_EGG);
        registerItem(ItemInit.HONEY_CHICKEN);
        registerItem(ItemInit.HONEY_STEW);
        registerItem(ItemInit.FROG_STEW);

        // Weapons
        registerItem(ItemInit.GUN_AK47);
        registerItem(ItemInit.GUN_BULLET);
        registerItem(ItemInit.GUN_RPG);
        registerItem(ItemInit.GUN_ROCKET);
        registerItem(ItemInit.THROWABLE_TORCH);

        // Magic
        registerBlock(BlockInit.MAGIC_ORE);
        registerItem(ItemInit.MAGIC_DUST);
        registerItem(ItemInit.SKILL_BOOK);

        // ColourEgg
        registerBlock(BlockInit.SUPER_CHEST);
        String suffix = ItemInit.SKULL.getRegistryName() + "_";
        for (int i = 0; i < ItemInit.SKULL.skullTypes.length; i++)
        {
            ISkullType skullType = ItemInit.SKULL.skullTypes[i];
            Class<? extends Entity> entity = skullType.getEntityClass();
            String name = skullType.getEntityName().toLowerCase();
            registerItem(ItemInit.SKULL, i, suffix + name);
        }
        registerItem(ItemInit.FAECES);
        registerItem(ItemInit.GER_HEART);
        registerItem(ItemInit.BRAIN_DEAD);
        registerItem(ItemInit.FUNNY);

        registerItem(ItemInit.RECORD_DJ);
        registerItem(ItemInit.RECORD_WZSONGS);

        registerItem(ItemInit.GOLDIAMOND_SWORD);
        registerItem(ItemInit.MJOLNIR);
        registerItem(ItemInit.INFINITY_GAUNTLET);

        // TEISR
        TileEntityRendererInit.initTileEntityItemStackRenderer();
    }

    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event)
    {
        BlockColors blockColorHandler = event.getBlockColors();
    }

    /**
     * Register a item's inventory model and its variants.
     *
     * @param item item's string id
     */
    private static void registerItem(Item item)
    {
        registerItem(item, 0, item.getRegistryName().toString());
    }

    /**
     * Register a block's inventory model and its variants.
     *
     * @param block block's string id
     */
    private static void registerBlock(Block block)
    {
        registerBlock(block, 0, block.getRegistryName().toString());
    }

    /**
     * Register a item's inventory model with meta and name and its variants.
     *
     * @param item item to register
     * @param meta item's meta
     * @param name item's model name
     */
    private static void registerItem(Item item, int meta, String name)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(name, "inventory"));
    }

    /**
     * Register a block's inventory model with meta and name and its variants.
     *
     * @param block block to register
     * @param meta item's meta
     * @param name item's model name
     */
    private static void registerBlock(Block block, int meta, String name)
    {
        registerItem(Item.getItemFromBlock(block), meta, name);
    }
}
