package io.github.dawncraft.stats;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatBasic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Register some stats
 *
 * @author QingChenW
 */
public class StatInit
{
    public static final List<StatLearning> SPELL_SKILL_STATS = Lists.<StatLearning>newArrayList();
    public static final StatBase[] LEARN_STATS = new StatBase[32000];
    public static final StatBase[] OBJECT_SPELL_STATS = new StatBase[32000];
    public static final StatBase MACHINE_INTERACTION = new StatBasic("stat.machineInteraction", new TextComponentTranslation("stat.machineInteraction")).registerStat();

    public static void initStats()
    {
        initSpellStats();
        initLearnableStats();
    }

    private static void initSpellStats()
    {
        for (Skill skill : Skill.REGISTRY)
        {
            if (skill != null)
            {
                int id = Skill.getIdFromSkill(skill);
                String name = replace(skill.getRegistryName());

                if (name != null)
                {
                    LEARN_STATS[id] = new StatLearning("stat.spellSkill.", name, new TextComponentTranslation("stat.spellSkill", new SkillStack(skill).getTextComponent()), skill).registerStat();
                    SPELL_SKILL_STATS.add((StatLearning) LEARN_STATS[id]);
                }
            }
        }
    }

    /**
     * Initializes statistics related to learnable skills. Is only called after skill stats have been initialized.
     */
    private static void initLearnableStats()
    {
        Set<Skill> set = Sets.<Skill>newHashSet();
    }

    @Nullable
    public static StatBase getLearnStats(Skill skill)
    {
        return LEARN_STATS[Skill.getIdFromSkill(skill)];
    }

    @Nullable
    public static StatBase getObjectSpellStats(Skill skill)
    {
        return OBJECT_SPELL_STATS[Skill.getIdFromSkill(skill)];
    }

    public static String replace(ResourceLocation res)
    {
        return res != null ? res.toString().replace(':', '.') : null;
    }
}
