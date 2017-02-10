package io.github.dawncraft.client;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.common.ConfigLoader;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Register some client events.
 * 
 * @author QingChenW
 */
public class ClientEventLoader extends Gui
{
    public static final ResourceLocation DCTEXTURES = new ResourceLocation(dawncraft.MODID + ":" + "textures/gui/widgets.png");
    public Minecraft mc = Minecraft.getMinecraft();
    
    public boolean magicMode = false;
    
    public ClientEventLoader(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        // Switch key was pressed
        if(KeyLoader.change.isPressed())
        {
            if(magicMode)
            {
                magicMode = false;
            }
            else
            {
                magicMode = true;
            }
        }
        // Magic key was pressed
        if (KeyLoader.magic.isPressed())
        {
            //Minecraft.getMinecraft().displayGuiScreen(new GuiMagicBook());
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
            //Minecraft.getMinecraft().displayGuiScreen(new GuiEncyclopedia());
        }
    }
    
    @SubscribeEvent
    public void PreGUIRender(RenderGameOverlayEvent.Pre event) 
    {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            int air,mana,a,b;
            EntityPlayer entityplayer = (EntityPlayer)mc.getRenderViewEntity();
            int width = event.resolution.getScaledWidth();
            int height = event.resolution.getScaledHeight();
            int w1,h1;
        
            if(mc.playerController.gameIsSurvivalOrAdventure())
            {
                this.mc.getTextureManager().bindTexture(DCTEXTURES);
                if(entityplayer.hasCapability(CapabilityLoader.mana, null))
                {
                      mana = entityplayer.getCapability(CapabilityLoader.mana, null).getMana();
                }
                else
                {
                    mana = 20;
                }
                w1 = width / 2 + 91;
                h1 = height - 39 - 9 - 1;
                int ii,x,y;
                int u = 22;
                
                if(ConfigLoader.manaRenderType)
                {
                    u = u + 9;
                }

                for (ii = 0; ii < 10; ++ii)
                {
                    x = w1 - ii * 8 - 9;
                    y = h1;

                    this.drawTexturedModalRect(x, y, 0, u, 9, 9);

                    if (ii * 2 + 1 < mana)
                    {
                        this.drawTexturedModalRect(x, y, 9, u, 9, 9);
                    }

                    if (ii * 2 + 1 == mana)
                    {
                        this.drawTexturedModalRect(x, y, 17, u, 9, 9);
                    }
                }
                
                this.mc.getTextureManager().bindTexture(super.icons);
            }
            
            if(event.type == ElementType.AIR)
            {
                event.setCanceled(true);
                w1 = width / 2 + 91;
                h1 = height - 39 - 9 - 1 - 9 - 1;
            
                this.mc.mcProfiler.startSection("air");
                if (entityplayer.isInsideOfMaterial(Material.water))
                {
                    air = this.mc.thePlayer.getAir();
                
                    a = MathHelper.ceiling_double_int((double)(air - 2) * 10.0D / 300.0D);
                    b = MathHelper.ceiling_double_int((double)air * 10.0D / 300.0D) - a;

                    for (int i = 0; i < a + b; ++i)
                    {
                        if (i < a)
                        {
                            this.drawTexturedModalRect(w1 - i * 8 - 9, h1, 16, 18, 9, 9);
                        }
                        else
                        {
                            this.drawTexturedModalRect(w1 - i * 8 - 9, h1, 25, 18, 9, 9);
                        }
                    }
                this.mc.mcProfiler.endSection();
                }
            }
        }
        
    }
    
    @SubscribeEvent
    public void TextRender(RenderGameOverlayEvent.Text event)
    {
        event.left.add(0, String.format("Welcome to play Dawncraft Mod, %s!", Minecraft.getMinecraft().thePlayer.getName()));
        event.left.add(1, String.format("Dawncraft Mod's version is %s!", dawncraft.VERSION));
        event.left.add(2, String.format("This word will remove in the future!"));
        EntityPlayer entityplayer = (EntityPlayer)mc.getRenderViewEntity();
        int mp = 20;
        if(entityplayer.hasCapability(CapabilityLoader.mana, null))
        {
              mp = entityplayer.getCapability(CapabilityLoader.mana, null).getMana();
        }
        event.left.add(3, String.format("Your Mana is %s!", mp));
    }
}
