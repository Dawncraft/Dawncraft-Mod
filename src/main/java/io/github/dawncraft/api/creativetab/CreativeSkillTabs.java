package io.github.dawncraft.api.creativetab;

import java.util.Arrays;
import java.util.List;

import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Skill creative inventory tab.
 *
 * @author QingChenW
 */
public abstract class CreativeSkillTabs
{
    public static CreativeSkillTabs[] creativeTabArray = new CreativeSkillTabs[12];
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
            creativeTabArray = Arrays.copyOf(creativeTabArray, index + 1);
        }
        this.tabIndex = index;
        this.tabLabel = label;
        creativeTabArray[index] = this;
    }

    @SideOnly(Side.CLIENT)
    public int getIndex()
    {
        return this.tabIndex;
    }

    @SideOnly(Side.CLIENT)
    public SkillStack getIconSkillStack()
    {
        if (this.iconSkillStack == null)
        {
            this.iconSkillStack = new SkillStack(this.getIconSkill(), this.getIconSkillLevel());
        }

        return this.iconSkillStack;
    }

    @SideOnly(Side.CLIENT)
    public abstract Skill getIconSkill();

    @SideOnly(Side.CLIENT)
    public int getIconSkillLevel()
    {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon()
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public String getLabel()
    {
        return this.tabLabel;
    }

    @SideOnly(Side.CLIENT)
    public String getTranslatedLabel()
    {
        return "skillGroup." + this.getLabel();
    }

    public CreativeSkillTabs setNoTitle()
    {
        this.drawTitle = false;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldDrawTitle()
    {
        return this.drawTitle;
    }

    public CreativeSkillTabs setNoScrollbar()
    {
        this.hasScrollbar = false;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldHideInventory()
    {
        return this.hasScrollbar;
    }

    public boolean hasSearchBar()
    {
        return this.tabIndex == CreativeTabsLoader.tabSearch.tabIndex;
    }

    public int getSearchbarWidth()
    {
        return 89;
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

    @SideOnly(Side.CLIENT)
    public int getPage()
    {
        if (this.tabIndex > 11)
        {
            return (this.tabIndex - 12) / 10 + 1;
        }
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public int getColumn()
    {
        if (this.tabIndex > 11)
        {
            return (this.tabIndex - 12) % 10 % 5;
        }
        return this.tabIndex % 6;
    }

    @SideOnly(Side.CLIENT)
    public boolean isOnTopRow()
    {
        if (this.tabIndex > 11)
        {
            return (this.tabIndex - 12) % 10 < 5;
        }
        return this.tabIndex < 6;
    }

    @SideOnly(Side.CLIENT)
    public void displayAllSkills(List<Skill> skillList)
    {
        for (Skill skill : Skill.REGISTRY)
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
        int max = creativeTabArray.length;
        for (int i = 0; i < max; i++)
        {
            if (creativeTabArray[i] == null) return i;
        }
        return max;
    }
}
