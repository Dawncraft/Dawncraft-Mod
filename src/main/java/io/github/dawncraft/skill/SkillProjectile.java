package io.github.dawncraft.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import io.github.dawncraft.magicfield.EntityMFFireBall;

public class SkillProjectile extends Skill
{
    public SkillProjectile(int maxLevel)
    {
        super(maxLevel);
    }

    @Override
    public float getConsume(int level)
    {
        return 1 + 1 * level;
    }
    
    @Override
    public int getCooldown(int level)
    {
        return 20 + 10 * (level - 1);
    }

    @Override
    public String getSkillStackDisplayDesc(SkillStack skillStack)
    {
        if (this == SkillLoader.fireball)
            return StatCollector.translateToLocalFormatted(this.getUnlocalizedName(skillStack) + ".desc",
                    skillStack.getSkillConsume(), 4.0F + 2.0F * this.getLevel(skillStack), (float) skillStack.getTotalCooldown() / 20);
        return super.getSkillStackDisplayDesc(skillStack);
    }
    
    @Override
    public void addInformation(SkillStack skillStack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        if (this == SkillLoader.fireball)
            tooltip.add(StatCollector.translateToLocal(this.getUnlocalizedName(skillStack) + ".desc2"));
    }
    
    @Override
    public boolean onSkillSpell(SkillStack skillStack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new EntityMFFireBall(world, player, 4.0F + 2.0F * this.getLevel(skillStack), 0.1D));
        }

        player.playSound("item.fireCharge.use", 1.0F, 1.0F);
        
        for (int i = 0; i < 8; i++)
        {
            Random rand = new Random();
            double d0 = player.getPosition().getX() + rand.nextFloat();
            double d1 = player.getPosition().getY() + 0.8F;
            double d2 = player.getPosition().getZ() + rand.nextFloat();
            double d3 = 0.0D;
            double d4 = 0.0D;
            double d5 = 0.0D;
            world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, d3, d4, d5, new int[0]);
        }
        
        return true;
    }
}
