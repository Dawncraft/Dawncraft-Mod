package io.github.dawncraft.client.gui;

import java.util.ArrayList;
import java.util.List;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.config.ConfigLoader;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiScreenConfig extends GuiConfig
{
    public static final List<IConfigElement> elements = new ArrayList<IConfigElement>();
    static
    {
        for (String category : ConfigLoader.config().getCategoryNames())
        {
            elements.add(new ConfigElement(ConfigLoader.config().getCategory(category)));
        }
    }

    public GuiScreenConfig(GuiScreen parent)
    {
        super(parent, elements, Dawncraft.MODID, true, false, Dawncraft.NAME);
    }

    @Override
    public void onGuiClosed()
    {
        ConfigLoader.loadConfig();
        super.onGuiClosed();
    }

    @Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);
    }
}
