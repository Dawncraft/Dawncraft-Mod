package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.client.renderer.magicfield.RenderMFFireBall;
import io.github.dawncraft.entity.boss.EntityBarbarianKing;
import io.github.dawncraft.entity.boss.EntityGerKing;
import io.github.dawncraft.entity.passive.EntityMouse;
import io.github.dawncraft.entity.passive.EntitySavage;
import io.github.dawncraft.entity.projectile.EntityBullet;
import io.github.dawncraft.entity.projectile.EntityMagnetBall;
import io.github.dawncraft.entity.projectile.EntityRocket;
import io.github.dawncraft.entity.projectile.EntityThrowableTorch;
import io.github.dawncraft.magicfield.EntityMFFireBall;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * Register entity render.
 *
 * @author QingChenW
 */
public class EntityRenderInit
{
    public static void initEntityRender()
    {
        registerEntityRenderer(EntityMouse.class, RenderMouse.class);
        registerEntityRenderer(EntitySavage.class, RenderSavage.class);
        registerEntityRenderer(EntityBarbarianKing.class, RenderBarbarianKing.class);
        registerEntityRenderer(EntityGerKing.class, RenderGerKing.class);

        registerEntityRenderer(EntityMagnetBall.class, RenderMagnetBall.class);
        registerEntityRenderer(EntityBullet.class, RenderBullet.class);
        registerEntityRenderer(EntityRocket.class, RenderRocket.class);
        registerEntityRenderer(EntityThrowableTorch.class, RenderThrowableTorch.class);
        registerEntityRenderer(EntityMFFireBall.class, new IRenderFactory<EntityMFFireBall>()
        {
            @Override
            public Render<EntityMFFireBall> createRenderFor(RenderManager manager)
            {
                return new RenderMFFireBall(manager, 0.5F);
            }
        });
    }

    private static <T extends Entity> void registerEntityRenderer(Class<T> entityClass, Class<? extends Render<T>> render)
    {
        registerEntityRenderer(entityClass, new Factory<>(render));
    }

    private static <T extends Entity> void registerEntityRenderer(Class<T> entityClass, IRenderFactory<? super T> renderFactory)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
    }

    @Deprecated
    private static <T extends Entity> void registerEntityRenderer(Class<T> entityClass, Render<T> render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, render);
    }

    /**
     * A default factory of entity renderer.
     *
     * @author ustc-zzzz
     */
    public static class Factory<T extends Entity> implements IRenderFactory<T>
    {
        private final Class<? extends Render<T>> renderClass;

        public Factory(Class<? extends Render<T>> renderClass)
        {
            this.renderClass = renderClass;
        }

        @Override
        public Render<T> createRenderFor(RenderManager manager)
        {
            try
            {
                return this.renderClass.getConstructor(RenderManager.class).newInstance(manager);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
