package io.github.dawncraft.api.client.event;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;

/**
 * This event fires when some methods associated with {@link ITextComponent} are called.
 *
 * @author QingChenW
 */
public class TextComponentEvent extends GuiScreenEvent
{
    public TextComponentEvent(GuiScreen gui)
    {
        super(gui);
    }

    /**
     * This event fires when {@link GuiScreen#handleComponentHover} is called.
     */
    public static class Hover extends TextComponentEvent
    {
        public final ITextComponent textComponent;
        public final int x;
        public final int y;

        public Hover(GuiScreen gui, ITextComponent component, int x, int y)
        {
            super(gui);
            this.textComponent = component;
            this.x = x;
            this.y = y;
        }
    }

    /**
     * This event fires when {@link GuiScreen#handleComponentClick} is called.
     */
    public static class Click extends TextComponentEvent
    {
        public final ITextComponent textComponent;

        public Click(GuiScreen gui, ITextComponent component)
        {
            super(gui);
            this.textComponent = component;
        }
    }
}
