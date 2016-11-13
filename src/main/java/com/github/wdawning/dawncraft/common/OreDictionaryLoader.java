package com.github.wdawning.dawncraft.common;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

import com.github.wdawning.dawncraft.item.ItemLoader;

public class OreDictionaryLoader
{
	public OreDictionaryLoader(FMLPreInitializationEvent event)
	{      
        OreDictionary.registerOre("ingotMagnet", ItemLoader.magnetIngot);
	}
}