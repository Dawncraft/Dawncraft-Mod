package io.github.dawncraft.crafting;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.item.ItemLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Register ore dictionary.
 *
 * @author QingChenW
 *
 */
public class OreDictionaryLoader
{
    public OreDictionaryLoader(FMLPreInitializationEvent event)
    {
        OreDictionary.registerOre("ingotMagnet", ItemLoader.magnetIngot);
        OreDictionary.registerOre("oreMagnet", BlockLoader.magnetOre);
        OreDictionary.registerOre("blockMagnet", BlockLoader.magnetBlock);
        OreDictionary.registerOre("ingotCopper", ItemLoader.copperIngot);
        OreDictionary.registerOre("oreCopper", BlockLoader.copperOre);
        //        OreDictionary.registerOre("blockCopper", BlockLoader.copperBlock);
        OreDictionary.registerOre("dustMagic", ItemLoader.magicDust);
        OreDictionary.registerOre("oreMagic", BlockLoader.magicOre);
    }
}
