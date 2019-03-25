package io.github.dawncraft.recipe;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.item.ItemLoader;
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
        registerOre("ingotMagnet", ItemLoader.magnetIngot);
        registerOre("oreMagnet", BlockLoader.magnetOre);
        registerOre("blockMagnet", BlockLoader.magnetBlock);
        registerOre("ingotCopper", ItemLoader.copperIngot);
        registerOre("oreCopper", BlockLoader.copperOre);
        registerOre("blockCopper", BlockLoader.copperBlock);
        registerOre("dustMagic", ItemLoader.magicDust);
        registerOre("oreMagic", BlockLoader.magicOre);
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
