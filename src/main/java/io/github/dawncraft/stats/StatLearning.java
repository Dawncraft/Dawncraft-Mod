package io.github.dawncraft.stats;

import io.github.dawncraft.skill.Skill;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.stats.StatBase;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StatLearning extends StatBase
{
    private final Skill skill;
    
    public StatLearning(String statPrefix, String statName, IChatComponent statNameIn, Skill skill)
    {
        super(statPrefix + statName, statNameIn);
        this.skill = skill;
        int id = Skill.getIdFromSkill(skill);
        if (id != 0)
        {
            IScoreObjectiveCriteria.INSTANCES.put(statPrefix + id, this.getCriteria());
        }
    }
    
    @SideOnly(Side.CLIENT)
    public Skill getSkill()
    {
        return this.skill;
    }
}
