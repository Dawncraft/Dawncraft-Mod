package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.entity.boss.EntityGerKing;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

/**
 * render of ger king
 *
 * @author QingChenW
 */
public class RenderGerKing extends RenderLiving<EntityGerKing>
{
    private static final ResourceLocation GERKING_TEXTURE = new ResourceLocation(dawncraft.MODID + ":" + "textures/entity/ger_king.png");
    
    public RenderGerKing(RenderManager renderManager)
    {
        super(renderManager, new ModelBiped(), 0.5F);
    }
    
    @Override
    protected void preRenderCallback(EntityGerKing entity, float partialTickTime)
    {
        //      GlStateManager.scale(1.0F, 1.0F, 1.0F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityGerKing entity)
    {
        return RenderGerKing.GERKING_TEXTURE;
    }
    
    @Override
    public void doRender(EntityGerKing entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        BossStatus.setBossStatus(entity, true);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
