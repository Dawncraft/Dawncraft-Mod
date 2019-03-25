package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.entity.boss.EntityGerKing;
import io.github.dawncraft.entity.passive.EntityMouse;
import io.github.dawncraft.entity.passive.EntitySavage;
import io.github.dawncraft.entity.projectile.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * Register entity render.
 *
 * @author QingChenW
 */
public class EntityRenderLoader
{
    public static void initEntityRender()
    {
        registerEntityRenderer(EntityMouse.class, RenderMouse.class);
        registerEntityRenderer(EntitySavage.class, RenderSavage.class);
        registerEntityRenderer(EntityGerKing.class, RenderGerKing.class);

        registerEntityRenderer(EntityMagnetBall.class, RenderMagnetBall.class);
        registerEntityRenderer(EntityBullet.class, RenderBullet.class);
        registerEntityRenderer(EntityRocket.class, RenderRocket.class);
    }

    private static <T extends Entity> void registerEntityRenderer(Class<T> entityClass, Class<? extends Render<T>> render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityRenderFactory<T>(render));
    }
}
