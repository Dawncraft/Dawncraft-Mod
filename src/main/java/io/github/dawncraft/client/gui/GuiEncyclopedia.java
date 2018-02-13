package io.github.dawncraft.client.gui;

import java.io.IOException;

import org.markdown4j.Markdown4jProcessor;

import io.github.dawncraft.config.LogLoader;
import net.minecraft.client.gui.Gui;

// 发现Minecraft wiki的api: https://minecraft-zh.gamepedia.com/api.php
// 哪天找时间给它剁了[滑稽]
public class GuiEncyclopedia extends Gui
{
    public static Markdown4jProcessor markdown = new Markdown4jProcessor();
    
    public GuiEncyclopedia()
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
