package io.github.dawncraft.server;

import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.command.CommandLoader;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.container.GuiLoader;
import io.github.dawncraft.crafting.CraftingLoader;
import io.github.dawncraft.crafting.OreDictionaryLoader;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.enchantment.EnchantmentLoader;
import io.github.dawncraft.entity.EntityLoader;
import io.github.dawncraft.entity.FakePlayerLoader;
import io.github.dawncraft.event.EventLoader;
import io.github.dawncraft.fluid.FluidLoader;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.potion.PotionLoader;
import io.github.dawncraft.skill.SkillLoader;
import io.github.dawncraft.stats.AchievementLoader;
import io.github.dawncraft.stats.DamageSourceLoader;
import io.github.dawncraft.tileentity.TileEntityLoader;
import io.github.dawncraft.worldgen.WorldGeneratorLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;

/**
 * The common proxy of Dawncraft Mod.
 *
 * @author QingChenW
 */
public class ServerProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        new LogLoader(event);
        new ConfigLoader(event);
        new CapabilityLoader(event);
        new CreativeTabsLoader(event);
        new FluidLoader(event);
        new ItemLoader(event);
        new BlockLoader(event);
        new EntityLoader(event);
        new TileEntityLoader(event);
        new SkillLoader(event);
        new EnchantmentLoader(event);
        new PotionLoader(event);
        new OreDictionaryLoader(event);
    }
    
    public void init(FMLInitializationEvent event)
    {
        new CraftingLoader(event);
        new AchievementLoader(event);
        new DamageSourceLoader(event);
        new WorldGeneratorLoader(event);
        new EventLoader(event);
        new NetworkLoader(event);
        new FakePlayerLoader(event);
        new GuiLoader(event);
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
