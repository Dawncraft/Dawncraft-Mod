package io.github.dawncraft.client.event;

import java.lang.reflect.Field;
import io.github.dawncraft.config.LogLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent.*;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class GuiStatsDawn extends Gui
{
    private StatsSkills skillStats;
    
    public GuiStatsDawn(FMLInitializationEvent event)
    {
    }
    
    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onGuiPostInit(InitGuiEvent.Post event)
    {
        if(event.gui instanceof GuiStats)
        {
            GuiStats stats = (GuiStats) event.gui;
            this.skillStats = new StatsSkills(event.gui.mc, stats);
            GuiButton button = new GuiButton(5, stats.width / 2 - 160, stats.height - 30, 80, 20, I18n.format("stat.skillsButton"));
            if (false)
            {
                button.enabled = false;
            }
            event.buttonList.add(button);
        }
    }
    
    @SubscribeEvent
    public void onActionPostPerformed(ActionPerformedEvent.Post event)
    {
        if(event.gui instanceof GuiStats)
        {
            GuiStats stats = (GuiStats) event.gui;
            GuiButton button = event.button;
            if(button.enabled)
            {
                if(button.id == 5)
                {
                    try
                    {
                        Field field = ReflectionHelper.findField(GuiStats.class, "displaySlot", "field_146545_u");
                        EnumHelper.setFailsafeFieldValue(field, stats, this.skillStats);
                    }
                    catch (Exception e)
                    {
                        LogLoader.logger().error("Reflect GuiStats failed: {}", e.toString());
                    }
                }
            }
        }
    }

    public class StatsSkills extends GuiSlot// extends GuiStats.Stats
    {
        public StatsSkills(Minecraft mcIn, GuiStats GuiStatsIn)
        {
            super(mcIn, GuiStatsIn.width, GuiStatsIn.height, 32, GuiStatsIn.height - 64, 20);
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
            GuiStatsDawn.this.drawCenteredString(this.mc.fontRendererObj, I18n.format("stat.skill.test"), this.width / 2, this.height / 2, 0xFFFFFF);
        }
    }
}
