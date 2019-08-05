package io.github.dawncraft.client.gui.stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.client.ClientProxy;
import io.github.dawncraft.client.renderer.skill.RenderSkill;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.ResourceLocation;

public class GuiStatsDawn extends GuiStats
{
    public static final ResourceLocation STAT_ICONS = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/stats_icons.png");

    /** Holds a instance of RenderSkill, used to draw the achievement icons on screen (is based on SkillStack) */
    protected RenderSkill skillRender;
    private List<GuiSlot> statSlots;
    private GuiButton buttonDone;
    private GuiButton buttonPage;
    private int currentPage = -1;

    public GuiStatsDawn(GuiScreen parentScreen, StatisticsManager statisticsManager)
    {
        super(parentScreen, statisticsManager);
        this.skillRender = ClientProxy.getInstance().getSkillRender();
    }

    @Override
    public void initLists()
    {
        super.initLists();
        this.statSlots = new ArrayList<>();
    }

    @Override
    public void initButtons()
    {
        super.initButtons();
        this.buttonDone = this.buttonList.get(0);
        this.buttonPage = new GuiButton(5, this.width / 2 - 160, this.height - 28, 125, 20, StatPage.getTitle(this.currentPage));
        this.buttonList.add(this.buttonPage);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 5)
        {
            this.currentPage++;
            if (this.currentPage >= StatPage.getStatPages().size())
            {
                this.currentPage = -1;
                this.buttonList.clear();
                this.initButtons();
            }
            else
            {
                this.statSlots.clear();
                StatPage.getStatPage(this.currentPage).initStatSlots(this, this.statSlots);
                this.buttonList.clear();
                this.buttonList.add(this.buttonDone);
                this.buttonPage.displayString = StatPage.getTitle(this.currentPage);
                this.buttonList.add(this.buttonPage);
                StatPage.getStatPage(this.currentPage).createButtons(this.buttonList, 6, this.width, this.height);
            }
        }
        else if (button.id > 5)
        {
            this.displaySlot = this.statSlots.get(button.id - 6);
        }
        else super.actionPerformed(button);
    }

    public void drawStatsScreen(int x, int y, Skill skill)
    {
        this.drawSprite(x + 1, y + 1, 0, 0);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        this.skillRender.renderSkillIntoGUI(new SkillStack(skill, 1), x + 2, y + 2);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }

    @Override
    public void drawSprite(int x, int y, int u, int v)
    {
        if (this.currentPage == -1)
        {
            super.drawSprite(x, y, u, v);
        }
        else
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(STAT_ICONS);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(x + 0, y + 18, this.zLevel).tex((u + 0) * 0.0078125F, (v + 18) * 0.0078125F).endVertex();
            bufferbuilder.pos(x + 18, y + 18, this.zLevel).tex((u + 18) * 0.0078125F, (v + 18) * 0.0078125F).endVertex();
            bufferbuilder.pos(x + 18, y + 0, this.zLevel).tex((u + 18) * 0.0078125F, (v + 0) * 0.0078125F).endVertex();
            bufferbuilder.pos(x + 0, y + 0, this.zLevel).tex((u + 0) * 0.0078125F, (v + 0) * 0.0078125F).endVertex();
            tessellator.draw();
        }
    }
}
