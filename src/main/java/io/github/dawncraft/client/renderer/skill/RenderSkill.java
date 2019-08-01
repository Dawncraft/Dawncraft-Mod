package io.github.dawncraft.client.renderer.skill;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.client.gui.GuiUtils;
import io.github.dawncraft.client.renderer.model.ModelLoader;
import io.github.dawncraft.client.renderer.texture.TextureLoader;
import io.github.dawncraft.skill.SkillStack;
import io.github.dawncraft.util.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

public class RenderSkill implements IResourceManagerReloadListener
{
    private TextureManager textureManager;
    private ModelLoader modelLoader;

    public float zLevel;

    public RenderSkill(TextureManager textureManager, ModelLoader modelLoader)
    {
        this.textureManager = textureManager;
        this.modelLoader = modelLoader;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {}

    public void renderSkill(SkillStack stack, int x, int y)
    {
        if (stack != null)
        {
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, 1.0F, 1.0F);
            this.drawSprite(x, y, this.modelLoader.getSkillSprite(stack), 16, 16);
            GlStateManager.popMatrix();
        }
    }

    public void renderSkillIntoGUI(SkillStack stack, int x, int y)
    {
        GlStateManager.pushMatrix();
        this.textureManager.bindTexture(TextureLoader.locationSkillsTexture);
        this.textureManager.getTexture(TextureLoader.locationSkillsTexture).setBlurMipmap(false, false);
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderSkill(stack, x, y);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        this.textureManager.bindTexture(TextureLoader.locationSkillsTexture);
        this.textureManager.getTexture(TextureLoader.locationSkillsTexture).restoreLastBlurMipmap();
    }

    public void renderSkillAndEffectIntoGUI(final SkillStack stack, int x, int y)
    {
        if (stack != null && stack.getSkill() != null)
        {
            this.zLevel += 50.0F;

            try
            {
                this.renderSkillIntoGUI(stack, x, y);
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering skill");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Skill being rendered");
                crashreportcategory.addDetail("Skill Type", new ICrashReportDetail<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        return String.valueOf(stack.getSkill());
                    }
                });
                crashreportcategory.addDetail("Skill Level", new ICrashReportDetail<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        return String.valueOf(stack.getSkillLevel());
                    }
                });
                crashreportcategory.addDetail("Skill NBT", new ICrashReportDetail<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        return String.valueOf(stack.getTagCompound());
                    }
                });
                throw new ReportedException(crashreport);
            }

            this.zLevel -= 50.0F;
        }
    }

    /**
     * Renders the stack level for the given SkillStack.
     */
    public void renderSkillOverlayIntoGUI(FontRenderer fontRenderer, SkillStack stack, int x, int y)
    {
        if (stack != null)
        {
            String level;
            if (stack.skillLevel >= 1)
            {
                level = StringUtils.toRome(stack.skillLevel);
            }
            else
            {
                level = TextFormatting.RED + String.valueOf(stack.skillLevel);
            }

            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableBlend();
            fontRenderer.drawStringWithShadow(level, x + 19 - 2 - fontRenderer.getStringWidth(level), y + 6 + 3, 0xffffff);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();

            float cooldown = 0.0F;
            EntityPlayer player = Minecraft.getMinecraft().player;
            IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
            cooldown = playerMagic.getCooldownTracker().getCooldownPercent(stack.getSkill(), 0);

            if (cooldown > 0.0F)
            {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.colorMask(true, true, true, false);
                GuiUtils.drawRect(x, y + MathHelper.floor(16.0F * (1.0F - cooldown)), 16, MathHelper.ceil(16.0F * cooldown), 255, 255, 255, 127);
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
    }

    /**
     * Draws a texture rectangle using the texture currently bound to the TextureManager
     */
    public void drawSprite(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(xCoord + 0, yCoord + heightIn, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
        worldrenderer.pos(xCoord + widthIn, yCoord + heightIn, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
        worldrenderer.pos(xCoord + widthIn, yCoord + 0, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
        worldrenderer.pos(xCoord + 0, yCoord + 0, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }
}
