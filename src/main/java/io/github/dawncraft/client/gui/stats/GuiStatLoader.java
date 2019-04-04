package io.github.dawncraft.client.gui.stats;

import net.minecraft.client.gui.GuiSlot;
import io.github.dawncraft.Dawncraft;

/**
 *
 *
 * @author QingChenW
 */
public class GuiStatLoader
{
    public static StatPage pageDawncraft = new StatPage(Dawncraft.NAME);
    
    public static void initStatSlots()
    {
        addStatSlot(pageDawncraft, "skills", GuiStatsSlot.StatsSkill.class);
        addStatSlot(pageDawncraft, "test", GuiStatsSlot.StatsTest.class);

        registerStatPage(pageDawncraft);
    }

    public static void addStatSlot(StatPage page, String name, Class<? extends GuiSlot> slotClass)
    {
        page.addSlot(name, slotClass);
    }

    public static void registerStatPage(StatPage page)
    {
        StatPage.registerStatPage(page);
    }
}
