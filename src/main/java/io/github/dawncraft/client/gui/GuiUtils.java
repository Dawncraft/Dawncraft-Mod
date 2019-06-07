package io.github.dawncraft.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

import io.github.dawncraft.skill.SkillStack;

public class GuiUtils
{
    public static void drawRectWithRGB(int x, int y, int width, int height, int color)
    {
        int alpha = 255;
        int red = color >> 16 & 255;
        int green = color >> 8 & 255;
        int blue = color & 255;
        drawRect(x, y, width, height, red, green, blue, alpha);
    }

    public static void drawRect(int x, int y, int width, int height, int color)
    {
        int alpha = color >> 24 & 255;
        int red = color >> 16 & 255;
        int green = color >> 8 & 255;
        int blue = color & 255;
        drawRect(x, y, width, height, red, green, blue, alpha);
    }
    
    /**
     * Draws a solid color rectangle with the specified coordinates and colors.
     *
     * @param x X position where the render begin
     * @param y Y position where the render begin
     * @param width The width of the render
     * @param height The height of the render
     * @param red Red component of the color
     * @param green Green component of the color
     * @param blue Blue component of the color
     * @param alpha Alpha component of the color
     */
    public static void drawRect(int x, int y, int width, int height, int red, int green, int blue, int alpha)
    {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((double)x, (double)y, 0.0D).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos((double)x, (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos((double)(x + width), (double)y, 0.0D).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void renderSkillToolTip(GuiScreen gui, SkillStack stack, int x, int y)
    {
        List<String> list = stack.getTooltip(gui.mc.thePlayer, gui.mc.gameSettings.advancedItemTooltips);
        
        for (int i = 1; i < list.size(); ++i)
        {
            list.set(i, EnumChatFormatting.GRAY + (String)list.get(i));
        }
        
        gui.drawHoveringText(list, x, y);
    }
}
