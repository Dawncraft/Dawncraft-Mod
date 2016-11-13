package com.github.wdawning.dawncraft.client.gui;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.common.ConfigLoader;
import com.github.wdawning.dawncraft.extend.ExtendedPlayer;

public class GuiEventLoader extends Gui
{
	public static final ResourceLocation dIconsTextures = new ResourceLocation(dawncraft.MODID + ":" + "textures/gui/icons.png");
	public static final ResourceLocation mcIconsTextures = new ResourceLocation("textures/gui/icons.png");
	public final Random rand = new Random();
	public Minecraft mc = Minecraft.getMinecraft();
	
	//register
    public GuiEventLoader()
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    //event about GUI
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void PreGUIRender(RenderGameOverlayEvent.Pre event) {     
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
        	int air,mana,a,b;
        	EntityPlayer entityplayer = (EntityPlayer)mc.getRenderViewEntity();
        	int width = event.resolution.getScaledWidth();
        	int height = event.resolution.getScaledHeight();
    		int w1,h1;
        
        	if(mc.playerController.gameIsSurvivalOrAdventure())
        	{
        		this.mc.getTextureManager().bindTexture(dIconsTextures);
        		mana = ExtendedPlayer.get(entityplayer).getMana();
        		w1 = width / 2 + 91;
        		h1 = height - 39 - 9 - 1;
        		int ii,x,y;
        		int u = 0;
        		
        		if(ConfigLoader.manaRenderType)
        		{
        			u = 9;
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
        		
        		this.mc.getTextureManager().bindTexture(mcIconsTextures);
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
     
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void TextRender(RenderGameOverlayEvent.Text event)
    {
        event.left.add(0, String.format("Welcome to play Dawncraft Mod, %s!", Minecraft.getMinecraft().thePlayer.getCommandSenderName()));
        event.left.add(1, String.format("Dawncraft Mod's version is %s!", dawncraft.VERSION));
        event.left.add(2, String.format("This word will remove in the future!"));
        EntityPlayer entityplayer = (EntityPlayer)mc.getRenderViewEntity();
        int amount = ExtendedPlayer.get(entityplayer).getMana();
        event.left.add(3, String.format("Your Mana is %s!", amount));
    }
}
