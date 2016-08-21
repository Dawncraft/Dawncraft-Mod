package com.github.wdawning.dawncraft.common;

import com.github.wdawning.dawncraft.achievement.AchievementLoader;
import com.github.wdawning.dawncraft.block.BlockLoader;
import com.github.wdawning.dawncraft.command.CommandLoader;
import com.github.wdawning.dawncraft.crafting.CraftingLoader;
import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;
import com.github.wdawning.dawncraft.enchantment.EnchantmentLoader;
import com.github.wdawning.dawncraft.entity.EntityLoader;
import com.github.wdawning.dawncraft.fluid.FluidLoader;
import com.github.wdawning.dawncraft.gui.ContainerEleHeatGenerator;
import com.github.wdawning.dawncraft.gui.GuiLoader;
import com.github.wdawning.dawncraft.item.ItemLoader;
import com.github.wdawning.dawncraft.network.NetworkLoader;
import com.github.wdawning.dawncraft.potion.PotionLoader;
import com.github.wdawning.dawncraft.tileentity.TileEntityEleHeatGenerator;
import com.github.wdawning.dawncraft.tileentity.TileEntityLoader;
import com.github.wdawning.dawncraft.worldgen.WorldGeneratorLoader;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        new ConfigLoader(event);
        new FluidLoader(event);
        new CreativeTabsLoader(event);
        new ItemLoader(event);
        new BlockLoader(event);
        new OreDictionaryLoader(event);
        new PotionLoader(event);
        new TileEntityLoader(event);
        new GuiLoader(event);
        new NetworkLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {
        new CraftingLoader();
        new EntityLoader();
        new EnchantmentLoader();
        new WorldGeneratorLoader();
        new AchievementLoader();
        new EventLoader();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

	public void serverStarting(FMLServerStartingEvent event)
	{
		new CommandLoader(event);
	}
}