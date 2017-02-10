package io.github.dawncraft.crafting;

import io.github.dawncraft.item.ItemLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
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
    }
}
