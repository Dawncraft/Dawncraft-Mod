package io.github.dawncraft.stats;

import io.github.dawncraft.entity.projectile.EntityBullet;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Register some damage source.
 *
 * @author QingChenW
 */
public class DamageSourceLoader
{
    public static DamageSource ger = new DamageSource("byGer").setDifficultyScaled().setExplosion();
    
    public DamageSourceLoader(FMLInitializationEvent event) {}
    
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
