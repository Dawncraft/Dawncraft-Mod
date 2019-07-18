package io.github.dawncraft.api.registry;

import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.talent.Talent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Register something added by Dawncraft mod.
 *
 * @author QingChenW
 */
public class ModRegistry
{
    public static final IForgeRegistry<Skill> SKILLS = GameRegistry.findRegistry(Skill.class);
    public static final IForgeRegistry<Talent> TALENTS = GameRegistry.findRegistry(Talent.class);
}
