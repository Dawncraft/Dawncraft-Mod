package io.github.dawncraft.recipe;

import io.github.dawncraft.block.BlockInit;
import io.github.dawncraft.item.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Register ore dictionary.
 *
 * @author QingChenW
 */
public class OreDictionaryLoader
{
    public static void initOreDictionary()
    {
        registerOre("ingotMagnet", ItemInit.MAGNET_INGOT);
        registerOre("oreMagnet", BlockInit.MAGIC_ORE);
        registerOre("blockMagnet", BlockInit.MAGNET_BLOCK);
        registerOre("ingotCopper", ItemInit.COPPER_INGOT);
        registerOre("oreCopper", BlockInit.COPPER_ORE);
        registerOre("blockCopper", BlockInit.COPPER_BLOCK);
        registerOre("dustMagic", ItemInit.MAGIC_DUST);
        registerOre("oreMagic", BlockInit.MAGIC_ORE);
    }

    public static void registerOre(String name, Item ore)
    {
        OreDictionary.registerOre(name, ore);
    }

    public static void registerOre(String name, Block ore)
    {
        OreDictionary.registerOre(name, ore);
    }

    public static void registerOre(String name, ItemStack ore)
    {
        OreDictionary.registerOre(name, ore);
    }
}
