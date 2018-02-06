package io.github.dawncraft.stats;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.stats.StatBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class StatLoader
{
    public static List<StatSpelling> spellStats = Lists.<StatSpelling>newArrayList();
    public static final StatBase[] objectSpellStats = new StatBase[32000];
    
    public StatLoader(FMLInitializationEvent event)
    {
        initSpellStats();
    }
    
    private static void initSpellStats()
    {
        for (Skill skill : Skill.skillRegistry)
        {
            if (skill != null)
            {
                int id = Skill.getIdFromSkill(skill);
                String name = replace(Skill.skillRegistry.getNameForObject(skill));

                if (name != null)
                {
                    objectSpellStats[id] = new StatSpelling("stat.spellMagic.", name, new ChatComponentTranslation("stat.spellMagic", new Object[] {new SkillStack(skill).getChatComponent()}), skill).registerStat();
                    spellStats.add((StatSpelling) objectSpellStats[id]);
                }
            }
        }
    }

    public static String replace(ResourceLocation resourcelocation)
    {
        return resourcelocation != null ? resourcelocation.toString().replace(':', '.') : null;
    }
}
