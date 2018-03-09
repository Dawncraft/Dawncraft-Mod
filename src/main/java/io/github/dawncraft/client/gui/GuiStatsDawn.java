package io.github.dawncraft.client.gui;

import java.io.IOException;
import java.lang.reflect.Field;

import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.stats.StatPage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.StatFileWriter;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class GuiStatsDawn extends GuiStats
{
    private int currentPage;
    
    public GuiStatsDawn(GuiScreen parentScreen, StatFileWriter statFile)
    {
        super(parentScreen, statFile);
    }

    @Override
    public void func_175366_f()
    {
        super.func_175366_f();
        this.currentPage = -1;
    }

    @Override
    public void createButtons()
    {
        super.createButtons();
        GuiButton button = new GuiButton(5, this.width / 2 - 160, this.height - 28, 125, 20, StatPage.getTitle(this.currentPage));
        this.buttonList.add(button);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException
    {
        if(button.enabled)
        {
            if(button.id == 5)
            {
                this.currentPage++;
                if (this.currentPage >= StatPage.getStatPages().size())
                {
                    this.currentPage = -1;
                    this.buttonList.clear();
                    this.createButtons();
                }
                else
                {
                    StatPage page = StatPage.getStatPage(this.currentPage);
                    this.buttonList.clear();
                    this.buttonList.add(new GuiButton(0, this.width / 2 + 4, this.height - 28, 150, 20, I18n.format("gui.done")));
                    button.displayString = StatPage.getTitle(this.currentPage);
                    this.buttonList.add(button);
                    page.createButtons(this.buttonList, 6, this.width, this.height);
                }
            }
            else if(button.id > 5)
            {
                StatPage page = StatPage.getStatPage(this.currentPage);
                try
                {
                    Field field = ReflectionHelper.findField(GuiStats.class, "displaySlot", "field_146545_u");
                    EnumHelper.setFailsafeFieldValue(field, this, page.initStatSlot(this, button.id - 6));
                }
                catch (Exception e)
                {
                    LogLoader.logger().error("Reflect GuiStats failed: {}", e.toString());
                }
            }
        }
        super.actionPerformed(button);
    }
}
