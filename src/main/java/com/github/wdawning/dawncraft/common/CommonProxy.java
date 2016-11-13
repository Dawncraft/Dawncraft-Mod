package com.github.wdawning.dawncraft.common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import com.github.wdawning.dawncraft.block.BlockLoader;
import com.github.wdawning.dawncraft.client.gui.GuiLoader;
import com.github.wdawning.dawncraft.enchantment.EnchantmentLoader;
import com.github.wdawning.dawncraft.entity.EntityLoader;
import com.github.wdawning.dawncraft.fluid.FluidLoader;
import com.github.wdawning.dawncraft.item.ItemLoader;
import com.github.wdawning.dawncraft.network.NetworkLoader;
import com.github.wdawning.dawncraft.potion.PotionLoader;
import com.github.wdawning.dawncraft.server.command.CommandLoader;
import com.github.wdawning.dawncraft.tileentity.TileEntityLoader;
import com.github.wdawning.dawncraft.worldgen.WorldGeneratorLoader;

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
        new WorldGeneratorLoader();
        new CraftingLoader();
        new EntityLoader();
        new EnchantmentLoader();
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
    
    public void interModComms(IMCEvent event)
    {
        
    }
}
