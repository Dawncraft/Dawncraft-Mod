package io.github.dawncraft.client.gui.stats;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;

public class StatPage
{
    private String name;
    private Map<String, Class<? extends GuiSlot>> statSlotClasses = new LinkedHashMap<String, Class<? extends GuiSlot>>();
    
    public StatPage(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void addSlot(String name, Class<? extends GuiSlot> slotClass)
    {
        this.statSlotClasses.put(name, slotClass);
    }
    
    public boolean hasSlot(String name)
    {
        return this.statSlotClasses.containsKey(name);
    }
    
    public Class<? extends GuiSlot> getSlot(String name)
    {
        return this.statSlotClasses.get(name);
    }
    
    public void removeSlot(String name)
    {
        this.statSlotClasses.remove(name);
    }
    
    public void createButtons(List<GuiButton> buttonList, int suggestId, int slotWidth, int slotHeight)
    {
        int x = slotWidth / 2 - 160;
        int y = slotHeight - 52;
        int width = 320 / this.statSlotClasses.size();
        int height = 20;
        for (Entry<String, Class<? extends GuiSlot>> entry : this.statSlotClasses.entrySet())
        {
            GuiButton guibutton = new GuiButton(suggestId++, x, y, width, height, I18n.format("stat." + entry.getKey() + "Button"));
            buttonList.add(guibutton);
            x += width;
        }
    }
    
    public void initStatSlots(GuiStats guiStats, List<GuiSlot> statSlots)
    {
        for (Entry<String, Class<? extends GuiSlot>> entry : this.statSlotClasses.entrySet())
        {
            try
            {
                Class<? extends GuiSlot> clazz = entry.getValue();
                Constructor<? extends GuiSlot> constructor;
                constructor = clazz.getConstructor(GuiStats.class);
                GuiSlot statSlot = constructor.newInstance(guiStats);
                statSlots.add(statSlot);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private static LinkedList<StatPage> statPages = new LinkedList<StatPage>();

    /**
     * Registers a stat page.
     * @param page The page.
     */
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
        return index < 0 ? "Minecraft" : getStatPage(index).getName();
    }
}