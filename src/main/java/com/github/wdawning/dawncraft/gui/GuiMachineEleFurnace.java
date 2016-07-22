package com.github.wdawning.dawncraft.gui;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.tileentity.TileEntityMachineEleFurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMachineEleFurnace extends GuiContainer
{
    private static final ResourceLocation eleFurnaceGuiTextures = new ResourceLocation(dawncraft.MODID + ":" + "textures/gui/container/iron_furnace.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityMachineEleFurnace tileEleFurnace;

    public GuiMachineEleFurnace(InventoryPlayer playerInv, TileEntityMachineEleFurnace tile)
    {
        super(new ContainerMachineEleFurnace(playerInv, tile));
        this.playerInventory = playerInv;
        this.tileEleFurnace = tile;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.tileEleFurnace.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(eleFurnaceGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (this.tileEleFurnace.isBurning())
        {
            i1 = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1);
        }

        i1 = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
    }

    private int getCookProgressScaled(int pixels)
    {
        int j = this.tileEleFurnace.getField(0);
        int k = this.tileEleFurnace.getField(1);
        return k != 0 && j != 0 ? j * pixels / k : 0;
    }

    private int getBurnLeftScaled(int pixels)
    {
        if (this.tileEleFurnace.isBurning())
        {
           return pixels;
        }

        return 0;
    }
}
