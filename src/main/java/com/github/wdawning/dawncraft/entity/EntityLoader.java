package com.github.wdawning.dawncraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.github.wdawning.dawncraft.dawncraft;

public class EntityLoader
{
    public EntityLoader()
    {
        registerEntity(EntityMouse.class, "Mouse", 64, 3, true);
        registerEntity(EntitySavage.class, "Savage", 64, 3, true);
        registerEntity(EntityGerKing.class, "GerKing", 64, 3, true);
        registerEntity(EntityMagnetBall.class, "MagnetBall", 64, 10, true);
        registerEntity(EntityFlanBomb.class, "FlanBomb", 64, 10, true);
        registerEntityEgg(EntityMouse.class, 0x5b0f00, 0x573131);
        registerEntityEgg(EntitySavage.class, 0x795949, 0x513830);
        registerEntityEgg(EntityGerKing.class, 0x795949, 0x800000);
        registerEntitySpawn(EntitySavage.class, 40, 2, 6, EnumCreatureType.CREATURE, BiomeGenBase.plains);
    }
    
    public static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange,
            int updateFrequency, boolean sendsVelocityUpdates)
    {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        
        EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
        EntityRegistry.registerModEntity(entityClass, name, entityID, dawncraft.instance, trackingRange,
                updateFrequency, sendsVelocityUpdates);
    }
    
    public static void registerEntityEgg(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary)
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
