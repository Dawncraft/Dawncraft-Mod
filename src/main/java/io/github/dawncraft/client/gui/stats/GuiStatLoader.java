package io.github.dawncraft.client.gui.stats;

import net.minecraft.client.gui.GuiSlot;
import io.github.dawncraft.Dawncraft;

/**
 * Register some stat slots.
 *
 * @author QingChenW
 */
public class GuiStatLoader
{
    public static StatPage pageDawncraft = new StatPage(Dawncraft.NAME);

    public static void initStatSlots()
    {
        addStatSlot(pageDawncraft, "overall", GuiStatsSlot.StatsTest.class);
        addStatSlot(pageDawncraft, "job", GuiStatsSlot.StatsTest.class);
        addStatSlot(pageDawncraft, "skills", GuiStatsSlot.StatsSkill.class);
        addStatSlot(pageDawncraft, "talents", GuiStatsSlot.StatsTest.class);
        
        registerStatPage(pageDawncraft);
    }
    
    private static void addStatSlot(StatPage page, String name, Class<? extends GuiSlot> slotClass)
    {
        page.addSlot(name, slotClass);
    }
    
    private static void registerStatPage(StatPage page)
    {
        StatPage.registerStatPage(page);
    }
}
