package io.github.dawncraft.client.gui;

import java.io.IOException;

import org.markdown4j.Markdown4jProcessor;

import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.util.WebBrowserV1;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

// 发现Minecraft wiki的api: https://minecraft-zh.gamepedia.com/api.php
// 哪天找时间给它剁了[滑稽]
public class GuiEncyclopedia extends GuiScreen
{
    public static Markdown4jProcessor markdown = new Markdown4jProcessor();
    
    private String currentURL;
    private GuiButton buttonTest;

    public GuiEncyclopedia()
    {

    }
    
    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(this.buttonTest = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 10, 200, 20, I18n.format("gui.wiki")));
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 0)
            {
                WebBrowserV1 webBrowser = new WebBrowserV1("我的世界中文维基百科", "http://minecraft-zh.gamepedia.com/Minecraft_Wiki");
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawRect(100, 100, 100, 100, 0x7fffffff);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {

    }

    public static void test()
    {
        try
        {
            String html = markdown.process("This is a **bold** text for test");
            LogLoader.logger().info(html);
        }
        catch (IOException e)
        {
            LogLoader.logger().error("Can't load markdown:", e);
        }
    }
}
