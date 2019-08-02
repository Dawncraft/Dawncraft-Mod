package io.github.dawncraft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.command.CommandInit;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.container.GuiLoader;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.entity.FakePlayerLoader;
import io.github.dawncraft.fluid.FluidInit;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.recipe.BrewingLoader;
import io.github.dawncraft.recipe.CraftingLoader;
import io.github.dawncraft.recipe.LearningLoader;
import io.github.dawncraft.recipe.OreDictionaryLoader;
import io.github.dawncraft.recipe.SmeltingLoader;
import io.github.dawncraft.stats.StatLoader;
import io.github.dawncraft.tileentity.TileEntityLoader;
import io.github.dawncraft.util.DawnEnumHelper;
import io.github.dawncraft.util.Metrics;
import io.github.dawncraft.util.ScriptHelper;
import io.github.dawncraft.world.WorldInit;
import io.github.dawncraft.world.gen.feature.GeneratorLoader;
import net.minecraft.command.ICommand;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * The common proxy of Dawncraft Mod.
 *
 * @author QingChenW
 */
public class CommonProxy
{
    public static Metrics metrics;
    public static final HoverEvent.Action SHOW_SKILL = DawnEnumHelper.addHoverActionType("SHOW_SKILL", "show_skill", true);

    public void preInit(FMLPreInitializationEvent event)
    {
        LogLoader.init(event.getModLog());
        ConfigLoader.init(event.getSuggestedConfigurationFile());
        CommandInit.initReflections();
        CapabilityLoader.initCapabilities();
        FluidInit.initFluids();
        TileEntityLoader.initTileEntities();
    }

    public void init(FMLInitializationEvent event)
    {
        CreativeTabsLoader.initCreativeTabs();
        OreDictionaryLoader.initOreDictionary();
        CraftingLoader.initCrafting();
        SmeltingLoader.initSmelting();
        BrewingLoader.initBrewing();
        LearningLoader.initLearning();
        StatLoader.initStats();
        GeneratorLoader.initGenerators();
        WorldInit.initWorlds();
        FakePlayerLoader.initFakePlayers();
        NetworkLoader.initNetwork();
        GuiLoader.initGuiHandler();
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        ScriptHelper.runScripts();
        try
        {
            metrics = new Metrics(Dawncraft.NAME, Dawncraft.VERSION);
        }
        catch (IOException e)
        {
            LogLoader.logger().error("Can't load metrics:", e);
        }
    }

    public void serverStarting(FMLServerStartingEvent event)
    {
        List<ICommand> commands = new ArrayList<>();
        CommandInit.registerCommands(commands);
        commands.forEach(command ->
        {
            event.registerServerCommand(command);
        });

        Metrics.setServer(event.getServer());
        metrics.start();
    }

    public void interModComms(IMCEvent event)
    {

    }
}
