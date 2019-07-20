package io.github.dawncraft.client.gui;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.config.ConfigLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiFactory implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraft) {}

    @Override
    public boolean hasConfigGui()
    {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parent)
    {
        List<IConfigElement> elements = Collections.singletonList(ConfigElement.from(ConfigLoader.class));
        return new GuiConfig(parent, elements, Dawncraft.MODID, false, false, Dawncraft.NAME);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return Collections.emptySet();
    }
}
