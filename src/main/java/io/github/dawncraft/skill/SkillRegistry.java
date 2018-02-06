package io.github.dawncraft.skill;

import com.google.common.base.Strings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;

/**
 * The register is out if date.
 * <br>The new version of Forge has RegistryBuilder to use.<br/>
 * But now, we can only use it...
 *
 * @author QingChenW
 */
@Deprecated
public class SkillRegistry
{
    public static final ResourceLocation Skill = new ResourceLocation("minecraft:skills");
    private static final int MIN_SKILL_ID = 0;
    private static final int MAX_SKILL_ID = 31999;
    private final FMLControlledNamespacedRegistry<Skill> iSkillRegistry = PersistentRegistryManager.createRegistry(Skill, Skill.class, null, MAX_SKILL_ID, MIN_SKILL_ID, true);

    private static final SkillRegistry main = new SkillRegistry();

    private FMLControlledNamespacedRegistry<Skill> getRegistry()
    {
        return this.iSkillRegistry;
    }

    private void register(Skill skill, String name)
    {
        this.iSkillRegistry.register(-1, this.addPrefix(name), skill);
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

    public static SkillRegistry getMain()
    {
        return main;
    }
    
    public static FMLControlledNamespacedRegistry<Skill> getSkillRegistry()
    {
        return getMain().getRegistry();
    }

    public static void registerSkill(Skill skill)
    {
        registerSkill(skill, skill.getRegistryName());
    }

    public static void registerSkill(Skill skill, String name)
    {
        if (Strings.isNullOrEmpty(name))
        {
            throw new IllegalArgumentException("Attempted to register a skill with no name: " + skill);
        }
        getMain().register(skill, name);
    }
}
