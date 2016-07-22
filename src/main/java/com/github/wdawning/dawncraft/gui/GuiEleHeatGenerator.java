package com.github.wdawning.dawncraft.gui;

import java.util.ArrayList;
import java.util.List;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.tileentity.TileEntityEleHeatGenerator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEleHeatGenerator extends GuiContainer
{
    private static final ResourceLocation heatGeneratorGuiTextures = new ResourceLocation(dawncraft.MODID + ":" + "textures/gui/container/heat_generator.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityEleHeatGenerator tileEleHeatGenerator;
    private List texts = new ArrayList();
    
	public GuiEleHeatGenerator(InventoryPlayer playerInv, TileEntityEleHeatGenerator tile)
	{
		super(new ContainerEleHeatGenerator(playerInv, tile));
        this.playerInventory = playerInv;
        this.tileEleHeatGenerator = tile;
        texts.add(this.tileEleHeatGenerator.getField(2) + "A/" + this.tileEleHeatGenerator.getField(3) + "A");
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.tileEleHeatGenerator.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(heatGeneratorGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        int i1;
        
        if (this.tileEleHeatGenerator.isBurning())
        {
            i1 = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(k + 55, l + 29 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
        }

        i1 = this.getElectricScaled(32);
        this.drawTexturedModalRect(k + 105, l + 30 + 31 - i1, 176, 14 + 31 - i1, 14, i1 + 1);
        
        if (mouseX > k + 105 && mouseX < k + 105 + 14 && mouseY > l + 30 + 31 - 32 && mouseY < l + 30 + 32 + 1)
        {
        	texts.set(0, this.tileEleHeatGenerator.getField(2) + "A/" + this.tileEleHeatGenerator.getField(3) + "A");
        	drawHoveringText(texts, mouseX, mouseY);
        }
    }
	
    private int getBurnLeftScaled(int pixels)
    {
        int j = this.tileEleHeatGenerator.getField(1);

        if (j == 0)
        {
            j = 200;
        }

        return this.tileEleHeatGenerator.getField(0) * pixels / j;
    }
    
    private int getElectricScaled(int pixels)
    {
        int j = this.tileEleHeatGenerator.getField(2);
        int k = this.tileEleHeatGenerator.getField(3);
        if (j > k)
        {
        	j = k;
        }
        return k != 0 && j != 0 ? j * pixels / k : 0;
    }
}
