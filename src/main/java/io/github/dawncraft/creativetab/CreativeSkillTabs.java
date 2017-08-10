package io.github.dawncraft.creativetab;

import java.util.List;

import io.github.dawncraft.skill.Skill;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class CreativeSkillTabs
{
    public static CreativeSkillTabs[] creativeTabArray = new CreativeSkillTabs[2];
    private final int tabIndex;
    private final String tabLabel;
    private String theTexture = "skills.png";
    private boolean drawTitle = true;
    @SideOnly(Side.CLIENT)
    private Skill iconSkillStack;
    
    public CreativeSkillTabs(String label)
    {
        this(getNextID(), label);
    }

    public static int getNextID()
    {
        return creativeTabArray.length;
    }
    
    public CreativeSkillTabs(int index, String label)
    {
        if (index >= creativeTabArray.length)
        {
            CreativeSkillTabs[] tmp = new CreativeSkillTabs[index + 1];
            for (int x = 0; x < creativeTabArray.length; x++)
            {
                tmp[x] = creativeTabArray[x];
            }
            creativeTabArray = tmp;
        }
        this.tabIndex = index;
        this.tabLabel = label;
        creativeTabArray[index] = this;
    }
    
    @SideOnly(Side.CLIENT)
    public int getTabIndex()
    {
        return this.tabIndex;
    }

    @SideOnly(Side.CLIENT)
    public String getTabLabel()
    {
        return this.tabLabel;
    }

    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel()
    {
        return "skillGroup." + this.getTabLabel();
    }

    public CreativeSkillTabs setBackgroundImageName(String texture)
    {
        this.theTexture = texture;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public String getBackgroundImageName()
    {
        return this.theTexture;
    }

    public CreativeSkillTabs setNoTitle()
    {
        this.drawTitle = false;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public boolean drawInForegroundOfTab()
    {
        return this.drawTitle;
    }

    public boolean hasSearchBar()
    {
        return this.tabIndex == CreativeTabsLoader.tabSearch.tabIndex;
    }
    
    public int getSearchbarWidth()
    {
        return 43;
    }
    
    @SideOnly(Side.CLIENT)
    public Skill getIconSkillStack()
    {
        return this.iconSkillStack;
    }
    
    @SideOnly(Side.CLIENT)
    public abstract Skill getTabIconSkill();
    
    @SideOnly(Side.CLIENT)
    public int getTabPage()
    {
        if (this.tabIndex > 7)
        {
            return (this.tabIndex - 8) / 10 + 1;
        }
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public int getTabColumn()
    {
        return this.tabIndex % 8;
    }

    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List<Skill> skillList)
    {
        for (Skill skill : Skill.skillRegistry)
        {
            if (skill == null)
            {
                continue;
            }
            if(skill.getCreativeTab() == this)
            {
                skillList.add(skill);
            }
        }
    }
}
