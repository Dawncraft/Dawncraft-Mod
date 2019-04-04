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
    public static DamageSource thirst = new DamageSource("thirst").setDamageBypassesArmor().setDamageIsAbsolute();
    public static DamageSource ger = new DamageSource("byGer").setDifficultyScaled().setExplosion();
    
    public static void initDamageSources() {}
    
    /**
     * returns EntityDamageSourceIndirect of a Magic Skill
     *
     * @param indirectEntityIn The entity that shoot the bullet
     */
    public static DamageSource causeSkillDamage(SkillStack skillStack, Entity indirectEntityIn)
    {
        return new SkillDamageSource(skillStack, indirectEntityIn);
    }
    
    /**
     * returns EntityDamageSourceIndirect of a bullet
     *
     * @param indirectEntityIn The entity that shoot the bullet
     */
    public static DamageSource causeBulletDamage(EntityBullet bullet, Entity indirectEntityIn)
    {
        return new EntityDamageSourceIndirect("bullet", bullet, indirectEntityIn).setProjectile();
    }
}
