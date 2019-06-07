package io.github.dawncraft.client.gui.container;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;

import java.util.List;

import io.github.dawncraft.Dawncraft;

public class GuiInventoryTabs extends Gui
{
    private static final ResourceLocation INVENTORY_TABS = new ResourceLocation(Dawncraft.MODID + ":" + "textures/gui/container/inventory/inventory_tabs.png");
    private Minecraft mc;
    private GuiScreen parent;
    private List<Tab> inventoryTabs;
    private int currentIndex = 0;
    
    public void initGui(GuiScreen parent, Tab... tabs)
    {
        this.mc = Minecraft.getMinecraft();
        this.parent = parent;
        this.inventoryTabs = Lists.newArrayList(tabs);
    }
    
    public void drawTabs(int mouseX, int mouseY, float partialTicks)
    {
    }

    public boolean mouseClicked(Minecraft mc, int mouseX, int mouseY)
    {
        return false;
    }
    
    protected boolean isMouseOverTab(Tab tab, int mouseX, int mouseY)
    {
        int x = 0;
        int y = 0 + 24 * tab.index;
        return mouseX >= x && mouseX <= x + 34 && mouseY >= y && mouseY <= y + 32;
    }
    
    public class Tab extends Gui
    {
        int index;
        String name;
        Item icon;

        public Tab(String name, Item icon)
        {
            this.index = getNextID();
            this.name = name;
            this.icon = icon;
        }

        protected void drawTab(Tab tab)
        {
        }
    }
    
    public static int getNextID()
    {
        return 0;
    }
}
