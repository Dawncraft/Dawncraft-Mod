package io.github.dawncraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/**
 * A factory of entity render.
 *
 * @author ustc-zzzz
 */
public class EntityRenderFactory<E extends Entity> implements IRenderFactory<E>
{
    private final Class<? extends Render<E>> renderClass;

    public EntityRenderFactory(Class<? extends Render<E>> renderClass)
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
