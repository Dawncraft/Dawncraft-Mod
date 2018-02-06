package io.github.dawncraft.api.creativetab;

import java.util.List;

import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class CreativeSkillTabs
{
    public static CreativeSkillTabs[] creativeTabArray = new CreativeSkillTabs[2];
    private final int tabIndex;
    private final String tabLabel;
    private String theTexture = "skills.png";
    private boolean hasScrollbar = true;
    private boolean drawTitle = true;
    @SideOnly(Side.CLIENT)
    private SkillStack iconSkillStack;
    
    public CreativeSkillTabs(String label)
    {
        this(getNextID(), label);
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
    
    public CreativeSkillTabs setNoScrollbar()
    {
        this.hasScrollbar = false;
        return this;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldHidePlayerInventory()
    {
        return this.hasScrollbar;
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
    public SkillStack getIconSkillStack()
    {
        if (this.iconSkillStack == null)
        {
            this.iconSkillStack = new SkillStack(this.getTabIconSkill(), this.getIconSkillLevel());
        }
        
        return this.iconSkillStack;
    }
    
    @SideOnly(Side.CLIENT)
    public abstract Skill getTabIconSkill();

    @SideOnly(Side.CLIENT)
    public int getIconSkillLevel()
    {
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public int getTabPage()
    {
        if (this.tabIndex > 9)
        {
            return (this.tabIndex - 10) / 8 + 1;
        }
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public int getTabRow()
    {
        if (this.tabIndex > 9)
        {
            return (this.tabIndex - 10) % 8 % 4;
        }
        return this.tabIndex % 5;
    }

    @SideOnly(Side.CLIENT)
    public boolean isTabInFirstColumn()
    {
        if (this.tabIndex > 9)
        {
            return (this.tabIndex - 10) % 8 < 4;
        }
        return this.tabIndex < 5;
    }

    @SideOnly(Side.CLIENT)
    public void displayAllSkills(List<Skill> skillList)
    {
        for (Skill skill : Skill.skillRegistry)
        {
            if (skill == null)
            {
                continue;
            }
            if (skill.getCreativeTab() == this)
            {
                skillList.add(skill);
            }
        }
    }

    public static int getNextID()
    {
        return creativeTabArray.length;
    }
}
