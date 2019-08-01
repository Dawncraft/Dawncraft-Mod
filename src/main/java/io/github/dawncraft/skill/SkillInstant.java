package io.github.dawncraft.skill;

import java.util.List;
import java.util.Random;

import io.github.dawncraft.client.particle.ParticleInit;
import io.github.dawncraft.stats.DamageSourceLoader;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class SkillInstant extends Skill
{
    public SkillInstant(int maxLevel)
    {
	super(maxLevel);
    }

    @Override
    public float getConsume(int level)
    {
	if (this == SkillInit.HEAL)
	    return 4 + 2 * level;
	else if (this == SkillInit.ATTACK)
	    return 2 + 1 * level;
	return super.getConsume(level);
    }

    @Override
    public int getCooldown(int level)
    {
	if (this == SkillInit.HEAL)
	    return 20 + 10 * (level - 1);
	else if (this == SkillInit.ATTACK)
	    return 20 + 10 * (level - 1);
	return super.getCooldown(level);
    }

    @Override
    public String getSkillStackDisplayDesc(SkillStack skillStack)
    {
	if (this == SkillInit.HEAL)
	    return I18n.format(this.getUnlocalizedName(skillStack) + ".desc",
		    skillStack.getSkillConsume(), 4.0F + 2.0F * this.getLevel(skillStack), (float) skillStack.getTotalCooldown() / 20);
	return super.getSkillStackDisplayDesc(skillStack);
    }

    @Override
    public void addInformation(SkillStack skillStack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
	if (this == SkillInit.HEAL)
	    tooltip.add(I18n.format(this.getUnlocalizedName(skillStack) + ".desc2"));
    }

    @Override
    public boolean onSkillSpell(SkillStack skillStack, World world, EntityPlayer player)
    {
	if (!world.isRemote)
	{
	    if (this == SkillInit.HEAL)
	    {
		player.heal(4.0F + 2.0F * this.getLevel(skillStack));
	    }
	    else if (this == SkillInit.ATTACK)
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

	world.spawnParticle(ParticleInit.LIGHTING, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 0.0D, 0.0D, 0.0D, new int[0]);

	return true;
    }
}
