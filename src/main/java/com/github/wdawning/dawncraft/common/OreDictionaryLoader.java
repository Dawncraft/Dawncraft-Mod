package com.github.wdawning.dawncraft.common;

import java.util.List;

import com.github.wdawning.dawncraft.item.ItemLoader;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryLoader
{
	public OreDictionaryLoader(FMLPreInitializationEvent event)
	{      
        OreDictionary.registerOre("ingotMagnet", ItemLoader.magnetIngot);
	}
}