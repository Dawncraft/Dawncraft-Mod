package io.github.dawncraft.client.event;

import io.github.dawncraft.client.gui.magic.GuiMagic;
import io.github.dawncraft.config.KeyLoader;
import io.github.dawncraft.util.WebBrowserV1;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

/**
 * @author QingChenW
 *
 */
public class InputClientEventHandler
{
    GuiInGameClientEventHandler magicGui;
    public InputClientEventHandler(FMLInitializationEvent event) {this.magicGui = GuiInGameClientEventHandler.instance;}
    
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        // Switch key was pressed
        if (KeyLoader.change.isPressed())
        {
            if(this.magicGui.magicMode)
            {
                this.magicGui.magicMode = false;
                this.magicGui.isSpelling = false;
            }
            else
            {
                this.magicGui.magicMode = true;
            }
        }
        // Spell key was pressed
        if (this.magicGui.magicMode)
        {
            for(int i = 0; i < mc.gameSettings.keyBindsHotbar.length; i++)
            {
                if(mc.gameSettings.keyBindsHotbar[i].isPressed())
                {
                    if(this.magicGui.isSpelling == true)
                    {
                        if(this.magicGui.magicIndex != i)
                        {
                            this.magicGui.magicIndex = i;
                        }
                        else
                        {
                            this.magicGui.isSpelling = false;
                        }
                    }
                    else
                    {
                        this.magicGui.magicIndex = i;
                        this.magicGui.isSpelling = true;
                    }
                }
            }
        }
        // Magic key was pressed
        if (KeyLoader.magic.isPressed())
        {
            mc.displayGuiScreen(new GuiMagic(mc.thePlayer));
        }
        // Use key was pressed
        if (KeyLoader.use.isPressed())
        {
            /*
          //public float defaultFov;
          if(KeyLoader.aim.isPressed())
          {
          if(mc.gameSettings.fovSetting != 10.0F)
          {
              defaultFov = mc.gameSettings.fovSetting;
              mc.gameSettings.fovSetting = 10.0F;
          }
          else
          {
              mc.gameSettings.fovSetting = defaultFov;
          }

          EntityPlayerSP player = mc.thePlayer;
          player.addChatMessage(new ChatComponentTranslation("chat.dawncraft.zoom"));
          }*/
        }
        // Wiki key was pressed
        if (KeyLoader.Encyclopedia.isPressed())
        {
            WebBrowserV1 webBrowser = new WebBrowserV1("我的世界中文维基百科", "http://minecraft-zh.gamepedia.com/Minecraft_Wiki");
        }
    }
}
