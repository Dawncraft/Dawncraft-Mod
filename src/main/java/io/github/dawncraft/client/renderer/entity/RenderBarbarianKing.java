package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.entity.boss.EntityBarbarianKing;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * render of barbarian king
 * Idea from 部落冲突
 *
 * @author QingChenW
 */
public class RenderBarbarianKing extends RenderLiving<EntityBarbarianKing>
{
    private static final ResourceLocation BARBARIANKING_TEXTURE = new ResourceLocation(Dawncraft.MODID + ":" + "textures/entity/barbarian_king.png");

    public RenderBarbarianKing(RenderManager renderManager)
    {
        super(renderManager, new ModelPlayer(0.0F, false), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntityBarbarianKing entity, float partialTickTime)
    {
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBarbarianKing entity)
    {
        return RenderBarbarianKing.BARBARIANKING_TEXTURE;
    }

    @Override
    public void doRender(EntityBarbarianKing entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
