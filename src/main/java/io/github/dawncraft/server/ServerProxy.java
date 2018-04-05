package io.github.dawncraft.server;

import java.io.IOException;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.block.BlockLoader;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.command.CommandLoader;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.container.GuiLoader;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.enchantment.EnchantmentLoader;
import io.github.dawncraft.entity.AttributesLoader;
import io.github.dawncraft.entity.EntityLoader;
import io.github.dawncraft.entity.FakePlayerLoader;
import io.github.dawncraft.event.EventLoader;
import io.github.dawncraft.fluid.FluidLoader;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.potion.PotionLoader;
import io.github.dawncraft.recipe.CraftingLoader;
import io.github.dawncraft.recipe.OreDictionaryLoader;
import io.github.dawncraft.skill.SkillLoader;
import io.github.dawncraft.stats.AchievementLoader;
import io.github.dawncraft.stats.DamageSourceLoader;
import io.github.dawncraft.stats.StatLoader;
import io.github.dawncraft.tileentity.TileEntityLoader;
import io.github.dawncraft.util.Metrics;
import io.github.dawncraft.util.ScriptHelper;
import io.github.dawncraft.world.WorldLoader;
import io.github.dawncraft.world.biome.BiomeLoader;
import io.github.dawncraft.world.gen.feature.GeneratorLoader;
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
        new AttributesLoader(event);
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
        new StatLoader(event);
        new AchievementLoader(event);
        new DamageSourceLoader(event);
        new WorldLoader(event);
        new BiomeLoader(event);
        new GeneratorLoader(event);
        new EventLoader(event);
        new NetworkLoader(event);
        new FakePlayerLoader(event);
        new GuiLoader(event);
    }
    
    public void postInit(FMLPostInitializationEvent event)
    {
        new ScriptHelper();
        try
        {
            Metrics metrics = new Metrics(Dawncraft.NAME, Dawncraft.VERSION);
            //metrics.start();
        }
        catch (IOException e)
        {
            LogLoader.logger().error("Can't load metrics:", e);
        }
    }
    
    public void serverStarting(FMLServerStartingEvent event)
    {
        new CommandLoader(event);
    }
    
    public void interModComms(IMCEvent event)
    {
        
    }
}
