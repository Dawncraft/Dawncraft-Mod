package io.github.dawncraft.client.renderer.item;

import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.client.event.TooltipEventHandler;
import io.github.dawncraft.entity.EntityUtils;
import io.github.dawncraft.item.ItemInit;
import io.github.dawncraft.item.ItemSkull;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

/**
 * Register items' inventory model.(Include ItemBlock)
 *
 * @author QingChenW
 */
public class ItemRenderInit
{
    public static void initItemRender()
    {
        // Energy
        registerBlock(BlockInit.ELECTRIC_CABLE);
        registerBlock(BlockInit.HEAT_GENERATOR);
        //        register(BlockLoader.energyGeneratorFluid);
        //        register(BlockLoader.energyGeneratorSolar);
        //        register(BlockLoader.energyGeneratorWind);
        //        register(BlockLoader.energyGeneratorNuclear);
        //        register(BlockLoader.energyGeneratorMagic);

        // Magnet
        registerItem(ItemInit.MAGNET);
        registerItem(ItemInit.MAGNET_INGOT);
        registerItem(ItemInit.MAGNET_STICK);
        registerItem(ItemInit.MAGNET_BALL);
        registerItem(ItemInit.MAGNET_CARD);
        registerItem(ItemInit.MAGNET_DOOR);

        registerItem(ItemInit.MAGNET_AXE);
        registerItem(ItemInit.MAGNET_PICKAXE);
        registerItem(ItemInit.MAGNET_HAMMER);
        registerItem(ItemInit.MAGNET_SPADE);
        registerItem(ItemInit.MAGNET_HOE);
        registerItem(ItemInit.MAGNET_SWORD);
        registerItem(ItemInit.MAGNET_WAND);
        registerItem(ItemInit.MAGNET_HELMET);
        registerItem(ItemInit.MAGNET_CHESTPLATE);
        registerItem(ItemInit.MAGNET_LEGGINGS);
        registerItem(ItemInit.MAGNET_BOOTS);

        registerBlock(BlockInit.MAGIC_ORE);
        registerBlock(BlockInit.MAGNET_BLOCK);
        registerBlock(BlockInit.MAGNET_RAIL);
        registerBlock(BlockInit.MAGNET_CHEST);

        // Machine
        registerItem(ItemInit.COPPER_INGOT);

        registerBlock(BlockInit.COPPER_ORE);
        registerBlock(BlockInit.COPPER_BLOCK);
        registerBlock(BlockInit.MACHINE_FURNACE);

        // Computer
        registerItem(ItemInit.SIMPLE_CPU);
        registerItem(ItemInit.ADVANCED_CPU);
        registerItem(ItemInit.PROFESSIONAL_CPU);

        registerBlock(BlockInit.SIMPLE_COMPUTER);
        registerBlock(BlockInit.ADVANCED_COMPUTER);
        registerBlock(BlockInit.PROFESSIONAL_COMPUTER);

        // Science

        // Furniture
        registerBlock(BlockInit.WOOD_TABLE);
        registerBlock(BlockInit.STONE_TABLE);
        registerBlock(BlockInit.WOOD_CHAIR);
        registerBlock(BlockInit.STONE_CHAIR);
        registerBlock(BlockInit.ALARM_CLOCK);

        // Cuisine
        registerItem(ItemInit.TUMBLER);
        registerItem(ItemInit.FAECES);
        registerItem(ItemInit.COOKED_EGG);
        registerItem(ItemInit.HONEY_CHICKEN);
        registerItem(ItemInit.HONEY_STEW);
        registerItem(ItemInit.FROG_STEW);
        registerItem(ItemInit.HONEY);
        registerItem(ItemInit.FROG);

        // Magic
        registerBlock(BlockInit.MAGIC_ORE);
        registerItem(ItemInit.MAGIC_DUST);
        registerItem(ItemInit.SKILL_BOOK);

        // War
        registerItem(ItemInit.GUN_AK47);
        registerItem(ItemInit.GUN_BULLET);
        registerItem(ItemInit.GUN_RPG);
        registerItem(ItemInit.GUN_ROCKET);
        registerItem(ItemInit.THROWABLE_TORCH);

        // ColourEgg
        registerBlock(BlockInit.SUPER_CHEST);
        String suffix = ItemInit.SKULL.getRegistryName() + "_";
        for (int i = 0; i < ItemSkull.skullTypes.length; i++)
        {
            Class<? extends Entity> entity = ItemSkull.skullTypes[i];
            registerItem(ItemInit.SKULL, i, suffix + EntityUtils.getEntityStringFromClass(entity).toLowerCase());
        }
        registerItem(ItemInit.GER_HEART);
        registerItem(ItemInit.BRAIN_DEAD);
        registerItem(ItemInit.FUNNY);

        registerItem(ItemInit.RECORD_DJ);
        registerItem(ItemInit.RECORD_WZSONGS);

        registerItem(ItemInit.GOLDIAMOND_SWORD);
        registerItem(ItemInit.MJOLNIR);
        registerItem(ItemInit.INFINITY_GAUNTLET);

        TooltipEventHandler.initTooltips();
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
