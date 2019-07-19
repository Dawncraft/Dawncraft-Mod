package io.github.dawncraft.client.gui;

import java.util.Collections;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

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
	return new GuiScreenConfig(parent);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
	return Collections.emptySet();
    }
}
