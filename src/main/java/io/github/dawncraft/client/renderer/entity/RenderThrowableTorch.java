package io.github.dawncraft.client.renderer.entity;

import io.github.dawncraft.entity.projectile.EntityThrowableTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderThrowableTorch extends Render<EntityThrowableTorch>
{
    private final RenderItem renderItem;

    public RenderThrowableTorch(RenderManager renderManager)
    {
        super(renderManager);
        this.renderItem = Minecraft.getMinecraft().getRenderItem();
    }

    @Override
    public void doRender(EntityThrowableTorch entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(entity.rotateTimer, 1.0F, 1.0F, 1.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        this.renderItem.renderItem(this.getItem(entity), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    public ItemStack getItem(EntityThrowableTorch entity)
    {
        return new ItemStack(Blocks.TORCH, 1, 0);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityThrowableTorch entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
