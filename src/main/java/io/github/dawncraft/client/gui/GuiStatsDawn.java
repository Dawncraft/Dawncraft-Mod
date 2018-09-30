package io.github.dawncraft.client.gui;

import java.io.IOException;
import java.lang.reflect.Field;

import io.github.dawncraft.client.renderer.entity.RenderSkill;
import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import io.github.dawncraft.stats.StatPage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.StatFileWriter;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class GuiStatsDawn extends GuiStats
{
    /** Holds a instance of RenderSkill, used to draw the achievement icons on screen (is based on SkillStack) */
    protected RenderSkill skillRender;
    public StatFileWriter statFileWriter;
    public int currentPage;
    
    public GuiStatsDawn(GuiScreen parentScreen, StatFileWriter statFile)
    {
        super(parentScreen, statFile);
        skillRender = RenderSkill.getSkillRender();
        this.statFileWriter = statFile;
    }

    @Override
    public void func_175366_f()
    {
        super.func_175366_f();
        this.currentPage = -1;
    }

    @Override
    public void createButtons()
    {
        super.createButtons();
        GuiButton button = new GuiButton(5, this.width / 2 - 160, this.height - 28, 125, 20, StatPage.getTitle(this.currentPage));
        this.buttonList.add(button);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException
    {
        if(button.enabled)
        {
            if(button.id == 5)
            {
                this.currentPage++;
                if (this.currentPage >= StatPage.getStatPages().size())
                {
                    this.currentPage = -1;
                    this.buttonList.clear();
                    this.createButtons();
                }
                else
                {
                    this.buttonList.clear();
                    this.buttonList.add(new GuiButton(0, this.width / 2 + 4, this.height - 28, 150, 20, I18n.format("gui.done")));
                    this.buttonList.add(new GuiButton(5, this.width / 2 - 160, this.height - 28, 125, 20, StatPage.getTitle(this.currentPage)));
                    StatPage page = StatPage.getStatPage(this.currentPage);
                    page.createButtons(this.buttonList, 6, this.width, this.height);
                }

            }
            else if(button.id > 5)
            {
                StatPage page = StatPage.getStatPage(this.currentPage);
                try
                {
                    Field field = ReflectionHelper.findField(GuiStats.class, "displaySlot", "field_146545_u");
                    EnumHelper.setFailsafeFieldValue(field, this, page.initStatSlot(this, button.id - 6));
                }
                catch (Exception e)
                {
                    LogLoader.logger().error("Reflect GuiStats failed: {}", e.toString());
                }
            }
            super.actionPerformed(button);
        }
    }
    
    public void drawStatsScreen(int z, int y, Skill skill)
    {
        this.drawSprite(z + 1, y + 1, 0, 0);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        this.skillRender.renderSkillIntoGUI(new SkillStack(skill, 1), z + 2, y + 2);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }
    
    public void drawSprite(int p_146527_1_, int p_146527_2_, int p_146527_3_, int p_146527_4_)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(statIcons);
        float f = 0.0078125F;
        float f1 = 0.0078125F;
        int i = 18;
        int j = 18;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(p_146527_1_ + 0, p_146527_2_ + 18, this.zLevel).tex((p_146527_3_ + 0) * 0.0078125F, (p_146527_4_ + 18) * 0.0078125F).endVertex();
        worldrenderer.pos(p_146527_1_ + 18, p_146527_2_ + 18, this.zLevel).tex((p_146527_3_ + 18) * 0.0078125F, (p_146527_4_ + 18) * 0.0078125F).endVertex();
        worldrenderer.pos(p_146527_1_ + 18, p_146527_2_ + 0, this.zLevel).tex((p_146527_3_ + 18) * 0.0078125F, (p_146527_4_ + 0) * 0.0078125F).endVertex();
        worldrenderer.pos(p_146527_1_ + 0, p_146527_2_ + 0, this.zLevel).tex((p_146527_3_ + 0) * 0.0078125F, (p_146527_4_ + 0) * 0.0078125F).endVertex();
        tessellator.draw();
    }
}
