package io.github.dawncraft.stats;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.achievement.GuiStats;
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
            public GuiStats guiStats;
            
            public StatsSkills(GuiStats guiStats)
            {
                super(guiStats.mc, guiStats.width, guiStats.height, 32, guiStats.height - 64, 20);
                this.guiStats = guiStats;
            }

            @Override
            protected int getSize()
            {
                return 10;
            }

            @Override
            protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
            {
                
            }

            @Override
            protected boolean isSelected(int slotIndex)
            {
                return slotIndex == 6 ? true : false;
            }

            @Override
            protected void drawBackground()
            {
                
            }

            @Override
            protected void drawSlot(int entryID, int par1, int par2, int par3, int mouseXIn, int mouseYIn)
            {
                this.guiStats.drawCenteredString(this.mc.fontRendererObj, I18n.format("stat.skill.test"), this.width / 2, this.height / 2, 0xFFFFFF);
            }
        }
    };

    public static List<StatSpelling> spellStats = Lists.<StatSpelling>newArrayList();
    public static final StatBase[] objectSpellStats = new StatBase[32000];
    
    public StatLoader(FMLInitializationEvent event)
    {
        initSpellStats();

        registerStatPage(pageDawncraft);
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
                    objectSpellStats[id] = new StatSpelling("stat.spellMagic.", name, new ChatComponentTranslation("stat.spellMagic", new Object[] {new SkillStack(skill).getChatComponent()}), skill).registerStat();
                    spellStats.add((StatSpelling) objectSpellStats[id]);
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
