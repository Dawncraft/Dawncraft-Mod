package io.github.dawncraft.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import io.github.dawncraft.client.particle.ParticleLoader;
import io.github.dawncraft.stats.DamageSourceLoader;

public class SkillInstant extends Skill
{
    public SkillInstant(int maxLevel)
    {
        super(maxLevel);
    }
    
    @Override
    public float getConsume(int level)
    {
        if (this == SkillLoader.heal)
            return 4 + 2 * level;
        else if (this == SkillLoader.attack)
            return 2 + 1 * level;
        return super.getConsume(level);
    }
    
    @Override
    public int getCooldown(int level)
    {
        if (this == SkillLoader.heal)
            return 20 + 10 * (level - 1);
        else if (this == SkillLoader.attack)
            return 20 + 10 * (level - 1);
        return super.getCooldown(level);
    }
    
    @Override
    public String getSkillStackDisplayDesc(SkillStack skillStack)
    {
        if (this == SkillLoader.heal)
            return StatCollector.translateToLocalFormatted(this.getUnlocalizedName(skillStack) + ".desc",
                    skillStack.getSkillConsume(), 4.0F + 2.0F * this.getLevel(skillStack), (float) skillStack.getTotalCooldown() / 20);
        return super.getSkillStackDisplayDesc(skillStack);
    }

    @Override
    public void addInformation(SkillStack skillStack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (this == SkillLoader.heal)
            tooltip.add(StatCollector.translateToLocal(this.getUnlocalizedName(skillStack) + ".desc2"));
    }
    
    @Override
    public boolean onSkillSpell(SkillStack skillStack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            if (this == SkillLoader.heal)
            {
                player.heal(4.0F + 2.0F * this.getLevel(skillStack));
            }
            else if (this == SkillLoader.attack)
            {
                player.attackEntityFrom(DamageSourceLoader.causeSkillDamage(skillStack, player), 2.0F + 1.0F * this.getLevel(skillStack));
            }
            
        }
        
        for (int i = 0; i < 4; i++)
        {
            Random rand = new Random();
            double x = player.getPosition().getX() + rand.nextFloat();
            double y = player.getPosition().getY() + 0.8F;
            double z = player.getPosition().getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
        }

        world.spawnParticle(ParticleLoader.LIGHTING, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 0.0D, 0.0D, 0.0D, new int[0]);
        
        return true;
    }
}
