package io.github.dawncraft.stats;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.client.gui.GuiStatsDawn;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.StatBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StatLoader
{
    public static StatPage pageDawncraft = new StatPage(Dawncraft.NAME)
    {
        @Override
        @SideOnly(Side.CLIENT)
        public GuiSlot initStatSlot(GuiStats guiStats, int index)
        {
            if(index == 0)
            {
                return new StatsSkills(guiStats);
            }
            return null;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void createButtons(List<GuiButton> buttonList, int suggestId, int width, int height)
        {
            GuiButton guibutton = new GuiButton(suggestId, width / 2 - 160, height - 52, 80, 20, I18n.format("stat.skillsButton"));
            buttonList.add(guibutton);
            GuiButton guibutton1 = new GuiButton(++suggestId, width / 2 - 80, height - 52, 80, 20, I18n.format("stat.testButton"));
            buttonList.add(guibutton1);
            
            guibutton1.enabled = false;
        }

        @SideOnly(Side.CLIENT)
        class StatsSkills extends GuiSlot
        {
            public GuiStatsDawn guiStats;
            protected List<StatLearning> statsHolder;
            protected Comparator<StatLearning> statSorter;

            public StatsSkills(GuiStats guiStats)
            {
                super(guiStats.mc, guiStats.width, guiStats.height, 32, guiStats.height - 64, 20);
                this.setShowSelectionBox(false);
                this.setHasListHeader(true, 20);
                this.guiStats = (GuiStatsDawn) guiStats;
                
                statsHolder = Lists.<StatLearning>newArrayList();
                for (StatLearning statlearning : StatLoader.skillStats)
                {
                    boolean flag = false;
                    int i = Skill.getIdFromSkill(statlearning.getSkill());

                    if (this.guiStats.statFileWriter.readStat(statlearning) > 0)
                    {
                        flag = true;
                    }
                    else if (StatLoader.objectSpellStats[i] != null && this.guiStats.statFileWriter.readStat(StatLoader.objectSpellStats[i]) > 0)
                    {
                        flag = true;
                    }
                    else if (StatLoader.objectLearnStats[i] != null && this.guiStats.statFileWriter.readStat(StatLoader.objectLearnStats[i]) > 0)
                    {
                        flag = true;
                    }

                    if (flag)
                    {
                        this.statsHolder.add(statlearning);
                    }
                }
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
                    String s = stat.format(this.guiStats.statFileWriter.readStat(stat));
                    this.guiStats.drawString(fontRenderer, s, x - fontRenderer.getStringWidth(s), y + 5, isOddLine ? 16777215 : 9474192);
                }
                else
                {
                    String s1 = "-";
                    this.guiStats.drawString(fontRenderer, s1, x - fontRenderer.getStringWidth(s1), y + 5, isOddLine ? 16777215 : 9474192);
                }
            }
        }
    };
    
    public static List<StatLearning> skillStats = Lists.<StatLearning>newArrayList();
    public static final StatBase[] objectLearnStats = new StatBase[32000];
    public static final StatBase[] objectSpellStats = new StatBase[32000];

    public StatLoader(FMLInitializationEvent event)
    {
        initLearnableStats();
        initSpellStats();
        
        registerStatPage(pageDawncraft);
    }
    
    /**
     * Initializes statistics related to learnable skills. Is only called after skill stats have been initialized.
     */
    private static void initLearnableStats()
    {
        Set<Skill> set = Sets.<Skill>newHashSet();
    }

    private static void initSpellStats()
    {
        for (Skill skill : Skill.skillRegistry)
        {
            if (skill != null)
            {
                int id = Skill.getIdFromSkill(skill);
                String name = replace(Skill.skillRegistry.getNameForObject(skill));
                
                if (name != null)
                {
                    objectLearnStats[id] = new StatLearning("stat.spellSkill.", name, new ChatComponentTranslation("stat.spellSkill", new SkillStack(skill).getChatComponent()), skill).registerStat();
                    skillStats.add((StatLearning) objectLearnStats[id]);
                }
            }
        }
    }
    
    public static String replace(ResourceLocation resourcelocation)
    {
        return resourcelocation != null ? resourcelocation.toString().replace(':', '.') : null;
    }
    
    public static void registerStatPage(StatPage page)
    {
        StatPage.registerStatPage(page);
    }
}
