package io.github.dawncraft.client.gui.stats;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.stats.StatBase;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;

import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.stats.StatLearning;
import io.github.dawncraft.stats.StatLoader;

public abstract class GuiStatsSlot
{
    public static class StatsSkill extends GuiSlot
    {
        public GuiStatsDawn guiStats;
        protected List<StatLearning> statsHolder;
        protected Comparator<StatLearning> statSorter;
        
        public StatsSkill(GuiStats guiStats)
        {
            super(guiStats.mc, guiStats.width, guiStats.height, 32, guiStats.height - 64, 20);
            this.setShowSelectionBox(false);
            this.setHasListHeader(true, 20);
            this.guiStats = (GuiStatsDawn) guiStats;

            this.statsHolder = Lists.<StatLearning>newArrayList();
            for (StatLearning statlearning : StatLoader.skillStats)
            {
                boolean flag = false;
                int i = Skill.getIdFromSkill(statlearning.getSkill());
                
                if (this.guiStats.field_146546_t.readStat(statlearning) > 0)
                {
                    flag = true;
                }
                else if (StatLoader.objectSpellStats[i] != null && this.guiStats.field_146546_t.readStat(StatLoader.objectSpellStats[i]) > 0)
                {
                    flag = true;
                }
                else if (StatLoader.objectLearnStats[i] != null && this.guiStats.field_146546_t.readStat(StatLoader.objectLearnStats[i]) > 0)
                {
                    flag = true;
                }
                
                if (flag)
                {
                    this.statsHolder.add(statlearning);
                }
            }
            
            this.statSorter = new Comparator<StatLearning>()
            {
                @Override
                public int compare(StatLearning o1, StatLearning o2)
                {
                    return 0;
                }
            };
        }

        @Override
        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
        {
        }

        @Override
        protected boolean isSelected(int slotIndex)
        {
            return false;
        }

        @Override
        protected void drawBackground()
        {
            this.guiStats.drawDefaultBackground();
        }
        
        @Override
        protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_)
        {
        }

        protected final StatLearning getStat(int index)
        {
            return this.statsHolder.get(index);
        }

        @Override
        protected final int getSize()
        {
            return this.statsHolder.size();
        }
        
        @Override
        protected void drawSlot(int entryID, int par1, int par2, int par3, int mouseXIn, int mouseYIn)
        {
            StatLearning statlearning = this.getStat(entryID);
            Skill skill = statlearning.getSkill();
            this.guiStats.drawStatsScreen(par1 + 40, par2, skill);
            int i = Skill.getIdFromSkill(skill);
            this.drawStat(StatLoader.objectSpellStats[i], par1 + 165, par2, entryID % 2 == 0);
            this.drawStat(statlearning, par1 + 215, par2, entryID % 2 == 0);
        }

        protected void drawStat(StatBase stat, int x, int y, boolean isOddLine)
        {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
            if (stat != null)
            {
                String s = stat.format(this.guiStats.field_146546_t.readStat(stat));
                this.guiStats.drawString(fontRenderer, s, x - fontRenderer.getStringWidth(s), y + 5, isOddLine ? 16777215 : 9474192);
            }
            else
            {
                String s1 = "-";
                this.guiStats.drawString(fontRenderer, s1, x - fontRenderer.getStringWidth(s1), y + 5, isOddLine ? 16777215 : 9474192);
            }
        }
    }

    public static class StatsTest extends GuiSlot
    {
        public StatsTest(GuiStats guiStats)
        {
            super(guiStats.mc, guiStats.width, guiStats.height, 32, guiStats.height - 64, 10);
        }
        
        @Override
        protected int getSize()
        {
            return 0;
        }
        
        @Override
        protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
        {

        }
        
        @Override
        protected boolean isSelected(int slotIndex)
        {
            return false;
        }
        
        @Override
        protected void drawBackground()
        {

        }
        
        @Override
        protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn,
                int mouseYIn)
        {

        }
    }
}
