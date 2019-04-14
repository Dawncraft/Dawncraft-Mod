package io.github.dawncraft.client.renderer.magicfield;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import io.github.dawncraft.magicfield.EntityMagicField;

public class RenderMagicField<T extends EntityMagicField> extends Render<T>
{
    protected RenderMagicField(RenderManager renderManager)
    {
        super(renderManager);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(T entity)
    {
        return null;
    }
}
