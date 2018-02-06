package io.github.dawncraft.client.renderer.skill;

import io.github.dawncraft.client.renderer.texture.TextureLoader;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class SkillRenderer extends Gui
{
    public static SkillRenderer skillRender;
    private final Minecraft mc;
    private final TextureManager textureManager;
    private final RenderManager renderManager;
    protected float zLevel;
    
    public SkillRenderer(Minecraft mcIn)
    {
        this.mc = mcIn;
        this.textureManager = mcIn.getTextureManager();
        this.renderManager = mcIn.getRenderManager();
    }

    public void renderSkillIntoGUI(SkillStack skillstack, int xPos, int yPos)
    {
        GlStateManager.pushMatrix();
        this.textureManager.bindTexture(TextureLoader.skillsTexture);
        this.textureManager.getTexture(TextureLoader.skillsTexture).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (skillstack != null)
        {
            GlStateManager.pushMatrix();
            ResourceLocation res = TextureLoader.getActualLocation(new ResourceLocation(skillstack.getSkill().getRegistryName()));
            TextureAtlasSprite sprite = TextureLoader.getTextureMapSkills().getAtlasSprite(res.toString());
            this.drawTexturedModalRect(xPos, yPos, sprite, 16, 16);

            int height = skillstack.getCooldown() * 16 / skillstack.getTotalCooldown();
            this.drawRect(xPos, yPos, 16, height, 0x80B4B4B4);// TODO 换成1.11末影珍珠的冷却背景
            GlStateManager.popMatrix();
        }
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
    }
}
