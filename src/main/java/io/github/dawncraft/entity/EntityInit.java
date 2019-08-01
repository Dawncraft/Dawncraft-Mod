package io.github.dawncraft.entity;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.entity.boss.EntityBarbarianKing;
import io.github.dawncraft.entity.boss.EntityGerKing;
import io.github.dawncraft.entity.passive.EntityMouse;
import io.github.dawncraft.entity.passive.EntitySavage;
import io.github.dawncraft.entity.projectile.EntityBullet;
import io.github.dawncraft.entity.projectile.EntityMagnetBall;
import io.github.dawncraft.entity.projectile.EntityRocket;
import io.github.dawncraft.entity.projectile.EntityThrowableTorch;
import io.github.dawncraft.magicfield.EntityMFFireBall;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Register some entities.
 *
 * @author QingChenW
 */
@Mod.EventBusSubscriber(modid = Dawncraft.MODID)
public class EntityInit
{
    private static int nextID = 0;

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        registerEntity(EntityEntryBuilder.create().entity(EntityMouse.class).name("Mouse").tracker(64, 3, true).egg(0x5b0f00, 0x573131), "mouse");
        registerEntity(EntityEntryBuilder.create().entity(EntitySavage.class).name("Savage").tracker(64, 3, true)
                .egg(0x795949, 0x513830).spawn(EnumCreatureType.CREATURE, 40, 2, 6, Biomes.PLAINS, Biomes.SAVANNA_PLATEAU), "savage");
        registerEntity(EntityEntryBuilder.create().entity(EntityBarbarianKing.class).name("BarbarianKing").tracker(64, 3, true), "barbarian_king");
        registerEntity(EntityEntryBuilder.create().entity(EntityGerKing.class).name("GerKing").tracker(64, 3, true), "ger_king");

        registerEntity(EntityEntryBuilder.create().entity(EntityMagnetBall.class).name("MagnetBall").tracker(64, 10, true), "magnet_ball");
        registerEntity(EntityEntryBuilder.create().entity(EntityBullet.class).name("Bullet").tracker(64, 10, true), "bullet");
        registerEntity(EntityEntryBuilder.create().entity(EntityRocket.class).name("SmallRocket").tracker(64, 10, true), "smallrocket");
        registerEntity(EntityEntryBuilder.create().entity(EntityThrowableTorch.class).name("ThrowableTorch").tracker(64, 10, true), "throwable_torch");

        registerEntity(EntityEntryBuilder.create().entity(EntityMFFireBall.class).name("MagicFieldFireBall").tracker(64, 10, true), "magicfield_fireball");

    }

    private static void registerEntity(EntityEntryBuilder<Entity> entityEntryBuilder, String name)
    {
        ForgeRegistries.ENTITIES.register(entityEntryBuilder.id(name, nextID++).build());
    }
}
