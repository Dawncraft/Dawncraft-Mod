package io.github.dawncraft.stats;

import javax.annotation.Nullable;

import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class SkillDamageSource extends DamageSource
{
    private SkillStack skillStack;
    private Entity damageSourceEntity;

    public SkillDamageSource(SkillStack skillStack, Entity indirectEntity)
    {
        super("skill");
        this.skillStack = skillStack;
        this.damageSourceEntity = indirectEntity;
        this.setMagicDamage();
    }

    public SkillStack getSkillStack()
    {
        return this.skillStack;
    }

    @Override
    @Nullable
    public Entity getTrueSource()
    {
        return this.damageSourceEntity;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLiving)
    {
        String text = "death.attack." + this.damageType;
        if (this.getSkillStack() != null)
        {
            String skillName = text + ".skill";
            return new TextComponentTranslation(skillName, entityLiving.getDisplayName(), this.damageSourceEntity.getDisplayName(), this.skillStack.getTextComponent());
        }
        return new TextComponentTranslation(text, entityLiving.getDisplayName(), this.damageSourceEntity.getDisplayName());
    }
}
