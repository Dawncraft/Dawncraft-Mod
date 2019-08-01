package io.github.dawncraft.stats;

import io.github.dawncraft.entity.projectile.EntityBullet;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

/**
 * Register some damage source.
 *
 * @author QingChenW
 */
public class DamageSourceLoader
{
    public static final DamageSource THIRST = new DamageSource("thirst").setDamageBypassesArmor().setDamageIsAbsolute();
    public static final DamageSource GER = new DamageSource("byGer").setDifficultyScaled().setExplosion();

    /**
     * returns EntityDamageSourceIndirect of a Magic Skill
     *
     * @param indirectEntityIn The entity that shoot the bullet
     */
    public static DamageSource causeSkillDamage(SkillStack skillStack, Entity indirectEntity)
    {
        return new SkillDamageSource(skillStack, indirectEntity);
    }

    /**
     * returns EntityDamageSourceIndirect of a bullet
     *
     * @param indirectEntityIn The entity that shoot the bullet
     */
    public static DamageSource causeBulletDamage(EntityBullet bullet, Entity indirectEntity)
    {
        return new EntityDamageSourceIndirect("bullet", bullet, indirectEntity).setProjectile();
    }
}
