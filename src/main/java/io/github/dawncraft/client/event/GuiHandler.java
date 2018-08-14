package io.github.dawncraft.client.event;

import io.github.dawncraft.client.gui.GuiStatsDawn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiHandler
{
    public GuiHandler(FMLInitializationEvent event)
    {
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if(event.gui instanceof GuiStats)
        {
            event.gui = new GuiStatsDawn(mc.currentScreen, mc.thePlayer.getStatFileWriter());
        }
    }
    
    @SubscribeEvent
    public void onGuiPostInit(InitGuiEvent.Post event)
    {
    }
    
    @SubscribeEvent
    public void onActionPostPerformed(ActionPerformedEvent.Post event)
    {
    }
}
