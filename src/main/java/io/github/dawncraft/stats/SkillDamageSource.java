package io.github.dawncraft.stats;

import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

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
    public Entity getEntity()
    {
        return this.damageSourceEntity;
    }

    @Override
    public IChatComponent getDeathMessage(EntityLivingBase entityLivingBase)
    {
        String text = "death.attack." + this.damageType;
        if (this.getSkillStack() != null)
        {
            String skillName = text + ".skill";
            if (StatCollector.canTranslate(skillName))
            {
                return new ChatComponentTranslation(skillName, entityLivingBase.getDisplayName(), this.damageSourceEntity.getDisplayName(), this.skillStack.getChatComponent());
            }
        }
        return new ChatComponentTranslation(text, entityLivingBase.getDisplayName(), this.damageSourceEntity.getDisplayName());
    }
}
