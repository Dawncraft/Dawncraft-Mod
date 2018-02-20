package io.github.dawncraft.api.talent;

import com.google.common.base.Strings;

import io.github.dawncraft.talent.Talent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;

/**
 *
 *
 * @author QingChenW
 */
@Deprecated
public class TalentRegistry
{
    public static final ResourceLocation Talent = new ResourceLocation("dawncraft:talents");
    private static final int MIN_TALENT_ID = 0;
    private static final int MAX_TALENT_ID = 31999;
    private final FMLControlledNamespacedRegistry<Talent> iTalentRegistry = PersistentRegistryManager.createRegistry(Talent, Talent.class, null, MAX_TALENT_ID, MIN_TALENT_ID, true);
    
    private static final TalentRegistry main = new TalentRegistry();
    
    private FMLControlledNamespacedRegistry<Talent> getRegistry()
    {
        return this.iTalentRegistry;
    }

    private void register(Talent talent, String name)
    {
        this.iTalentRegistry.register(-1, this.addPrefix(name), talent);
    }
    
    private ResourceLocation addPrefix(String name)
    {
        int index = name.lastIndexOf(':');
        String oldPrefix = index == -1 ? "" : name.substring(0, index);
        name = index == -1 ? name : name.substring(index + 1);
        String prefix;
        ModContainer mc = Loader.instance().activeModContainer();

        if (mc != null)
        {
            prefix = mc.getModId().toLowerCase();
        }
        else // no mod container, assume minecraft
        {
            prefix = "minecraft";
        }

        if (!oldPrefix.equals(prefix) && oldPrefix.length() > 0)
        {
            FMLLog.bigWarning("Dangerous alternative prefix %s for name %s, invalid registry invocation/invalid name?", prefix, name);
            prefix = oldPrefix;
        }

        return new ResourceLocation(prefix, name);
    }

    public static TalentRegistry getMain()
    {
        return main;
    }
    
    public static FMLControlledNamespacedRegistry<Talent> getTalentRegistry()
    {
        return getMain().getRegistry();
    }

    public static void registerTalent(Talent talent)
    {
        registerTalent(talent, talent.getRegistryName());
    }

    public static void registerTalent(Talent talent, String name)
    {
        if (Strings.isNullOrEmpty(name))
        {
            throw new IllegalArgumentException("Attempted to register a talent with no name: " + talent);
        }
        getMain().register(talent, name);
    }
}
