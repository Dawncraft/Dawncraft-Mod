package com.github.wdawning.dawncraft.common;

import com.github.wdawning.dawncraft.achievement.AchievementLoader;
import com.github.wdawning.dawncraft.block.BlockLoader;
import com.github.wdawning.dawncraft.crafting.CraftingLoader;
import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;
import com.github.wdawning.dawncraft.entity.EntityLoader;
import com.github.wdawning.dawncraft.fluid.FluidLoader;
import com.github.wdawning.dawncraft.item.ItemLoader;
import com.github.wdawning.dawncraft.potion.PotionLoader;
import com.github.wdawning.dawncraft.tileentity.TileEntityLoader;
import com.github.wdawning.dawncraft.worldgen.WorldGeneratorLoader;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        new ConfigLoader(event);
        new FluidLoader(event);
        new CreativeTabsLoader(event);
        new BlockLoader(event);
        new ItemLoader(event);
        new PotionLoader(event);
        new TileEntityLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {
        new EventLoader();
        new CraftingLoader();
        new EntityLoader();
        new WorldGeneratorLoader();
        new AchievementLoader();
        //    new EnchantmentLoader();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}