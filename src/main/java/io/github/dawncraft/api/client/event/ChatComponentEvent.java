package io.github.dawncraft.api.client.event;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IChatComponent;

import net.minecraftforge.client.event.GuiScreenEvent;

/**
 * ChatComponentEvent
 *
 * @author QingChenW
 */
public class ChatComponentEvent extends GuiScreenEvent
{
    public ChatComponentEvent(GuiScreen gui)
    {
        super(gui);
    }

    /**
     * This event fires when {@code GuiScreen.handleComponentHover(IChatComponent, int, int)} is called.
     */
    public static class Hover extends ChatComponentEvent
    {
        public final IChatComponent chatComponent;
        public final int x;
        public final int y;

        public Hover(GuiScreen gui, IChatComponent chatComponent, int x, int y)
        {
            super(gui);
            this.chatComponent = chatComponent;
            this.x = x;
            this.y = y;
        }
    }
    
    /**
     * This event fires when {@code GuiScreen.handleComponentClick(IChatComponent)} is called.
     */
    public static class Click extends ChatComponentEvent
    {
        public final IChatComponent chatComponent;

        public Click(GuiScreen gui, IChatComponent chatComponent)
        {
            super(gui);
            this.chatComponent = chatComponent;
        }
    }
}
