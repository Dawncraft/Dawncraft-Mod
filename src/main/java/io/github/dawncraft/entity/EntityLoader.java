package io.github.dawncraft.entity;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.entity.boss.EntityGerKing;
import io.github.dawncraft.entity.passive.EntityMouse;
import io.github.dawncraft.entity.passive.EntitySavage;
import io.github.dawncraft.entity.projectile.EntityMagnetBall;
import io.github.dawncraft.entity.projectile.EntityRocket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * @author QingChenW
 *
 */
public class EntityLoader
{
    private static int nextID = 0;

    public static void initEntities()
    {
        registerEntity(EntityMouse.class, "Mouse", 64, 3, true);
        registerEntity(EntitySavage.class, "Savage", 64, 3, true);
        registerEntity(EntityGerKing.class, "GerKing", 64, 3, true);
        registerEntity(EntityMagnetBall.class, "MagnetBall", 64, 10, true);
        registerEntity(EntityRocket.class, "SmallRocket", 64, 10, true);
        registerEntityEgg(EntityMouse.class, 0x5b0f00, 0x573131);
        registerEntityEgg(EntitySavage.class, 0x795949, 0x513830);
        registerEntitySpawn(EntitySavage.class, 40, 2, 6, EnumCreatureType.CREATURE, BiomeGenBase.plains);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange,
            int updateFrequency, boolean sendsVelocityUpdates)
    {
        EntityRegistry.registerModEntity(entityClass, name, nextID++, Dawncraft.instance, trackingRange, updateFrequency,
                sendsVelocityUpdates);
    }
    
    private static void registerEntityEgg(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary)
    {
        EntityRegistry.registerEgg(entityClass, eggPrimary, eggSecondary);
    }
    
    private static void registerEntitySpawn(Class<? extends Entity> entityClass, int spawnWeight, int min, int max,
            EnumCreatureType typeOfCreature, BiomeGenBase... biomes)
    {
        if (EntityLiving.class.isAssignableFrom(entityClass))
        {
            Class<? extends EntityLiving> entityLivingClass = entityClass.asSubclass(EntityLiving.class);
            EntityRegistry.addSpawn(entityLivingClass, spawnWeight, min, max, typeOfCreature, biomes);
        }
    }
}
