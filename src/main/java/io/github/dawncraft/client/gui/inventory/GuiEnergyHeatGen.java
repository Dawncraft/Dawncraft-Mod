package io.github.dawncraft.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.container.ContainerEnergyHeatGen;
import io.github.dawncraft.tileentity.TileEntityEnergyHeatGen;

@SideOnly(Side.CLIENT)
public class GuiEnergyHeatGen extends GuiContainer
{
    private static final ResourceLocation heatGenGuiTextures = new ResourceLocation(
            dawncraft.MODID + ":" + "textures/gui/container/heat_generator.png");
    private final EntityPlayer entityPlayer;
    private final TileEntityEnergyHeatGen heatGenerator;
    private List texts = new ArrayList();
    
    public GuiEnergyHeatGen(EntityPlayer player, TileEntity tileEntity)
    {
        super(new ContainerEnergyHeatGen(player, tileEntity));
        this.entityPlayer = player;
        this.heatGenerator = (TileEntityEnergyHeatGen) tileEntity;
        texts.add(this.heatGenerator.getTileData().getInteger("Electricity") + "A/12800A");
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
//        String s = this.heatGenerator.getDisplayName().getUnformattedText();
//        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.entityPlayer.inventory.getDisplayName().getUnformattedText(), 8,
                this.ySize - 96 + 2, 4210752);
        
        int offsetX = (this.width - this.xSize) / 2;
        int offsetY = (this.height - this.ySize) / 2;
        if (mouseX > offsetX + 105 && mouseX < offsetX + 105 + 14 && mouseY > offsetY + 30 + 31 - 32
                && mouseY < offsetY + 30 + 32 + 1)
        {
            texts.set(0, this.heatGenerator.getTileData().getInteger("Electricity") + "A/12800A");
            drawHoveringText(texts, mouseX, mouseY);
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(heatGenGuiTextures);
        int offsetX = (this.width - this.xSize) / 2;
        int offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        
        int pixels;
        /*
        if (this.heatGenerator.isWorking())
        {
            pixels = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(offsetX + 55, offsetY + 29 + 12 - pixels, 176, 12 - pixels, 14, pixels + 1);
        }
        
        pixels = this.getElectricScaled(32);
        this.drawTexturedModalRect(offsetX + 105, offsetY + 30 + 31 - pixels, 176, 14 + 31 - pixels, 14, pixels + 1);*/
    }
    /*
    private int getBurnLeftScaled(int pixels)
    {
        int j = this.heatGenerator.getField(1);
        
        if (j == 0)
        {
            j = 200;
        }
        
        return this.heatGenerator.getField(0) * pixels / j;
    }
    
    private int getElectricScaled(int pixels)
    {
        int j = this.heatGenerator.getField(2);
        int k = this.heatGenerator.getField(3);
        if (j > k)
        {
            j = k;
        }
        return k != 0 && j != 0 ? j * pixels / k : 0;
    }*/
}
