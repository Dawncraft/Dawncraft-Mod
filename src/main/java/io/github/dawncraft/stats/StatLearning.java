package io.github.dawncraft.stats;

import io.github.dawncraft.skill.Skill;
import net.minecraft.stats.StatBase;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StatLearning extends StatBase
{
    private final Skill skill;

    public StatLearning(String statPrefix, String statName, ITextComponent statText, Skill skill)
    {
        super(statPrefix + statName, statText);
        this.skill = skill;
    }

    @SideOnly(Side.CLIENT)
    public Skill getSkill()
    {
        return this.skill;
    }
}
