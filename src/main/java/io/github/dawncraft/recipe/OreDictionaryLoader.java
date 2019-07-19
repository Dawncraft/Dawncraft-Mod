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
        registerOre("ingotMagnet", ItemInit.magnetIngot);
        registerOre("oreMagnet", BlockInit.magnetOre);
        registerOre("blockMagnet", BlockInit.magnetBlock);
        registerOre("ingotCopper", ItemInit.copperIngot);
        registerOre("oreCopper", BlockInit.copperOre);
        registerOre("blockCopper", BlockInit.copperBlock);
        registerOre("dustMagic", ItemInit.magicDust);
        registerOre("oreMagic", BlockInit.magicOre);
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
