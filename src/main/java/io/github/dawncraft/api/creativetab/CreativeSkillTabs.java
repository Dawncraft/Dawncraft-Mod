package io.github.dawncraft.api.creativetab;

import java.util.Arrays;
import java.util.List;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Skill creative inventory tab.
 *
 * @author QingChenW
 */
public abstract class CreativeSkillTabs
{
    public static CreativeSkillTabs[] CREATIVE_TAB_ARRAY = new CreativeSkillTabs[12];
    private final int index;
    private final String label;
    private SkillStack icon;
    private boolean drawTitle = true;
    private boolean hasScrollbar = true;
    private String backgroundTexture = "skills.png";

    public CreativeSkillTabs(String label)
    {
        this(getNextID(), label);
    }

    public CreativeSkillTabs(int index, String label)
    {
        if (index >= CREATIVE_TAB_ARRAY.length)
        {
            CREATIVE_TAB_ARRAY = Arrays.copyOf(CREATIVE_TAB_ARRAY, index + 1);
        }
        this.index = index;
        this.label = label;
        this.icon = null;
        CREATIVE_TAB_ARRAY[index] = this;
    }

    @SideOnly(Side.CLIENT)
    public int getIndex()
    {
        return this.index;
    }

    @SideOnly(Side.CLIENT)
    public String getLabel()
    {
        return this.label;
    }

    @SideOnly(Side.CLIENT)
    public int getLabelColor()
    {
        return 0x404040;
    }

    @SideOnly(Side.CLIENT)
    public String getTranslationKey()
    {
        return "skillGroup." + this.getLabel();
    }

    @SideOnly(Side.CLIENT)
    public SkillStack getIcon()
    {
        if (this.icon == null)
        {
            this.icon = this.createIcon();
        }

        return this.icon;
    }

    @SideOnly(Side.CLIENT)
    public abstract SkillStack createIcon();

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIconSprite()
    {
        return null;
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
    public boolean hasScrollbar()
    {
        return this.hasScrollbar;
    }

    public boolean hasSearchBar()
    {
        return this.index == CreativeTabsLoader.SEARCH.index;
    }

    public int getSearchbarWidth()
    {
        return 89;
    }

    public CreativeSkillTabs setBackgroundImageName(String texture)
    {
        this.backgroundTexture = texture;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public String getBackgroundImageName()
    {
        return this.backgroundTexture;
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation getBackgroundImage()
    {
        return new ResourceLocation(Dawncraft.MODID, "textures/gui/container/skill_inventory/tab_" + this.getBackgroundImageName());
    }

    @SideOnly(Side.CLIENT)
    public int getPage()
    {
        if (this.index > 11)
        {
            return (this.index - 12) / 10 + 1;
        }
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public int getColumn()
    {
        if (this.index > 11)
        {
            return (this.index - 12) % 10 % 5;
        }
        return this.index % 6;
    }

    @SideOnly(Side.CLIENT)
    public boolean isOnTopRow()
    {
        if (this.index > 11)
        {
            return (this.index - 12) % 10 < 5;
        }
        return this.index < 6;
    }

    @SideOnly(Side.CLIENT)
    public boolean isAlignedRight()
    {
        return this.getColumn() == 5;
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
            if (skill.isInCreativeTab(this))
            {
                skillList.add(skill);
            }
        }
    }

    public static int getNextID()
    {
        int max = CREATIVE_TAB_ARRAY.length;
        for (int i = 0; i < max; i++)
        {
            if (CREATIVE_TAB_ARRAY[i] == null) return i;
        }
        return max;
    }
}
