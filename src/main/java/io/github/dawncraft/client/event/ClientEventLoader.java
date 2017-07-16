package io.github.dawncraft.client.event;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.client.gui.magic.GuiMagicBook;
import io.github.dawncraft.config.ConfigLoader;
import io.github.dawncraft.config.KeyLoader;
import io.github.dawncraft.item.ItemLoader;
import io.github.dawncraft.network.MessageSkill;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.util.WebBrowser;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
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
    public boolean isSpelling = false;
    public int magicIndex;
    
    public ClientEventLoader(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        // Switch key was pressed
        if (KeyLoader.change.isPressed())
        {
            if(magicMode)
            {
                magicMode = false;
                isSpelling = false;
            }
            else
            {
                magicMode = true;
            }
        }
        // Spell key was pressed
        if (magicMode)
        {
        	for(int i = 0; i < mc.gameSettings.keyBindsHotbar.length; i++)
        	{
        		if(mc.gameSettings.keyBindsHotbar[i].isPressed())
        		{
        			if(isSpelling == true)
        			{
	        			if(magicIndex != i)
	        			{
	        				magicIndex = i;
	        			}
	        			else
	        			{
	        				isSpelling = false;
	        			}
        			}
        			else
        			{
        				magicIndex = i;
        				isSpelling = true;
        			}
        		}
        	}
        }
        // Magic key was pressed
        if (KeyLoader.magic.isPressed())
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiMagicBook());
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
            WebBrowser webBrowser = new WebBrowser("我的世界中文维基百科", "http://minecraft-zh.gamepedia.com/Minecraft_Wiki");
        }
    }
    
    @SubscribeEvent
    public void PreGUIRender(RenderGameOverlayEvent.Pre event) 
    {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            this.mc.getTextureManager().bindTexture(DCTEXTURES);
            int air,mana,a,b;
            EntityPlayer entityplayer = (EntityPlayer)mc.getRenderViewEntity();
            int width = event.resolution.getScaledWidth();
            int height = event.resolution.getScaledHeight();
            int w1,h1;
        
            if(mc.playerController.gameIsSurvivalOrAdventure())
            {
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
            
            if(event.type == ElementType.HOTBAR)
            {
            	if(magicMode)
            	{
                    event.setCanceled(true);
	                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	                w1 = width / 2 - 91;
	                h1 = height - 22;
	                
	                this.drawTexturedModalRect(w1, h1, 0, 0, 182, 22);
	                if(isSpelling)
	                {
		                this.drawTexturedModalRect(w1 - 1 + magicIndex * 20, h1 - 1, 182, 0, 24, 22);
	                }
	                
	                //GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	                for (int i = 0; i < 9; ++i)
	                {
	                    int x = w1 + 3 + i * 20;
	                    int y = height - 16 - 3;
	                    if(i == 0)
	                    {
	                        this.mc.getTextureManager().bindTexture(new ResourceLocation(dawncraft.MODID + ":" + "textures/gui/skill.png"));
			                this.drawTexturedModalRect(x, y, 0, 0, 16, 16);
			                this.mc.getTextureManager().bindTexture(DCTEXTURES);
	                    }
	                }
            	}
            }
            
            if(magicMode && isSpelling)
            {
            	if(magicIndex == 0)
            	{
            		String s = I18n.format("magic.prefix.spell", I18n.format("magic.heal.name"));
            	    this.drawCenteredString(mc.fontRendererObj, s, width / 2, height - 54, 16777215);
            	}
            }
            
            if(entityplayer.isUsingItem() && entityplayer.getItemInUse().getItem() == ItemLoader.flanRPG)
            {
                this.drawTexturedModalRect(width / 2 - 20, height / 2 - 20, 214, 0, 42, 42);
            }
            
            this.mc.getTextureManager().bindTexture(super.icons);
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
