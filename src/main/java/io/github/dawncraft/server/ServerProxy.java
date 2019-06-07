package io.github.dawncraft.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import io.github.dawncraft.recipe.BrewingLoader;
import io.github.dawncraft.recipe.CraftingLoader;
import io.github.dawncraft.recipe.FishingLoader;
import io.github.dawncraft.recipe.OreDictionaryLoader;
import io.github.dawncraft.recipe.SmeltingLoader;
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

import net.minecraft.command.CommandBase;

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
        LogLoader.init(event.getModLog());
        ConfigLoader.init(event.getSuggestedConfigurationFile());
        CommandLoader.initReflections();
        CapabilityLoader.initCapabilities();
        CreativeTabsLoader.initCreativeTabs();
        FluidLoader.initFluids();
        ItemLoader.initItems();
        BlockLoader.initBlocks();
        SkillLoader.initSkills();
        AttributesLoader.initAttributes();
        EntityLoader.initEntities();
        TileEntityLoader.initTileEntities();
        EnchantmentLoader.initEnchantments();
        PotionLoader.initPotions();
        OreDictionaryLoader.initOreDictionary();
    }
    
    public void init(FMLInitializationEvent event)
    {
        CraftingLoader.initCrafting();
        SmeltingLoader.initSmelting();
        BrewingLoader.initBrewing();
        FishingLoader.initFishing();
        StatLoader.initStats();
        AchievementLoader.initAchievements();
        DamageSourceLoader.initDamageSources();
        BiomeLoader.initBiomes();
        GeneratorLoader.initGenerators();
        WorldLoader.initWorlds();
        EventLoader.initEvents();
        FakePlayerLoader.initFakePlayers();
        NetworkLoader.initNetwork();
        GuiLoader.initGuiHandler();
    }
    
    public void postInit(FMLPostInitializationEvent event)
    {
        ScriptHelper.runScripts();
        try
        {
            Metrics metrics = new Metrics(Dawncraft.NAME, Dawncraft.VERSION);
            // metrics.start();
        }
        catch (IOException e)
        {
            LogLoader.logger().error("Can't load metrics:", e);
        }
    }
    
    public void serverStarting(FMLServerStartingEvent event)
    {
        List<CommandBase> commands = new ArrayList<CommandBase>();
        CommandLoader.initCommands(commands);
        for (CommandBase command : commands)
        {
            event.registerServerCommand(command);
        }
    }
    
    public void interModComms(IMCEvent event)
    {
        
    }
}
