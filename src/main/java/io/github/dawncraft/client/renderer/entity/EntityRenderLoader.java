package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.client.renderer.magicfield.RenderMFFireBall;
import io.github.dawncraft.entity.boss.EntityBarbarianKing;
import io.github.dawncraft.entity.boss.EntityGerKing;
import io.github.dawncraft.entity.passive.EntityMouse;
import io.github.dawncraft.entity.passive.EntitySavage;
import io.github.dawncraft.entity.projectile.*;
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
public class EntityRenderLoader
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
        registerEntityRenderer(EntityMFFireBall.class, new IRenderFactory()
        {
            @Override
            public Render createRenderFor(RenderManager manager)
            {
                return new RenderMFFireBall(manager, 0.5F);
            }
        });
    }
    
    private static <T extends Entity> void registerEntityRenderer(Class<T> entityClass, Class<? extends Render<T>> render)
    {
        registerEntityRenderer(entityClass, new Factory<T>(render));
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
    public static class Factory<E extends Entity> implements IRenderFactory<E>
    {
        private final Class<? extends Render<E>> renderClass;

        public Factory(Class<? extends Render<E>> renderClass)
        {
            this.renderClass = renderClass;
        }

        @Override
        public Render<E> createRenderFor(RenderManager manager)
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
