package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.entity.*;
import io.github.dawncraft.entity.boss.EntityGerKing;
import io.github.dawncraft.entity.passive.EntityMouse;
import io.github.dawncraft.entity.passive.EntitySavage;
import io.github.dawncraft.entity.projectile.*;
import io.github.dawncraft.item.ItemLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register entity render.
 * 
 * @author QingChenW
 */
public class EntityRenderLoader
{
    public EntityRenderLoader(FMLPreInitializationEvent event)
    {
        register(EntityMouse.class, RenderMouse.class);
        register(EntitySavage.class, RenderSavage.class);
        register(EntityGerKing.class, RenderGerKing.class);
        
        register(EntityMagnetBall.class, RenderMagnetBall.class);
        register(EntityBullet.class, RenderBullet.class);
        register(EntityRocket.class, RenderRocket.class);
    }
    
    private static <T extends Entity> void register(Class<T> entityClass, Class<? extends Render<T>> render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityRenderFactory<T>(render));
    }
}
