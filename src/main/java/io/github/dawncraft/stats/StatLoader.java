package io.github.dawncraft.stats;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.stats.StatBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class StatLoader
{
    public static List<StatLearning> skillStats = Lists.<StatLearning>newArrayList();
    public static final StatBase[] objectLearnStats = new StatBase[32000];
    public static final StatBase[] objectSpellStats = new StatBase[32000];

    public static void initStats()
    {
        initLearnableStats();
        initSpellStats();
    }

    /**
     * Initializes statistics related to learnable skills. Is only called after skill stats have been initialized.
     */
    private static void initLearnableStats()
    {
        Set<Skill> set = Sets.<Skill>newHashSet();
    }

    private static void initSpellStats()
    {
        for (Skill skill : Skill.REGISTRY)
        {
            if (skill != null)
            {
                int id = Skill.getIdFromSkill(skill);
                String name = replace(Skill.REGISTRY.getNameForObject(skill));

                if (name != null)
                {
                    objectLearnStats[id] = new StatLearning("stat.spellSkill.", name, new TextComponentTranslation("stat.spellSkill", new SkillStack(skill).getTextComponent()), skill).registerStat();
                    skillStats.add((StatLearning) objectLearnStats[id]);
                }
            }
        }
    }

    public static String replace(ResourceLocation resourcelocation)
    {
        return resourcelocation != null ? resourcelocation.toString().replace(':', '.') : null;
    }
}
