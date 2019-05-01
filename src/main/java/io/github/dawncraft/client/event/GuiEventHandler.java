package io.github.dawncraft.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.event.HoverEvent;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import io.github.dawncraft.api.client.event.ChatComponentEvent;
import io.github.dawncraft.client.gui.GuiUtils;
import io.github.dawncraft.client.gui.stats.GuiStatsDawn;
import io.github.dawncraft.event.EventLoader;
import io.github.dawncraft.skill.SkillStack;

public class GuiEventHandler
{
    private Minecraft mc;
    
    public GuiEventHandler()
    {
        this.mc = Minecraft.getMinecraft();
    }
    
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
        if (event.gui instanceof GuiStats)
        {
            event.gui = new GuiStatsDawn(this.mc.currentScreen, this.mc.thePlayer.getStatFileWriter());
        }
    }

    @SubscribeEvent
    public void onPostGuiInit(InitGuiEvent.Post event)
    {
    }

    @SubscribeEvent
    public void onPostDrawScreen(DrawScreenEvent.Post event)
    {
    }

    @SubscribeEvent
    public void onPostActionPerformed(ActionPerformedEvent.Post event)
    {
    }

    @SubscribeEvent
    public void onDrawHoverChatComponent(ChatComponentEvent.Hover event)
    {
        if (event.chatComponent != null && event.chatComponent.getChatStyle().getChatHoverEvent() != null)
        {
            HoverEvent hoverEvent = event.chatComponent.getChatStyle().getChatHoverEvent();
            
            if (hoverEvent.getAction() == EventLoader.SHOW_SKILL)
            {
                SkillStack skillStack = null;
                try
                {
                    NBTBase nbt = JsonToNBT.getTagFromJson(hoverEvent.getValue().getUnformattedText());
                    
                    if (nbt instanceof NBTTagCompound)
                    {
                        skillStack = SkillStack.loadSkillStackFromNBT((NBTTagCompound) nbt);
                    }
                }
                catch (NBTException e) {}
                
                if (skillStack != null)
                {
                    GuiUtils.renderSkillToolTip(event.gui, skillStack, event.x, event.y);
                }
                else
                {
                    event.gui.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Skill!", event.x, event.y);
                }
            }
        }
    }
}
