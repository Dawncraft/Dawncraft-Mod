package io.github.dawncraft.stats;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class StatPage
{
    private String name;
    
    public StatPage(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }

    @SideOnly(Side.CLIENT)
    public abstract void createButtons(List<GuiButton> buttonList, int suggestId, int width, int height);

    @SideOnly(Side.CLIENT)
    public abstract GuiSlot initStatSlot(GuiStats guiStats, int index);

    private static LinkedList<StatPage> statPages = new LinkedList<StatPage>();

    public static void registerStatPage(StatPage page)
    {
        if (getStatPage(page.getName()) != null)
        {
            throw new RuntimeException("Duplicate stat page name \"" + page.getName() + "\"!");
        }
        statPages.add(page);
    }

    /**
     * Will return a stat page by its index on the list.
     *
     * @param index The page's index.
     * @return the stat page corresponding to the index or null if invalid index
     */
    public static StatPage getStatPage(int index)
    {
        return statPages.get(index);
    }

    /**
     * Will return a stat page by its name.
     *
     * @param name The page's name.
     * @return the stat page with the given name or null if no such page
     */
    public static StatPage getStatPage(String name)
    {
        for (StatPage page : statPages)
        {
            if (page.getName().equals(name))
            {
                return page;
            }
        }
        return null;
    }

    /**
     * Will return the list of stat pages.
     *
     * @return the list's size
     */
    public static Set<StatPage> getStatPages()
    {
        return new HashSet<StatPage>(statPages);
    }

    public static String getTitle(int index)
    {
        return index == -1 ? "Minecraft" : getStatPage(index).getName();
    }
}