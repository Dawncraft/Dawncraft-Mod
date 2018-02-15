package io.github.dawncraft.client.renderer.skill;

import io.github.dawncraft.client.renderer.texture.TextureLoader;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class SkillRenderer
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
        if(skillstack != null && skillstack.getSkill() != null)
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
                this.drawSkill(xPos, yPos, sprite, 16, 16);
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
    
    public void renderSkillOverlayIntoGUI(FontRenderer fr, SkillStack skillstack, int xPos, int yPos, String text)
    {
        if (skillstack != null)
        {
            if (skillstack.getSkillLevel() != 1 || text != null)
            {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                String s = text == null ? String.valueOf(skillstack.getSkillLevel()) : text;
                fr.drawStringWithShadow(s, (float)(xPos + 19 - 2 - fr.getStringWidth(s)), (float)(yPos + 6 + 3), 16777215);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                // Fixes opaque cooldown overlay a bit lower
                // TODO: check if enabled blending still screws things up down the line.
                GlStateManager.enableBlend();
            }

            //EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
            //float cooldown = entityplayersp == null ? 0.0F : entityplayersp.getCooldownTracker().getCooldown(skillstack.getItem(), Minecraft.getMinecraft().getRenderPartialTicks());
            float cooldown = skillstack.getCooldown() / skillstack.getTotalCooldown();
            
            if (cooldown > 0.0F)
            {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldrender = tessellator.getWorldRenderer();
                this.draw(worldrender, xPos, yPos + MathHelper.floor_float(16.0F * (1.0F - cooldown)), 16, MathHelper.ceiling_float_int(16.0F * cooldown), 255, 255, 255, 127);
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
    }
    
    /**
     * Draws a texture rectangle using the texture currently bound to the TextureManager
     */
    public void drawSkill(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)this.zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMaxV()).endVertex();
        worldrenderer.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)this.zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMaxV()).endVertex();
        worldrenderer.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)this.zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMinV()).endVertex();
        worldrenderer.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)this.zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    /**
     * Draw with the WorldRenderer
     *
     * @param renderer The WorldRenderer's instance
     * @param x X position where the render begin
     * @param y Y position where the render begin
     * @param width The width of the render
     * @param height The height of the render
     * @param red Red component of the color
     * @param green Green component of the color
     * @param blue Blue component of the color
     * @param alpha Alpha component of the color
     */
    private void draw(WorldRenderer renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha)
    {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((double)(x + 0), (double)(y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + 0), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }
}
