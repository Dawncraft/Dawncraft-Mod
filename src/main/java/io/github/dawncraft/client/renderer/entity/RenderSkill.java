package io.github.dawncraft.client.renderer.entity;

import java.util.List;
import java.util.concurrent.Callable;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.client.renderer.model.ModelLoader;
import io.github.dawncraft.client.renderer.skill.SkillModelMesher;
import io.github.dawncraft.client.renderer.texture.TextureLoader;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

public class RenderSkill implements IResourceManagerReloadListener
{
    public float zLevel;
    private final SkillModelMesher skillModelMesher;
    private final TextureManager textureManager;

    public RenderSkill()
    {
        super();
        this.textureManager = Minecraft.getMinecraft().getTextureManager();
        this.skillModelMesher = new SkillModelMesher();
        ModelLoader.onRegisterSkills(this.skillModelMesher);
    }
    
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        this.skillModelMesher.rebuildCache();
    }
    
    private void renderModel(IBakedModel model, SkillStack stack)
    {
        this.renderModel(model, -1, stack);
    }
    
    private void renderModel(IBakedModel model, int color)
    {
        this.renderModel(model, color, (SkillStack) null);
    }
    
    private void renderModel(IBakedModel model, int color, SkillStack stack)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.ITEM);
        
        for (EnumFacing enumfacing : EnumFacing.values())
        {
            this.renderQuads(worldrenderer, model.getFaceQuads(enumfacing), color, stack);
        }
        
        this.renderQuads(worldrenderer, model.getGeneralQuads(), color, stack);
        tessellator.draw();
    }
    
    private void renderQuads(WorldRenderer renderer, List<BakedQuad> quads, int color, SkillStack stack)
    {
        boolean flag = color == -1 && stack != null;
        int i = 0;
        
        for (int j = quads.size(); i < j; ++i)
        {
            BakedQuad bakedquad = (BakedQuad)quads.get(i);
            int k = color;
            
            if (flag && bakedquad.hasTintIndex())
            {
                k = stack.getSkill().getColorFromSkillStack(stack, bakedquad.getTintIndex());
                
                if (EntityRenderer.anaglyphEnable)
                {
                    k = TextureUtil.anaglyphColor(k);
                }
                
                k = k | -16777216;
            }
            
            net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(renderer, bakedquad, k);
        }
    }
    
    public void renderSkill(SkillStack stack, IBakedModel model)
    {
        if (stack != null)
        {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            
            if (model.isBuiltInRenderer())
            {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(-0.5F, -0.5F, -0.5F);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableRescaleNormal();
                // TileEntityItemStackRenderer.instance.renderByItem(stack);
            }
            else
            {
                GlStateManager.translate(-0.5F, -0.5F, -0.5F);
                this.renderModel(model, stack);
            }
            
            GlStateManager.popMatrix();
        }
    }
    
    public void renderSkill(SkillStack stack, TextureAtlasSprite sprite, int x, int y)
    {
        if (stack != null)
        {
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, 1.0F, 1.0F);
            this.drawSprite(x, y, sprite, 16, 16);
            GlStateManager.popMatrix();
        }
    }
    
    public void renderSkillIntoGUI(SkillStack stack, int x, int y)
    {
        //IBakedModel ibakedmodel = this.skillModelMesher.getSkillModel(stack);
        ResourceLocation res = getActualLocation(new ResourceLocation(stack.getSkill().getRegistryName()));
        TextureAtlasSprite sprite = TextureLoader.getTextureLoader().getTextureMapSkills().getAtlasSprite(res.toString());
        GlStateManager.pushMatrix();
        this.textureManager.bindTexture(TextureLoader.locationSkillsTexture);
        this.textureManager.getTexture(TextureLoader.locationSkillsTexture).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        /*        this.setupGuiTransform(x, y, ibakedmodel.isGui3d());
        ibakedmodel = ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GUI);
        this.renderSkill(stack, ibakedmodel);*/
        this.renderSkill(stack, sprite, x, y);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        this.textureManager.bindTexture(TextureLoader.locationSkillsTexture);
        this.textureManager.getTexture(TextureLoader.locationSkillsTexture).restoreLastBlurMipmap();
    }
    
    public static ResourceLocation getActualLocation(ResourceLocation location)
    {
        return new ResourceLocation(location.getResourceDomain(), "skills/" + location.getResourcePath());
    }
    
    private void setupGuiTransform(int xPosition, int yPosition, boolean isGui3d)
    {
        GlStateManager.translate((float)xPosition, (float)yPosition, 100.0F + this.zLevel);
        GlStateManager.translate(8.0F, 8.0F, 0.0F);
        GlStateManager.scale(1.0F, 1.0F, -1.0F);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        
        if (isGui3d)
        {
            GlStateManager.scale(40.0F, 40.0F, 40.0F);
            GlStateManager.rotate(210.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.enableLighting();
        }
        else
        {
            GlStateManager.scale(64.0F, 64.0F, 64.0F);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.disableLighting();
        }
    }
    
    public void renderSkillAndEffectIntoGUI(final SkillStack stack, int xPosition, int yPosition)
    {
        if (stack != null && stack.getSkill() != null)
        {
            this.zLevel += 50.0F;
            
            try
            {
                this.renderSkillIntoGUI(stack, xPosition, yPosition);
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering skill");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Skill being rendered");
                crashreportcategory.addCrashSectionCallable("Skill Type", new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        return String.valueOf((Object)stack.getSkill());
                    }
                });
                crashreportcategory.addCrashSectionCallable("Stem Aux", new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        return String.valueOf(stack.getSkillLevel());
                    }
                });
                crashreportcategory.addCrashSectionCallable("Item NBT", new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        return String.valueOf((Object)stack.hasTagCompound());
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
    public void renderSkillOverlayIntoGUI(FontRenderer fr, SkillStack stack, int xPosition, int yPosition)
    {
        if (stack != null)
        {
            String s = String.valueOf(stack.skillLevel);
            
            if (stack.skillLevel < 1)
            {
                s = EnumChatFormatting.RED + String.valueOf(stack.skillLevel);
            }
            
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableBlend();
            fr.drawStringWithShadow(s, (float)(xPosition + 19 - 2 - fr.getStringWidth(s)), (float)(yPosition + 6 + 3), 16777215);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            
            float cooldown = 0.0F;
            EntityPlayerSP clientPlayer = Minecraft.getMinecraft().thePlayer;
            if (clientPlayer.hasCapability(CapabilityLoader.playerMagic, null))
            {
                IPlayerMagic playerMagic = clientPlayer.getCapability(CapabilityLoader.playerMagic, null);
                cooldown = playerMagic.getCooldownTracker().getCooldownPercent(stack.getSkill(), 0);
            }

            if (cooldown > 0.0F)
            {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldrender = tessellator.getWorldRenderer();
                this.draw(worldrender, xPosition, yPosition + MathHelper.floor_float(16.0F * (1.0F - cooldown)), 16, MathHelper.ceiling_float_int(16.0F * cooldown), 223, 223, 223, 63);
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
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
    public void draw(WorldRenderer renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha)
    {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((double)(x + 0), (double)(y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + 0), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }
    
    /**
     * Draws a texture rectangle using the texture currently bound to the TextureManager
     */
    public void drawSprite(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(xCoord + 0, yCoord + heightIn, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
        worldrenderer.pos(xCoord + widthIn, yCoord + heightIn, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
        worldrenderer.pos(xCoord + widthIn, yCoord + 0, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
        worldrenderer.pos(xCoord + 0, yCoord + 0, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    public SkillModelMesher getSkillModelMesher()
    {
        return this.skillModelMesher;
    }
}
